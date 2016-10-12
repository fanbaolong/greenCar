package com.green.car.http;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ConnectTimeoutException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.green.car.util.LogUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.OkHeaders;

/**
 * 网络模块具体实现方式
 *
 * @author chengbo
 */
public class NetManager {
    private static NetManager netManager;
    private OkHttpClient mOkHttpClient;
    private static String baseUrl = null;
    private final int TIMEOUTTIME = 5;
    private final int READTIMEOUT = 15;

    public final static int REQUEST_TYPE_POST = 0x01;
    public final static int REQUEST_TYPE_GET = 0x02;
    public final String CHARSET_PREFIX = "\ufeff";// 去掉非法字符
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");// POST提交Json数据
    private String res;

    public static NetManager getInstant(Context context) {
        if (netManager == null) {
            netManager = new NetManager(context);
            baseUrl = TextUtils.isEmpty(ConstantUtil.URL)?"":ConstantUtil.URL;
        }
        return netManager;
    }

    private NetManager(Context context) {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(TIMEOUTTIME, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(READTIMEOUT, TimeUnit.SECONDS);
    }

    public String sendJsonRequestFromOkHttpClient(Context context, String path,
                                                  Map<String, Object> values, Handler handler, String tag) {
        return sendJsonRequestFromOkHttpClient(context, REQUEST_TYPE_GET, path,
                values, handler, tag);
    }

    public String sendJsonRequestFromOkHttpClient(Context context,
                                                  int requestType, String path, Map<String, Object> values,
                                                  Handler handler, String tag) {
        return sendJsonRequestFromOkHttpClient(context, requestType, path,
                values, handler, tag, false, 24 * 3600 * 1000);
    }

    @SuppressWarnings("unchecked")
    public String sendJsonRequestFromOkHttpClient(Context context,
                                                  int requestType, String path, Map<String, Object> values,
                                                  Handler handler, String tag, boolean isNeedCache, long timeStamp) {
        StringBuilder pars = new StringBuilder();
        Request request = null;
        Builder builder = new Builder();
        MultipartBuilder multipartBuilder = null;
        if (values != null && !values.isEmpty()) {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String entryKey = entry.getKey();
                Object entryValue = entry.getValue();
                if (requestType == REQUEST_TYPE_POST) {
                    if (multipartBuilder == null) {
                        multipartBuilder = new MultipartBuilder()
                                .type(MultipartBuilder.FORM);
                    }
                    if (entryValue instanceof ArrayList) {
                        ArrayList<Object> list = (ArrayList<Object>) entryValue;
                        for (Object object : list) {
                            if (object instanceof File) {
                                multipartBuilder.addFormDataPart(entryKey,
                                        ((File) object).getName(), RequestBody
                                                .create(MultipartBuilder.FORM,
                                                        (File) object));
                            } else {
                                multipartBuilder.addFormDataPart(entryKey,
                                        String.valueOf(object));
                            }
                        }
                    } else {
                        if (entryValue instanceof File) {
                            multipartBuilder.addFormDataPart(entryKey,
                                    ((File) entryValue).getName(), RequestBody
                                            .create(MultipartBuilder.FORM,
                                                    (File) entryValue));
                        } else {
                            multipartBuilder.addFormDataPart(entryKey,
                                    String.valueOf(entryValue));
                        }
                    }
                } else {
                    pars.append(entryKey).append("=").append(entryValue)
                            .append("&");
                }
            }
        }
        RequestBody multipartBody = null;
        try {
            if (multipartBuilder != null)
                multipartBody = requestBodyWithContentLength(multipartBuilder
                        .build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestType == REQUEST_TYPE_GET) {
            builder.url((TextUtils.isEmpty(baseUrl)?"":baseUrl) + path + "?" + pars);
        } else {
            builder.url((TextUtils.isEmpty(baseUrl)?"":baseUrl) + path);
            builder.post(multipartBody);
        }
        //builder.addHeader("appInfo", CommonFunction.getInformation(context));
        request = builder.build();
        CookieHandler cookieHandler = mOkHttpClient.getCookieHandler();
        if (cookieHandler != null) {
            Map<String, List<String>> headers = OkHeaders.toMultimap(request.headers(), null);
            try {
                Map<String, List<String>> cookies = cookieHandler.get(request.uri(), headers);
                OkHeaders.addCookies(builder, cookies);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            mOkHttpClient.setCookieHandler(cookieManager);
        }
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            String res = removeFirstPrefix(response.body().string()); 
            LogUtils.error("response",res);
            return res;
        } catch (SocketTimeoutException ex) {
            return "" + ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT;
        } catch (ConnectTimeoutException e) {
            return "" + ConstantUtil.REQUEST_FAILTURECODE.CONNECTED_REFRUSH;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private RequestBody requestBodyWithContentLength(
            final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            throw new IOException("Unable to copy RequestBody");
        }
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                MediaType mediaType = MediaType.parse(requestBody.contentType()
                        + "; charset=utf-8");
                return mediaType;
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(ByteString.read(buffer.inputStream(),
                        (int) buffer.size()));
            }
        };
    }

    /**
     * method desc： 过滤掉服务器返回数据的特殊字符，这会影响到数据解析
     *
     * @param json
     * @return
     */
    private String removeFirstPrefix(String json) {
        if (!TextUtils.isEmpty(json)) {
            if (json.startsWith(CHARSET_PREFIX)) {
                json = json.substring(1);
            }
        }
        return json;
    }

    /**
     * 打印请求参数
     */
    private String getMap(Map<String, Object> map) {
        String str = "";
        if (map != null) {
            StringBuffer sb = new StringBuffer();
            Object[] os = map.keySet().toArray();
            for (int i = 0; i < os.length; i++) {
                Object key = os[i];
                Object value = map.get(key);
                if (key != null && value != null) {
                    sb.append(key + "=" + value.toString()).append("&");
                }
            }
            str = sb.toString();
        }
        return str;
    }

    public Object sendJsonRequestFromOkHttpClient(String path) {
        Request request = null;
        Builder builder = new Builder();
        request = builder.url(path).build();
        CookieHandler cookieHandler = mOkHttpClient.getCookieHandler();
        if (cookieHandler != null) {
            Map<String, List<String>> headers = OkHeaders.toMultimap(request.headers(), null);
            try {
                Map<String, List<String>> cookies = cookieHandler.get(request.uri(), headers);
                OkHeaders.addCookies(builder, cookies);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            mOkHttpClient.setCookieHandler(cookieManager);
        }

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            return response.body().bytes();
        } catch (SocketTimeoutException ex) {
            return "" + ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT;
        } catch (ConnectTimeoutException e) {
            return "" + ConstantUtil.REQUEST_FAILTURECODE.CONNECTED_REFRUSH;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}

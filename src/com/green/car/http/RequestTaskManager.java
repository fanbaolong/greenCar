package com.green.car.http;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;

import com.green.car.util.LogUtils;

/**
 * 请求管理类
 * 
 * @author chengbo
 */
public class RequestTaskManager implements IRequestTask {
	/**
	 * get请求的方式
	 * 
	 * @param context
	 * @param url
	 * @param values
	 * @param mHandler
	 */
	public void requestDataByGet(Context context, String tag, String url, Map<String, Object> values, RequestHandler mHandler) {
		requestDataByGet(context, false, tag, url, values, mHandler);
	}

	public void requestDataByGet(Context context, String tag, String url, Map<String, Object> values, RequestHandler mHandler, long timeStamp) {
		requestDataByGet(context, false, tag, url, values, mHandler, NetManager.REQUEST_TYPE_GET, true, timeStamp);
	}

	/**
	 * get请求的方式
	 * 
	 * @param context
	 * @param isShowProgressBar
	 * @param url
	 * @param values
	 * @param mHandler
	 */
	public void requestDataByGet(Context context, boolean isShowProgressBar, String tag, String url, Map<String, Object> values, RequestHandler mHandler) {
		requestDataByGet(context, isShowProgressBar, tag, url, values, mHandler, true);
	}

	public void requestDataByGet(Context context, boolean isShowProgressBar, String tag, String url, Map<String, Object> values, RequestHandler mHandler,
			boolean isNeedThread) {
		requestDataByGet(context, isShowProgressBar, tag, url, values, mHandler, NetManager.REQUEST_TYPE_GET, isNeedThread, 0);
	}

	public void requestDataByGet(Context context, boolean isShowProgressBar, String tag, String url, Map<String, Object> values, RequestHandler mHandler,
			int requestType, boolean isNeedThread, long timeStamp) {
		requestData(context, isShowProgressBar, tag, url, values, mHandler, requestType, isNeedThread, timeStamp);
	}

	public void requestDataByPost(Context context, String url, String tag, Map<String, Object> values, RequestHandler mHandler) {
		requestDataByPost(context, false, url, tag, values, mHandler);
	}

	public void requestDataByPost(Context context, boolean isShowProgressBar, String url, String tag, Map<String, Object> values, RequestHandler mHandler) {
		requestDataByPost(context, isShowProgressBar, url, tag, values, mHandler, true);
	}

	public void requestDataByPost(Context context, boolean isShowProgressBar, String url, String tag, Map<String, Object> values, RequestHandler mHandler,
			boolean isNeedThread) {
		requestDataByPost(context, isShowProgressBar, url, tag, values, mHandler, isNeedThread, 0);
	}

	public void requestDataByPost(Context context, boolean isShowProgressBar, String url, String tag, Map<String, Object> values, RequestHandler mHandler,
			long timeStamp) {
		requestDataByPost(context, false, url, tag, values, mHandler, true, timeStamp);
	}

	/**
	 * post请求的方式
	 * 
	 * @param context
	 * @param isShowProgressBar
	 * @param url
	 * @param values
	 * @param mHandler
	 */
	public void requestDataByPost(Context context, boolean isShowProgressBar, String url, String tag, Map<String, Object> values, RequestHandler mHandler,
			boolean isNeedThread, long timeStamp) {
		requestData(context, isShowProgressBar, tag, url, values, mHandler, NetManager.REQUEST_TYPE_POST, isNeedThread, timeStamp);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	public void requestData(Context context, boolean isShowProgressBar, String tag, String url, Map<String, Object> values, RequestHandler mHandler,
			int requestType, boolean isNeedThread, long timeStamp) {
		/** 添加基础的请求t */
		if (values == null) {
			values = new HashMap<String, Object>();
//			values.put("t", Constants.TOKEN);
		} else {
			if (!getHasKey(values, "t")) {
//				values.put("t", Constants.TOKEN);
			}
		}
		/** 打印请求数据 */
		LogUtils.error("request", (TextUtils.isEmpty(ConstantUtil.URL) ? "" : ConstantUtil.URL) + url + "?" + getMap(values));
		if (NetCheckUtil.isNetOk(context)) {// 连网状态是否能取到新的数据
			if (isNeedThread) {
				RequestTask task = new RequestTask(context, isShowProgressBar, tag, url, requestType, mHandler, timeStamp);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, values);
				} else {
					task.execute(values);
				}
				requestTasks.put(tag, task);
			} else {
				Object result = NetManager.getInstant(context).sendJsonRequestFromOkHttpClient(context, requestType, url, values, mHandler, tag);
				if (mHandler != null) {
					Message message = CommonFunction.getMessage(ConstantUtil.SUCCESS, result, tag);
					mHandler.sendMessage(message);
				}
			}
		} else {// 断网状态是否能取到新的数据
			if (mHandler != null) {
				Object result = null;
				int code = ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_FAIL;
				Message message = CommonFunction.getMessage(code, result, tag);
				mHandler.sendMessage(message);
			}
			return;
		}
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

	private boolean getHasKey(Map<String, Object> map, String key) {
		if (map != null) {
			Object[] os = map.keySet().toArray();
			for (int i = 0; i < os.length; i++) {
				Object keys = os[i];
				if (TextUtils.equals(key, keys.toString())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 取消请求
	 */
	public void cancelRequest(String tag) {
		requestTasks.get(tag).cancel(true);
		requestTasks.remove(tag);
	}

	/**
	 * 取消全部的请求
	 */
	public void cancelAllRequest() {
		for (Map.Entry<String, RequestTask> entry : requestTasks.entrySet()) {
			entry.getValue().cancel(true);
		}
		requestTasks.clear();
	}
}

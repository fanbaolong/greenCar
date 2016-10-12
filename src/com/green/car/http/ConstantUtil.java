package com.green.car.http;

/**
 * Created by wangke on 16/5/11.
 */
public class ConstantUtil {
    public static String URL;
    public static String BASE_URL;

    public static class REQUEST_FAILTURECODE {
        // 网络连接失败
        public static final int NETWORK_CONNT_FAIL = 0x400;
        // 网络连接超时
        public static final int NETWORK_CONNT_TIMEOUT = 0x401;
        // 网络数据错误
        public static final int DATA_ERROR = 0x402;
        // 连接服务器失败
        public static final int CONNECTED_REFRUSH = 0x403;
        // 获取本地数据
        public static final int GET_LOCAL_DATA = 0x405;
    }

    // 数据请求成功
    public static final int SUCCESS = 0x200;
}

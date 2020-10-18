package com.was.minemvc.common;


/**
 * 应用配置
 */
public class Config {

    public static final boolean DEBUG = false;

    public static final boolean DEVELOPMENT = false; //   大修改接口等 功能时  为了防止小bug 需要更新app  做个标识

    // 外网地址(正式)
    public static final String EXTERNAL_NETWORK = "http://47.92.77.68/SchoolUniformProject/"; //正式服务器地址
    // 外网地址(测试)
    public static final String EXTERNAL_NETWORK_TEST = "http://47.92.77.68/SchoolUniformProjectTest/"; //测试服务器地址

    // 网络请求路径   EXTERNAL_NETWORK_TEST
    private static String NETWORK_PATH = EXTERNAL_NETWORK;


    // 支付地址(正式)
    public static final String EXTERNAL_PAY_NETWORK = "https://jhpay.jss.com.cn/"; //正式服务器地址
    // 支付地址(测试)
    public static final String EXTERNAL_PAY_NETWORK_TEST = "https://jhpaytest.nntest.cn/"; //测试服务器地址

    // 支付网络请求路径   EXTERNAL_NETWORK_TEST
    private static String NETWORK_PAY_PATH = EXTERNAL_PAY_NETWORK;

    public static String getNetworkPayPath() {
        return NETWORK_PAY_PATH;
    }

    /**
     * 得到网络路径
     *
     * @return
     */
    public static String getNetworkPath() {
        return NETWORK_PATH;
    }

    /**
     * 设置网络路径
     *
     * @param networkPath
     */
    public static void setNetworkPath(String networkPath) {
        NETWORK_PATH = networkPath;
    }


}

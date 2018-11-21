package com.cainiao.baselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检测手机网络是否可用和当前网络类型
 * @author lishuangxiang
 */
public class NetworkStateUtil {

    public static final String NO_NETWORK_CONNECTED="当前网络不可用，请检查网络连接再试";


    /**
     * 网络是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * WIFI是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 移动网络是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络连接类型
     *
     * @param context
     * @return -1 获取失败
     */
    public static int isConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mTypeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mTypeNetworkInfo != null && mTypeNetworkInfo.isAvailable()) {
                return mTypeNetworkInfo.getType();
            }
        }
        return -1;
    }
}

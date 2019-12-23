package com.unicloud.core.mvvm.net.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtil {
    const val INVALID_NETWORK_TYPE = -1
    const val MOBILE_NETWORK_TYPE = 0
    const val WIFI_NETWORK_TYPE = 1
    /**
     * 判断当前网络状态是否可用
     *
     * @param context context
     * @return 可用返回true, 否则false
     */
    fun isNetWorkAvailable(context: Context): Boolean {
        var hasWifoCon = false
        var hasMobileCon = false
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfos = cm.allNetworkInfo
        for (net in netInfos) {
            val type = net.typeName
            if (type.equals("WIFI", ignoreCase = true)) {
                if (net.isConnected) {
                    hasWifoCon = true
                    //					UIToastUtil.setToast(context, context.getResources().getString(R.string.getwificonn));
                }
            }
            if (type.equals("MOBILE", ignoreCase = true)) {
                if (net.isConnected) {
                    hasMobileCon = true
                    //					UIToastUtil.setToast(context, context.getResources().getString(R.string.getlocationconn));
                }
            }
        }
        return hasWifoCon || hasMobileCon
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context context
     * @return -1不可用，0移动网络，WIFI网络
     */
    fun getNetWorkType(context: Context?): Int {
        var mNetworkInfo: NetworkInfo? = null
        if (context != null) {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            mNetworkInfo = mConnectivityManager.activeNetworkInfo
        }
        return if (mNetworkInfo != null && mNetworkInfo.isAvailable) mNetworkInfo.type else INVALID_NETWORK_TYPE
    }

    /**
     * 当前是否为WIFI连接
     *
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 当前是否为移动网络
     *
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected && networkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * 获取apn 类型
     *
     * @param context context
     * @return 类型
     */
    fun getAPNType(context: Context): String? {
        val connMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo ?: return null
        return if (networkInfo.extraInfo == null) {
            null
        } else networkInfo.extraInfo.toLowerCase()
    }
}
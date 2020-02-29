package com.unicloud.core.mvvm.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.unicloud.core.mvvm.dialog.ConfirmDialog;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import static com.blankj.utilcode.util.Utils.runOnUiThread;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkManager extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "NetworkManager";

    private Context mContext;
    private OnNetworkChangedListener mOnNetworkChangedListener;
    private ConfirmDialog dialog;

    public NetworkManager(Context context) {
        mContext = context;
    }

    public void register(OnNetworkChangedListener onNetworkChangedListener) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            mOnNetworkChangedListener = onNetworkChangedListener;
            try {
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (!NetworkUtil.isNetWorkAvailable(mContext)) {
//                mOnNetworkChangedListener.unAvailable();
//            }
            if (networkInfo == null || !networkInfo.isConnected()) {
                mOnNetworkChangedListener.unAvailable();
            }
        }
    }

    public void unRegister() {
        mOnNetworkChangedListener = null;
    }

    public void showDialog() {
        if (ActivityUtils.getTopActivity() == null)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialog == null)
                        dialog = new ConfirmDialog(ActivityUtils.getTopActivity());
                    dialog.setContent("当前网络不可用，是否去设置？");
                    dialog.cancelClick("退出应用", new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            ActivityUtils.finishAllActivities(true);
                            return null;
                        }
                    });
                    dialog.confirmClick("去设置", new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent mIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            ActivityUtils.getTopActivity().startActivity(mIntent);
                            return null;
                        }
                    });
                    dialog.show();
                } catch (WindowManager.BadTokenException exception) {

                }
            }
        });
    }

    public void dismissDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        if (mOnNetworkChangedListener != null)
            mOnNetworkChangedListener.onAvailable();
        LogUtils.d(TAG, "--------------onAvailable");
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        LogUtils.d(TAG, "--------------onLosing");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        if (mOnNetworkChangedListener != null)
            mOnNetworkChangedListener.unAvailable();
        LogUtils.d(TAG, "--------------onLost");
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        LogUtils.d(TAG, "--------------onUnavailable");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        LogUtils.d(TAG, "--------------onCapabilitiesChanged");
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        LogUtils.d(TAG, "--------------onLinkPropertiesChanged");
    }

    public interface OnNetworkChangedListener {
        void onAvailable();

        void unAvailable();
    }
}
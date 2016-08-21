package com.thea.fordesign.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class NetworkConnState {

    /**
     * Detect the network connectivity.
     * @param context
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null)
                return info.isAvailable();
        }
        return false;
    }

    /**
     * Get the connected type.
     * @param context
     * @return
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getConnectType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null)
                return info.getType();
        }
        return -1;
    }
}

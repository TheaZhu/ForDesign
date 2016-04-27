package com.thea.fordesign.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class NetworkConnState {

    /**
     * Detect the network connectivity.
     * @param context
     * @return
     */
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

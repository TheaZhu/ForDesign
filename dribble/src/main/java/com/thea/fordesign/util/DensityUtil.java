package com.thea.fordesign.util;

import android.content.Context;

public class DensityUtil {  
	  
    public static int dp2px(Context context, float dpValue) {
    	if (context == null)
    		return 0;
    	if (context.getResources() == null)
    		return 0;

        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    public static int px2dp(Context context, float pxValue) {
    	if (context == null)
    		return 0;
    	if (context.getResources() == null)
    		return 0;

        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
}  
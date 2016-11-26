package com.thea.fordesign.util;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class StringUtil {

    public static boolean isGif(String url) {
        return url.endsWith(".gif") || url.endsWith(".GIF");
    }
}

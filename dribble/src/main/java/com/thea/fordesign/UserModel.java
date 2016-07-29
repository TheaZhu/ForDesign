package com.thea.fordesign;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserModel {
    public static final String SP_NAME = "sp_user_info";

    public static final String KEY_USER_SIGN_IN = "user_sign_in";
    public static final String KEY_DRIBBBLE_USER_ACCESS_TOKEN = "dribbble_access_token";

    private SharedPreferences sp;

    public UserModel(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public boolean getUserSignIn() {
        return sp.getBoolean(KEY_USER_SIGN_IN, false);
    }

    public void setUserSignIn(boolean signIn) {
        sp.edit().putBoolean(KEY_USER_SIGN_IN, signIn).apply();
    }

    public String getDribbbleUserAccessToken() {
        return sp.getString(KEY_DRIBBBLE_USER_ACCESS_TOKEN, null);
    }

    public void setDribbbleUserAccessToken(String accessToken) {
        sp.edit().putString(KEY_DRIBBBLE_USER_ACCESS_TOKEN, accessToken).apply();
    }

    public String getDribbbleAccessToken() {
        return getDribbbleUserAccessToken() != null ? getDribbbleUserAccessToken() :
                DribbbleConstant.AUTH_TYPE + DribbbleConstant.CLIENT_ACCESS_TOKEN;
    }
}

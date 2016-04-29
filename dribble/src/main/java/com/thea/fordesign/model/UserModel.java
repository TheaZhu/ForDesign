package com.thea.fordesign.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserModel {
    public static final String SP_NAME = "sp_user_info";

    public static final String KEY_DRIBBBLE_USER_ACCESS_TOKEN = "dribbble_access_token";

    private SharedPreferences sp;

    public UserModel(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public String getDribbbleUserAccessToken() {
        return sp.getString(KEY_DRIBBBLE_USER_ACCESS_TOKEN, null);
    }

    public void setDribbbleUserAccessToken(String accessToken) {
        sp.edit().putString(KEY_DRIBBBLE_USER_ACCESS_TOKEN, accessToken).apply();
    }
}

package com.thea.fordesign;

import android.content.Context;
import android.content.SharedPreferences;

import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.config.DribbbleConstant;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserModel {
    public static final String SP_NAME = "sp_user_info";

    public static final String KEY_USER_SIGN_IN = "user_sign_in";
    public static final String KEY_DRIBBBLE_USER_ACCESS_TOKEN = "dribbble_access_token";
    public static final String KEY_DRIBBBLE_USER_TYPE = "dribbble_user_type";
    public static final String KEY_DRIBBBLE_USER_PRO = "dribbble_user_pro";
    public static final String KEY_DRIBBBLE_USER_CAN_UPLOAD_SHOT = "dribbble_user_can_upload_shot";

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

    public void setDribbbleUser(DribbbleUser user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_DRIBBBLE_USER_CAN_UPLOAD_SHOT, user.isCanUploadShot());
        editor.putString(KEY_DRIBBBLE_USER_TYPE, user.getType());
        editor.putBoolean(KEY_DRIBBBLE_USER_PRO, user.isPro()).apply();
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

    public boolean isCanUploadShot() {
        return sp.getBoolean(KEY_DRIBBBLE_USER_CAN_UPLOAD_SHOT, false);
    }

    public boolean isPro() {
        return sp.getBoolean(KEY_DRIBBBLE_USER_PRO, false);
    }

    public String getUserType() {
        return sp.getString(KEY_DRIBBBLE_USER_TYPE, "User");
    }

    public boolean canCreate() {
        return getUserType().equalsIgnoreCase("Player") || getUserType().equalsIgnoreCase("Team");
    }
}

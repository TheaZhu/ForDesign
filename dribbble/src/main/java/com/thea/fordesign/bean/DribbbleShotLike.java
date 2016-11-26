package com.thea.fordesign.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleShotLike {

    private int id;
    @SerializedName("created_at")
    private String createdTime;

    private DribbbleUser user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public DribbbleUser getUser() {
        return user;
    }

    public void setUser(DribbbleUser user) {
        this.user = user;
    }
}

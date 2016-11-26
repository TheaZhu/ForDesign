package com.thea.fordesign.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleFollower {

    private int id;
    @SerializedName("created_at")
    private String createdTime;
    @SerializedName(value = "follower", alternate = {"followee"})
    private DribbbleUser follower;

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

    public DribbbleUser getFollower() {
        return follower;
    }

    public void setFollower(DribbbleUser follower) {
        this.follower = follower;
    }
}

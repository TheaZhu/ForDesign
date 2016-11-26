package com.thea.fordesign.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleUserLike {

    private int id;
    @SerializedName("created_at")
    private String createdTime;

    private DribbbleShot shot;

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

    public DribbbleShot getShot() {
        return shot;
    }

    public void setShot(DribbbleShot shot) {
        this.shot = shot;
    }
}

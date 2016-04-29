package com.thea.fordesign.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleBucket {

    /**
     * id : 2754
     * name : Great Marks
     * description : Collecting superb brand marks from the
     * <a href="https://dribbble.com">Dribbble verse</a>.
     * shots_count : 251
     * created_at : 2011-05-20T21:05:55Z
     * updated_at : 2014-02-21T16:37:12Z
     */

    private int id;
    private String name;
    private String description;
    private DribbbleUser user;

    @SerializedName("shots_count")
    private int shotsCount;

    @SerializedName("created_at")
    private String createdTime;
    @SerializedName("updated_at")
    private String updatedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DribbbleUser getUser() {
        return user;
    }

    public void setUser(DribbbleUser user) {
        this.user = user;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public void setShotsCount(int shotsCount) {
        this.shotsCount = shotsCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}

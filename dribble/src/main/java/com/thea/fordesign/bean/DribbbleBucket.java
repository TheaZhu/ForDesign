package com.thea.fordesign.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.thea.fordesign.BR;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleBucket extends BaseObservable {

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

    @Bindable
    public int getShotsCount() {
        return shotsCount;
    }

    public void setShotsCount(int shotsCount) {
        this.shotsCount = shotsCount;
        notifyPropertyChanged(BR.shotsCount);
    }

    public void increaseShotsCount() {
        shotsCount++;
        notifyPropertyChanged(BR.shotsCount);
    }

    public void decreaseShotsCount() {
        shotsCount--;
        notifyPropertyChanged(BR.shotsCount);
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

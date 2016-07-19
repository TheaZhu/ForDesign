package com.thea.fordesign.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleAttachment {

    private int id;
    private String url;
    @SerializedName("thumbnail_url")
    private String thumbnailUrl;
    private int size;
    @SerializedName("content_type")
    private String contentType;
    @SerializedName("views_count")
    private int viewsCount;
    @SerializedName("created_at")
    private String createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}

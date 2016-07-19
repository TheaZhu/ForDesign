package com.thea.fordesign.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleTeam implements Parcelable {

    private int id;
    private String name;
    private String username;
    private String bio;
    private String location;
    private String type;
    private boolean pro;

    private DribbbleUser.Links links;

    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("buckets_count")
    private int bucketsCount;
    @SerializedName("buckets_url")
    private String bucketsUrl;

    @SerializedName("comments_received_count")
    private int commentsReceivedCount;

    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("followings_count")
    private int followingsCount;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;

    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("likes_received_count")
    private int likesReceivedCount;
    @SerializedName("likes_url")
    private String likesUrl;

    @SerializedName("members_count")
    private int membersCount;
    @SerializedName("members_url")
    private String membersUrl;

    @SerializedName("projects_count")
    private int projectsCount;
    @SerializedName("rebounds_received_count")
    private int reboundsReceivedCount;

    @SerializedName("shots_count")
    private int shotsCount;
    @SerializedName("can_upload_shot")
    private boolean canUploadShot;
    @SerializedName("shots_url")
    private String shotsUrl;
    @SerializedName("team_shots_url")
    private String teamShotsUrl;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DribbbleUser.Links getLinks() {
        return links;
    }

    public void setLinks(DribbbleUser.Links links) {
        this.links = links;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    public int getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getLikesReceivedCount() {
        return likesReceivedCount;
    }

    public void setLikesReceivedCount(int likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        this.projectsCount = projectsCount;
    }

    public int getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    public void setReboundsReceivedCount(int reboundsReceivedCount) {
        this.reboundsReceivedCount = reboundsReceivedCount;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public void setShotsCount(int shotsCount) {
        this.shotsCount = shotsCount;
    }

    public boolean isCanUploadShot() {
        return canUploadShot;
    }

    public void setCanUploadShot(boolean canUploadShot) {
        this.canUploadShot = canUploadShot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public String getBucketsUrl() {
        return bucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public String getLikesUrl() {
        return likesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    public String getMembersUrl() {
        return membersUrl;
    }

    public void setMembersUrl(String membersUrl) {
        this.membersUrl = membersUrl;
    }

    public String getShotsUrl() {
        return shotsUrl;
    }

    public void setShotsUrl(String shotsUrl) {
        this.shotsUrl = shotsUrl;
    }

    public String getTeamShotsUrl() {
        return teamShotsUrl;
    }

    public void setTeamShotsUrl(String teamShotsUrl) {
        this.teamShotsUrl = teamShotsUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeString(this.type);
        dest.writeByte(this.pro ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.links, flags);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.avatarUrl);
        dest.writeInt(this.bucketsCount);
        dest.writeString(this.bucketsUrl);
        dest.writeInt(this.commentsReceivedCount);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.likesReceivedCount);
        dest.writeString(this.likesUrl);
        dest.writeInt(this.membersCount);
        dest.writeString(this.membersUrl);
        dest.writeInt(this.projectsCount);
        dest.writeInt(this.reboundsReceivedCount);
        dest.writeInt(this.shotsCount);
        dest.writeByte(this.canUploadShot ? (byte) 1 : (byte) 0);
        dest.writeString(this.shotsUrl);
        dest.writeString(this.teamShotsUrl);
        dest.writeString(this.createdTime);
        dest.writeString(this.updatedTime);
    }

    public DribbbleTeam() {
    }

    protected DribbbleTeam(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.username = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.type = in.readString();
        this.pro = in.readByte() != 0;
        this.links = in.readParcelable(DribbbleUser.Links.class.getClassLoader());
        this.htmlUrl = in.readString();
        this.avatarUrl = in.readString();
        this.bucketsCount = in.readInt();
        this.bucketsUrl = in.readString();
        this.commentsReceivedCount = in.readInt();
        this.followersCount = in.readInt();
        this.followingsCount = in.readInt();
        this.followersUrl = in.readString();
        this.followingUrl = in.readString();
        this.likesCount = in.readInt();
        this.likesReceivedCount = in.readInt();
        this.likesUrl = in.readString();
        this.membersCount = in.readInt();
        this.membersUrl = in.readString();
        this.projectsCount = in.readInt();
        this.reboundsReceivedCount = in.readInt();
        this.shotsCount = in.readInt();
        this.canUploadShot = in.readByte() != 0;
        this.shotsUrl = in.readString();
        this.teamShotsUrl = in.readString();
        this.createdTime = in.readString();
        this.updatedTime = in.readString();
    }

    public static final Parcelable.Creator<DribbbleTeam> CREATOR = new Parcelable.Creator<DribbbleTeam>() {
        @Override
        public DribbbleTeam createFromParcel(Parcel source) {
            return new DribbbleTeam(source);
        }

        @Override
        public DribbbleTeam[] newArray(int size) {
            return new DribbbleTeam[size];
        }
    };
}

package com.thea.fordesign.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleUser implements Parcelable {

    private int id;
    private String name;
    private String username;
    private String bio;
    private String location;
    private String type;
    private boolean pro;

    private Links links;

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

    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;
    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("followings_count")
    private int followingsCount;

    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("likes_url")
    private String likesUrl;
    @SerializedName("likes_received_count")
    private int likesReceivedCount;

    @SerializedName("projects_count")
    private int projectsCount;
    @SerializedName("rebounds_received_count")
    private int reboundsReceivedCount;

    @SerializedName("shots_count")
    private int shotsCount;
    @SerializedName("shots_url")
    private String shotsUrl;
    @SerializedName("can_upload_shot")
    private boolean canUploadShot;

    @SerializedName("teams_count")
    private int teamsCount;
    @SerializedName("teams_url")
    private String teamsUrl;

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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
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

    public int getBucketsCount() {
        return bucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    public String getBucketsUrl() {
        return bucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    public int getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
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

    public String getLikesUrl() {
        return likesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    public int getLikesReceivedCount() {
        return likesReceivedCount;
    }

    public void setLikesReceivedCount(int likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
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

    public String getShotsUrl() {
        return shotsUrl;
    }

    public void setShotsUrl(String shotsUrl) {
        this.shotsUrl = shotsUrl;
    }

    public boolean isCanUploadShot() {
        return canUploadShot;
    }

    public void setCanUploadShot(boolean canUploadShot) {
        this.canUploadShot = canUploadShot;
    }

    public int getTeamsCount() {
        return teamsCount;
    }

    public void setTeamsCount(int teamsCount) {
        this.teamsCount = teamsCount;
    }

    public String getTeamsUrl() {
        return teamsUrl;
    }

    public void setTeamsUrl(String teamsUrl) {
        this.teamsUrl = teamsUrl;
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
    public String toString() {
        return "DribbbleUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", pro=" + pro +
                ", links=" + links.toString() +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", bucketsCount=" + bucketsCount +
                ", bucketsUrl='" + bucketsUrl + '\'' +
                ", commentsReceivedCount=" + commentsReceivedCount +
                ", followersUrl='" + followersUrl + '\'' +
                ", followingUrl='" + followingUrl + '\'' +
                ", followersCount=" + followersCount +
                ", followingsCount=" + followingsCount +
                ", likesCount=" + likesCount +
                ", likesUrl='" + likesUrl + '\'' +
                ", likesReceivedCount=" + likesReceivedCount +
                ", projectsCount=" + projectsCount +
                ", reboundsReceivedCount=" + reboundsReceivedCount +
                ", shotsCount=" + shotsCount +
                ", shotsUrl='" + shotsUrl + '\'' +
                ", canUploadShot=" + canUploadShot +
                ", teamsCount=" + teamsCount +
                ", teamsUrl='" + teamsUrl + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }

    public static class Links implements Parcelable {
        private String web;
        private String twitter;

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.web);
            dest.writeString(this.twitter);
        }

        public Links() {
        }

        @Override
        public String toString() {
            return "Links{" +
                    "web='" + web + '\'' +
                    ", twitter='" + twitter + '\'' +
                    '}';
        }

        protected Links(Parcel in) {
            this.web = in.readString();
            this.twitter = in.readString();
        }

        public static final Creator<Links> CREATOR = new Creator<Links>() {
            @Override
            public Links createFromParcel(Parcel source) {
                return new Links(source);
            }

            @Override
            public Links[] newArray(int size) {
                return new Links[size];
            }
        };
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
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
        dest.writeInt(this.likesCount);
        dest.writeString(this.likesUrl);
        dest.writeInt(this.likesReceivedCount);
        dest.writeInt(this.projectsCount);
        dest.writeInt(this.reboundsReceivedCount);
        dest.writeInt(this.shotsCount);
        dest.writeString(this.shotsUrl);
        dest.writeByte(this.canUploadShot ? (byte) 1 : (byte) 0);
        dest.writeInt(this.teamsCount);
        dest.writeString(this.teamsUrl);
        dest.writeString(this.createdTime);
        dest.writeString(this.updatedTime);
    }

    public DribbbleUser() {
    }

    protected DribbbleUser(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.username = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.type = in.readString();
        this.pro = in.readByte() != 0;
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.htmlUrl = in.readString();
        this.avatarUrl = in.readString();
        this.bucketsCount = in.readInt();
        this.bucketsUrl = in.readString();
        this.commentsReceivedCount = in.readInt();
        this.followersUrl = in.readString();
        this.followingUrl = in.readString();
        this.followersCount = in.readInt();
        this.followingsCount = in.readInt();
        this.likesCount = in.readInt();
        this.likesUrl = in.readString();
        this.likesReceivedCount = in.readInt();
        this.projectsCount = in.readInt();
        this.reboundsReceivedCount = in.readInt();
        this.shotsCount = in.readInt();
        this.shotsUrl = in.readString();
        this.canUploadShot = in.readByte() != 0;
        this.teamsCount = in.readInt();
        this.teamsUrl = in.readString();
        this.createdTime = in.readString();
        this.updatedTime = in.readString();
    }

    public static final Parcelable.Creator<DribbbleUser> CREATOR = new Parcelable.Creator<DribbbleUser>() {
        @Override
        public DribbbleUser createFromParcel(Parcel source) {
            return new DribbbleUser(source);
        }

        @Override
        public DribbbleUser[] newArray(int size) {
            return new DribbbleUser[size];
        }
    };
}

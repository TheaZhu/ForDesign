package com.thea.fordesign.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.URLUtil;

import com.google.gson.annotations.SerializedName;
import com.thea.fordesign.DribbleConstant;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleShot implements Parcelable {

    private int id;
    private String title = "";
    private String description = "";
    private int width;
    private int height;
    private boolean animated;
    private List<String> tags;

    private Image images;
    private DribbbleUser user;
    private DribbbleTeam team;

    @SerializedName("views_count")
    private int viewsCount;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("attachments_count")
    private int attachmentsCount;
    @SerializedName("rebounds_count")
    private int reboundsCount;
    @SerializedName("buckets_count")
    private int bucketsCount;
    @SerializedName("created_at")
    private String createdTime;
    @SerializedName("updated_at")
    private String updatedTime;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("attachments_url")
    private String attachmentsUrl;
    @SerializedName("buckets_url")
    private String bucketsUrl;
    @SerializedName("comments_url")
    private String commentsUrl;
    @SerializedName("likes_url")
    private String likesUrl;
    @SerializedName("projects_url")
    private String projectsUrl;
    @SerializedName("rebounds_url")
    private String reboundsUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public DribbbleUser getUser() {
        return user;
    }

    public void setUser(DribbbleUser user) {
        this.user = user;
    }

    public DribbbleTeam getTeam() {
        return team;
    }

    public void setTeam(DribbbleTeam team) {
        this.team = team;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        this.bucketsCount = bucketsCount;
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

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAttachmentsUrl() {
        return attachmentsUrl;
    }

    public void setAttachmentsUrl(String attachmentsUrl) {
        this.attachmentsUrl = attachmentsUrl;
    }

    public String getBucketsUrl() {
        return bucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getLikesUrl() {
        return likesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        this.likesUrl = likesUrl;
    }

    public String getProjectsUrl() {
        return projectsUrl;
    }

    public void setProjectsUrl(String projectsUrl) {
        this.projectsUrl = projectsUrl;
    }

    public String getReboundsUrl() {
        return reboundsUrl;
    }

    public void setReboundsUrl(String reboundsUrl) {
        this.reboundsUrl = reboundsUrl;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        if (URLUtil.isHttpsUrl(images.hidpi) || URLUtil.isHttpUrl(images.hidpi))
            return images.hidpi;
        else if (URLUtil.isHttpsUrl(images.normal) || URLUtil.isHttpUrl(images.normal))
            return images.normal;
        else if (URLUtil.isHttpsUrl(images.teaser) || URLUtil.isHttpUrl(images.teaser))
            return images.teaser;
        return DribbleConstant.DEFAULT_SHOT_IMAGE_URL;
    }

    @Override
    public String toString() {
        return "DribbbleShot{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", animated=" + animated +
                ", tags=" + tags +
                ", images=" + images +
                ", user=" + user.toString() +
                ", team=" + team +
                ", viewsCount=" + viewsCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", attachmentsCount=" + attachmentsCount +
                ", reboundsCount=" + reboundsCount +
                ", bucketsCount=" + bucketsCount +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", attachmentsUrl='" + attachmentsUrl + '\'' +
                ", bucketsUrl='" + bucketsUrl + '\'' +
                ", commentsUrl='" + commentsUrl + '\'' +
                ", likesUrl='" + likesUrl + '\'' +
                ", projectsUrl='" + projectsUrl + '\'' +
                ", reboundsUrl='" + reboundsUrl + '\'' +
                '}';
    }

    public static class Image implements Parcelable {
        private String hidpi;
        private String normal;
        private String teaser;

        public String getHidpi() {
            return hidpi;
        }

        public void setHidpi(String hidpi) {
            this.hidpi = hidpi;
        }

        public String getNormal() {
            return normal;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getTeaser() {
            return teaser;
        }

        public void setTeaser(String teaser) {
            this.teaser = teaser;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.hidpi);
            dest.writeString(this.normal);
            dest.writeString(this.teaser);
        }

        public Image() {
        }

        protected Image(Parcel in) {
            this.hidpi = in.readString();
            this.normal = in.readString();
            this.teaser = in.readString();
        }

        public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
            @Override
            public Image createFromParcel(Parcel source) {
                return new Image(source);
            }

            @Override
            public Image[] newArray(int size) {
                return new Image[size];
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeByte(this.animated ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.tags);
        dest.writeParcelable(this.images, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.team, flags);
        dest.writeInt(this.viewsCount);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.commentsCount);
        dest.writeInt(this.attachmentsCount);
        dest.writeInt(this.reboundsCount);
        dest.writeInt(this.bucketsCount);
        dest.writeString(this.createdTime);
        dest.writeString(this.updatedTime);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.attachmentsUrl);
        dest.writeString(this.bucketsUrl);
        dest.writeString(this.commentsUrl);
        dest.writeString(this.likesUrl);
        dest.writeString(this.projectsUrl);
        dest.writeString(this.reboundsUrl);
    }

    public DribbbleShot() {
    }

    protected DribbbleShot(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.animated = in.readByte() != 0;
        this.tags = in.createStringArrayList();
        this.images = in.readParcelable(Image.class.getClassLoader());
        this.user = in.readParcelable(DribbbleUser.class.getClassLoader());
        this.team = in.readParcelable(DribbbleTeam.class.getClassLoader());
        this.viewsCount = in.readInt();
        this.likesCount = in.readInt();
        this.commentsCount = in.readInt();
        this.attachmentsCount = in.readInt();
        this.reboundsCount = in.readInt();
        this.bucketsCount = in.readInt();
        this.createdTime = in.readString();
        this.updatedTime = in.readString();
        this.htmlUrl = in.readString();
        this.attachmentsUrl = in.readString();
        this.bucketsUrl = in.readString();
        this.commentsUrl = in.readString();
        this.likesUrl = in.readString();
        this.projectsUrl = in.readString();
        this.reboundsUrl = in.readString();
    }

    public static final Parcelable.Creator<DribbbleShot> CREATOR = new Parcelable
            .Creator<DribbbleShot>() {
        @Override
        public DribbbleShot createFromParcel(Parcel source) {
            return new DribbbleShot(source);
        }

        @Override
        public DribbbleShot[] newArray(int size) {
            return new DribbbleShot[size];
        }
    };
}

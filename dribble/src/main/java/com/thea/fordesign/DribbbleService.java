package com.thea.fordesign;

import android.support.annotation.NonNull;

import com.thea.fordesign.bean.DribbbleAttachment;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.bean.DribbbleLike;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.bean.DribbbleUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface DribbbleService {

    @GET("authorize?client_id=" + DribbleConstant.CLIENT_ID)
    Call<String> authorize();

    @POST("token?client_id=" + DribbleConstant.CLIENT_ID + "&client_secret=" + DribbleConstant
            .CLIENT_SECRET)
    Call<String> postToken(@Query("code") String code);

    @GET("user")
    Call<DribbbleUser> getUser(@Header("Authorization") String authorization);

    @GET("user/buckets")
    Call<List<DribbbleBucket>> getUserBuckets(@Header("Authorization") String authorization);

    @GET("user/projects")
    Call<List<DribbbleProject>> getUserProjects(@Header("Authorization") String authorization);

    @GET("user/shots")
    Call<List<DribbbleShot>> getUserShots(@Header("Authorization") String authorization);

    @GET("user/teams")
    Call<List<DribbbleTeam>> getUserTeams(@Header("Authorization") String authorization);

    @GET("user/followers")
    Call<List<DribbbleUser>> getUserFollowers(@Header("Authorization") String authorization,
                                              @Query("page") int page, @Query("per_page") int
                                                      perPage);

    @GET("user/following")
    Call<List<DribbbleUser>> getUserFollowing(@Header("Authorization") String authorization);

    @GET("user/following/shots")
    Call<List<DribbbleShot>> getUserFollowingShots(@Header("Authorization") String authorization);

    @GET("user/following/{user}")
    Call checkIfFollowing(@Header("Authorization") String authorization, @Path("user") int userId);

    @GET("user/likes")
    Call<List<DribbbleLike>> getUserLikes(@Header("Authorization") String authorization);

    @GET("users/{user}")
    Call<DribbbleUser> getUser(@Header("Authorization") String authorization, @Path("user") int
            userId);

    @GET("users/{user}/buckets")
    Call<List<DribbbleBucket>> getUserBuckets(@Header("Authorization") String authorization,
                                              @Path("user") int userId);

    @GET("users/{user}/projects")
    Call<List<DribbbleProject>> getUserProjects(@Header("Authorization") String authorization,
                                                @Path("user") int userId);

    @GET("users/{user}/shots")
    Call<List<DribbbleShot>> getUserShots(@Header("Authorization") String authorization, @Path
            ("user") int userId);

    @GET("users/{user}/teams")
    Call<List<DribbbleTeam>> getUserTeams(@Header("Authorization") String authorization, @Path
            ("user") int userId);

    @GET("users/{user}/followers")
    Call<List<DribbbleUser>> getUserFollowers(@Header("Authorization") String authorization,
                                              @Path("user") int userId, @Query("page") int page,
                                              @Query("per_page") int perPage);

    @GET("users/{user}/following")
    Call<List<DribbbleUser>> getUserFollowing(@Header("Authorization") String authorization,
                                              @Path("user") int userId);

    @GET("users/{user}/likes")
    Call<List<DribbbleLike>> getUserLikes(@Header("Authorization") String authorization, @Path
            ("user") int userId);

    @GET("buckets/{bucket}")
    Call<DribbbleBucket> getBucket(@Header("Authorization") String authorization, @Path("bucket")
    int bucketId);

    @GET("buckets/{bucket}/shots")
    Call<List<DribbbleShot>> getBucketShots(@Header("Authorization") String authorization, @Path
            ("bucket") int bucketId);

    @GET("projects/{project}")
    Call<DribbbleProject> getProject(@Header("Authorization") String authorization, @Path
            ("project") int projectId);

    @GET("projects/{project}/shots")
    Call<List<DribbbleShot>> getProjectShots(@Header("Authorization") String authorization, @Path
            ("project") int projectId);

    @GET("shots")
    Call<List<DribbbleShot>> getShots(@Header("Authorization") String authorization, @NonNull
    @Query("list") String list, @NonNull @Query("timeframe") String timeFrame, @NonNull @Query
            ("date") String date, @NonNull @Query("sort") String sort);

    @GET("shots")
    Call<List<DribbbleShot>> getShots(@Header("Authorization") String authorization);

    @GET("shots/{shot}")
    Call<DribbbleShot> getShot(@Header("Authorization") String authorization, @Path("shot") int
            shotId);

    @GET("shots/{shot}/buckets")
    Call<List<DribbbleBucket>> getShotBuckets(@Header("Authorization") String authorization,
                                              @Path("shot") int shotId);

    @GET("shots/{shot}/projects")
    Call<List<DribbbleProject>> getShotProjects(@Header("Authorization") String authorization,
                                                @Path("shot") int shotId);

    @GET("shots/{shot}/rebounds")
    Call<List<DribbbleShot>> getShotRebounds(@Header("Authorization") String authorization, @Path
            ("shot") int shotId);

    @GET("shots/{shot}/attachments")
    Call<List<DribbbleAttachment>> getShotAttachments(@Header("Authorization") String
                                                              authorization, @Path("shot") int
                                                              shotId);

    @GET("shots/{shot}/attachments/{attachment}")
    Call<DribbbleAttachment> getShotAttachment(@Header("Authorization") String authorization,
                                               @Path("shot") int shotId, @Path("attachment") int
                                                       attachmentId);

    @GET("shots/{shot}/comments")
    Call<List<DribbbleComment>> getShotComments(@Header("Authorization") String authorization,
                                                @Path("shot") int shotId);

    @GET("shots/{shot}/comments/{comment}")
    Call<DribbbleComment> getShotComment(@Header("Authorization") String authorization, @Path
            ("shot") int shotId, @Path("comment") int
                                                 commentId);

    @GET("shots/{shot}/likes")
    Call<List<DribbbleLike>> getShotLikes(@Header("Authorization") String authorization, @Path
            ("shot") int shotId);

    @GET("shots/{shot}/like")
    Call<DribbbleLike> getShotLike(@Header("Authorization") String authorization, @Path("shot")
    int shotId);

    @GET("shots/{shot}/comments/{comment}/likes")
    Call<List<DribbbleLike>> getShotCommentLikes(@Header("Authorization") String authorization,
                                                 @Path("shot") int shotId, @Path("comment") int
                                                         commentId);

    @GET("shots/{shot}/comments/{comment}/like")
    Call<DribbbleLike> getShotCommentLike(@Header("Authorization") String authorization, @Path
            ("shot") int shotId, @Path("comment") int
                                                  commentId);

    @GET("teams/{team}/members")
    Call<List<DribbbleUser>> getTeamMembers(@Header("Authorization") String authorization, @Path
            ("team") int teamId);

    @GET("teams/{team}/shots")
    Call<List<DribbbleShot>> getTeamShots(@Header("Authorization") String authorization, @Path
            ("team") int teamId);

    @GET
    Call<List<DribbbleUser>> getUsers(@Header("Authorization") String authorization, @Url String
            url);

    @GET
    Call<List<DribbbleBucket>> getBuckets(@Header("Authorization") String authorization, @Url
    String url);

    @GET
    Call<List<DribbbleProject>> getProjects(@Header("Authorization") String authorization, @Url
    String url);

    @GET
    Call<List<DribbbleShot>> getShots(@Header("Authorization") String authorization, @Url String
            url);

    @GET
    Call<List<DribbbleTeam>> getTeams(@Header("Authorization") String authorization, @Url String
            url);

    class Builder {
        private Retrofit.Builder mRetrofitBuilder;

        public Builder() {
            mRetrofitBuilder = new Retrofit.Builder()
                    .baseUrl(DribbleConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
        }

        public Builder baseUrl(String baseUrl) {
            mRetrofitBuilder.baseUrl(baseUrl);
            return this;
        }

        public DribbbleService create() {
            return mRetrofitBuilder.build().create(DribbbleService.class);
        }
    }
}

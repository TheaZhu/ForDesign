package com.thea.fordesign;

import com.thea.fordesign.bean.AccessToken;
import com.thea.fordesign.bean.DribbbleAttachment;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleShotLike;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.bean.DribbbleUserLike;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @POST(DribbbleConstant.OAUTH + "token?client_id=" + DribbbleConstant.CLIENT_ID +
            "&client_secret=" + DribbbleConstant.CLIENT_SECRET)
    Call<AccessToken> postToken(@Query("code") String code);

    @GET("user")
    Call<DribbbleUser> getUser(@Header("Authorization") String authorization);

    @GET("user/buckets")
    Call<List<DribbbleBucket>> getUserBuckets(@Header("Authorization") String authorization);

    @GET("user/projects")
    Call<List<DribbbleProject>> getUserProjects(@Header("Authorization") String authorization,
                                                @Query("page") int page);

    @GET("user/shots")
    Call<List<DribbbleShot>> getUserShots(@Header("Authorization") String authorization);

    @GET("user/teams")
    Call<List<DribbbleTeam>> getUserTeams(@Header("Authorization") String authorization);

    @GET("user/followers")
    Call<List<DribbbleFollower>> getUserFollowers(@Header("Authorization") String authorization,
                                              @Query("page") int page,
                                              @Query("per_page") int perPage);

    @GET("user/following")
    Call<List<DribbbleFollower>> getUserFollowing(@Header("Authorization") String authorization);

    @GET("user/following/shots")
    Call<List<DribbbleShot>> getUserFollowingShots(@Header("Authorization") String authorization);

    @GET("user/following/{user}")
    Call checkIfFollowing(@Header("Authorization") String authorization, @Path("user") int userId);

    @GET("user/likes")
    Call<List<DribbbleUserLike>> getUserLikes(@Header("Authorization") String authorization,
                                              @Query("page") int page);

    @GET("users/{user}")
    Call<DribbbleUser> getUser(@Header("Authorization") String authorization,
                               @Path("user") int userId);

    @GET("users/{user}/buckets")
    Call<List<DribbbleBucket>> getUserBuckets(@Header("Authorization") String authorization,
                                              @Path("user") int userId);

    @GET("users/{user}/projects")
    Call<List<DribbbleProject>> getUserProjects(@Header("Authorization") String authorization,
                                                @Path("user") int userId,
                                                @Query("page") int page);

    @GET("users/{user}/shots")
    Call<List<DribbbleShot>> getUserShots(@Header("Authorization") String authorization,
                                          @Path("user") int userId);

    @GET("users/{user}/teams")
    Call<List<DribbbleTeam>> getUserTeams(@Header("Authorization") String authorization,
                                          @Path("user") int userId);

    @GET("users/{user}/followers")
    Call<List<DribbbleFollower>> getUserFollowers(@Header("Authorization") String authorization,
                                              @Path("user") int userId,
                                              @Query("page") int page,
                                              @Query("per_page") int perPage);

    @GET("users/{user}/following")
    Call<List<DribbbleFollower>> getUserFollowing(@Header("Authorization") String authorization,
                                              @Path("user") int userId);

    @GET("users/{user}/likes")
    Call<List<DribbbleUserLike>> getUserLikes(@Header("Authorization") String authorization,
                                              @Path("user") int userId,
                                              @Query("page") int page);

    @GET("buckets/{bucket}")
    Call<DribbbleBucket> getBucket(@Header("Authorization") String authorization,
                                   @Path("bucket") int bucketId);

    @GET("buckets/{bucket}/shots")
    Call<List<DribbbleShot>> getBucketShots(@Header("Authorization") String authorization,
                                            @Path("bucket") int bucketId);

    @GET("projects/{project}")
    Call<DribbbleProject> getProject(@Header("Authorization") String authorization,
                                     @Path("project") int projectId);

    @GET("projects/{project}/shots")
    Call<List<DribbbleShot>> getProjectShots(@Header("Authorization") String authorization,
                                             @Path("project") int projectId);

    @GET("shots")
    Call<List<DribbbleShot>> getShots(@Header("Authorization") String authorization,
                                      @Query("list") String list,
                                      @Query("sort") String sort,
                                      @Query("timeframe") String timeFrame,
                                      @Query("date") String date,
                                      @Query("page") int page,
                                      @Query("per_page") int perPage);

    @GET("shots/{shot}")
    Call<DribbbleShot> getShot(@Header("Authorization") String authorization,
                               @Path("shot") int shotId);

    @GET("shots/{shot}/buckets")
    Call<List<DribbbleBucket>> getShotBuckets(@Header("Authorization") String authorization,
                                              @Path("shot") int shotId);

    @GET("shots/{shot}/projects")
    Call<List<DribbbleProject>> getShotProjects(@Header("Authorization") String authorization,
                                                @Path("shot") int shotId);

    @GET("shots/{shot}/rebounds")
    Call<List<DribbbleShot>> getShotRebounds(@Header("Authorization") String authorization,
                                             @Path("shot") int shotId);

    @GET("shots/{shot}/attachments")
    Call<List<DribbbleAttachment>> getShotAttachments(@Header("Authorization") String authorization,
                                                      @Path("shot") int shotId);

    @GET("shots/{shot}/attachments/{attachment}")
    Call<DribbbleAttachment> getShotAttachment(@Header("Authorization") String authorization,
                                               @Path("shot") int shotId,
                                               @Path("attachment") int attachmentId);

    @GET("shots/{shot}/comments")
    Call<List<DribbbleComment>> getShotComments(@Header("Authorization") String authorization,
                                                @Path("shot") int shotId);

    @GET("shots/{shot}/comments/{comment}")
    Call<DribbbleComment> getShotComment(@Header("Authorization") String authorization,
                                         @Path("shot") int shotId,
                                         @Path("comment") int commentId);

    @GET("shots/{shot}/likes")
    Call<List<DribbbleShotLike>> getShotLikes(@Header("Authorization") String authorization,
                                              @Path("shot") int shotId);

    @GET("shots/{shot}/like")
    Call<DribbbleShotLike> getShotLike(@Header("Authorization") String authorization,
                                       @Path("shot") int shotId);

    @GET("shots/{shot}/comments/{comment}/likes")
    Call<List<DribbbleShotLike>> getShotCommentLikes(@Header("Authorization") String authorization,
                                                     @Path("shot") int shotId,
                                                     @Path("comment") int commentId);

    @GET("shots/{shot}/comments/{comment}/like")
    Call<DribbbleShotLike> getShotCommentLike(@Header("Authorization") String authorization,
                                              @Path("shot") int shotId,
                                              @Path("comment") int commentId);

    @GET("teams/{team}/members")
    Call<List<DribbbleUser>> getTeamMembers(@Header("Authorization") String authorization,
                                            @Path("team") int teamId);

    @GET("teams/{team}/shots")
    Call<List<DribbbleShot>> getTeamShots(@Header("Authorization") String authorization,
                                          @Path("team") int teamId);

    @GET
    Call<List<DribbbleUser>> getUsers(@Header("Authorization") String authorization,
                                      @Url String url);

    @GET
    Call<List<DribbbleFollower>> getUserFollowers(@Header("Authorization") String authorization,
                                                  @Url String url,
                                                  @Query("page") int page);

    @GET
    Call<List<DribbbleUserLike>> getUserLikes(@Header("Authorization") String authorization,
                                                  @Url String url,
                                                  @Query("page") int page);

    @GET
    Call<List<DribbbleBucket>> getBuckets(@Header("Authorization") String authorization,
                                          @Url String url,
                                          @Query("page") int page);

    @GET
    Call<List<DribbbleTeam>> getTeams(@Header("Authorization") String authorization,
                                          @Url String url,
                                          @Query("page") int page);

    @GET
    Call<List<DribbbleProject>> getProjects(@Header("Authorization") String authorization,
                                            @Url String url,
                                            @Query("page") int page);

    @GET
    Call<List<DribbbleShot>> getShots(@Header("Authorization") String authorization,
                                      @Url String url,
                                      @Query("page") int page);

    @GET
    Call<List<DribbbleShotLike>> getShotLikes(@Header("Authorization") String authorization,
                                      @Url String url,
                                      @Query("page") int page);

    @GET
    Call<List<DribbbleComment>> getShotComments(@Header("Authorization") String authorization,
                                                @Url String url,
                                                @Query("page") int page);

    @GET
    Call<List<DribbbleTeam>> getTeams(@Header("Authorization") String authorization,
                                      @Url String url);

    @POST("shots/{shot}/like")
    Call<DribbbleUserLike> likeShot(@Header("Authorization") String authorization,
                                    @Path("shot") int shotId);

    @FormUrlEncoded
    @POST("shots/{shot}/comments")
    Call<DribbbleComment> createComment(@Header("Authorization") String authorization,
                                        @Path("shot") int shotId,
                                        @Field("body") String body);

    class Builder {
        private Retrofit.Builder mRetrofitBuilder;

        public Builder() {
            mRetrofitBuilder = new Retrofit.Builder().baseUrl(DribbbleConstant.BASE_URL)
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

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
    Call<List<DribbbleBucket>> getUserBuckets();

    @GET("user/projects")
    Call<List<DribbbleProject>> getUserProjects();

    @GET("user/shots")
    Call<List<DribbbleShot>> getUserShots();

    @GET("user/teams")
    Call<List<DribbbleTeam>> getUserTeams();

    @GET("user/followers")
    Call<List<DribbbleUser>> getUserFollowers(@Query("page") int page, @Query("per_page") int
            perPage);

    @GET("user/following")
    Call<List<DribbbleUser>> getUserFollowing();

    @GET("user/following/shots")
    Call<List<DribbbleShot>> getUserFollowingShots();

    @GET("user/following/{user}")
    Call checkIfFollowing(@Path("user") int userId);

    @GET("user/likes")
    Call<List<DribbbleLike>> getUserLikes();

    @GET("users/{user}")
    Call<DribbbleUser> getUser(@Path("user") int userId);

    @GET("users/{user}/buckets")
    Call<List<DribbbleBucket>> getUserBuckets(@Path("user") int userId);

    @GET("users/{user}/projects")
    Call<List<DribbbleProject>> getUserProjects(@Path("user") int userId);

    @GET("users/{user}/shots")
    Call<List<DribbbleShot>> getUserShots(@Path("user") int userId);

    @GET("users/{user}/teams")
    Call<List<DribbbleTeam>> getUserTeams(@Path("user") int userId);

    @GET("users/{user}/followers")
    Call<List<DribbbleUser>> getUserFollowers(@Path("user") int userId, @Query("page") int page,
                                          @Query("per_page") int perPage);

    @GET("users/{user}/following")
    Call<List<DribbbleUser>> getUserFollowing(@Path("user") int userId);

    @GET("users/{user}/likes")
    Call<List<DribbbleLike>> getUserLikes(@Path("user") int userId);

    @GET("buckets/{bucket}")
    Call<DribbbleBucket> getBucket(@Path("bucket") int bucketId);

    @GET("buckets/{bucket}/shots")
    Call<List<DribbbleShot>> getBucketShots(@Path("bucket") int bucketId);

    @GET("projects/{project}")
    Call<DribbbleProject> getProject(@Path("project") int projectId);

    @GET("projects/{project}/shots")
    Call<List<DribbbleShot>> getProjectShots(@Path("project") int projectId);

    @GET("shots")
    Call<List<DribbbleShot>> getShots(@NonNull @Query("list") String list, @NonNull @Query
            ("timeframe") String timeFrame, @NonNull @Query("date") String date, @NonNull @Query
            ("sort") String sort);

    @GET("shots/{shot}")
    Call<DribbbleShot> getShot(@Path("shot") int shotId);

    @GET("shots/{shot}/buckets")
    Call<List<DribbbleBucket>> getShotBuckets(@Path("shot") int shotId);

    @GET("shots/{shot}/projects")
    Call<List<DribbbleProject>> getShotProjects(@Path("shot") int shotId);

    @GET("shots/{shot}/rebounds")
    Call<List<DribbbleShot>> getShotRebounds(@Path("shot") int shotId);

    @GET("shots/{shot}/attachments")
    Call<List<DribbbleAttachment>> getShotAttachments(@Path("shot") int shotId);

    @GET("shots/{shot}/attachments/{attachment}")
    Call<DribbbleAttachment> getShotAttachment(@Path("shot") int shotId, @Path("attachment") int
            attachmentId);

    @GET("shots/{shot}/comments")
    Call<List<DribbbleComment>> getShotComments(@Path("shot") int shotId);

    @GET("shots/{shot}/comments/{comment}")
    Call<DribbbleComment> getShotComment(@Path("shot") int shotId, @Path("comment") int
            commentId);

    @GET("shots/{shot}/likes")
    Call<List<DribbbleLike>> getShotLikes(@Path("shot") int shotId);

    @GET("shots/{shot}/like")
    Call<DribbbleLike> getShotLike(@Path("shot") int shotId);

    @GET("shots/{shot}/comments/{comment}/likes")
    Call<List<DribbbleLike>> getShotCommentLikes(@Path("shot") int shotId, @Path("comment") int
            commentId);

    @GET("shots/{shot}/comments/{comment}/like")
    Call<DribbbleLike> getShotCommentLike(@Path("shot") int shotId, @Path("comment") int
            commentId);

    @GET("teams/{team}/members")
    Call<List<DribbbleUser>> getTeamMembers(@Path("team") int teamId);

    @GET("teams/{team}/shots")
    Call<List<DribbbleShot>> getTeamShots(@Path("team") int teamId);

    @GET
    Call<List<DribbbleUser>> getUsers(@Url String url);

    @GET
    Call<List<DribbbleBucket>> getBuckets(@Url String url);

    @GET
    Call<List<DribbbleProject>> getProjects(@Url String url);

    @GET
    Call<List<DribbbleShot>> getShots(@Url String url);

    @GET
    Call<List<DribbbleTeam>> getTeams(@Url String url);
}

package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.config.DribbbleConstant;
import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class CommentsRepository implements CommentsDataSource {
    public static final String TAG = CommentsRepository.class.getSimpleName();

    private static CommentsRepository sInstance = null;

    private DribbbleService mService;

    Map<Integer, DribbbleComment> mCachedComments;
    boolean mCacheIsDirty = false;

    private CommentsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static CommentsRepository getInstance() {
        if (sInstance == null) {
            synchronized (CommentsRepository.class) {
                if (sInstance == null)
                    sInstance = new CommentsRepository();
            }
        }
        return sInstance;
    }

    @Override
    public void getComments(@NonNull String authorization, @Nullable String url, final int page,
                            final LoadCommentsCallback callback) {
        if (mCachedComments == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get comments");
            Call<List<DribbbleComment>> call = mService.getShotComments(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleComment>>() {
                @Override
                public void onResponse(Call<List<DribbbleComment>> call,
                                       Response<List<DribbbleComment>> response) {
                    LogUtil.i(TAG, "get comments code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleComment> comments = response.body();
                    if (comments != null) {
                        refreshCache(page, comments);
                        if (callback != null)
                            callback.onCommentsLoaded(comments);
                    } else if (callback != null)
                        callback.onDataNotAvailable();
                }

                @Override
                public void onFailure(Call<List<DribbbleComment>> call, Throwable t) {
                    LogUtil.i(TAG, "get shots call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onCommentsLoaded(new ArrayList<>(mCachedComments.values()));
        }
    }

    @Override
    public void getComment(@NonNull String authorization, int commentId, final GetCommentCallback
            callback) {
        DribbbleComment cachedComment = getCommentWithId(commentId);

        // Respond immediately with cache if available
        if (callback == null)
            return;
        if (cachedComment != null) {
            LogUtil.i(TAG, "get comment local: " + commentId);
            callback.onShotLoaded(cachedComment);
        } else {
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void saveComment(@NonNull DribbbleComment comment) {

    }

    @Override
    public void likeComment(@NonNull DribbbleComment comment) {

    }

    @Override
    public void likeComment(int commentId) {

    }

    @Override
    public void dislikeComment(@NonNull DribbbleComment comment) {

    }

    @Override
    public void dislikeComment(int commentId) {

    }

    @Override
    public void createComment(@NonNull String authorization, int shotId, String commentBody,
                              final CreateCommentCallback callback) {
        Call<DribbbleComment> call = mService.createComment(authorization, shotId, commentBody);
        call.enqueue(new Callback<DribbbleComment>() {
            @Override
            public void onResponse(Call<DribbbleComment> call, Response<DribbbleComment> response) {
                LogUtil.i(TAG, "create comment code: " + response.code() + ", message: " +
                        response.message());
                if (response.code() == DribbbleConstant.CODE_CREATED) {
                    if (callback != null)
                        callback.onCommentCreated(response.body());
                } else if (callback != null)
                    callback.onFailed(response.code(), response.message());
            }

            @Override
            public void onFailure(Call<DribbbleComment> call, Throwable t) {
                LogUtil.i(TAG, "create comment call executed: " + call.isExecuted() + ", url: " +
                        call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFailed(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @Override
    public void refreshComment() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllComment() {
        if (mCachedComments == null) {
            mCachedComments = new LinkedHashMap<>();
        }
        mCachedComments.clear();
    }

    @Override
    public void deleteComment(int commentId) {
        mCachedComments.remove(commentId);
    }

    private void refreshCache(int page, List<DribbbleComment> comments) {
        if (mCachedComments == null) {
            mCachedComments = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedComments.clear();
        for (DribbbleComment comment : comments) {
            mCachedComments.put(comment.getId(), comment);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleComment getCommentWithId(int id) {
        if (mCachedComments == null || mCachedComments.isEmpty()) {
            return null;
        } else {
            return mCachedComments.get(id);
        }
    }
}

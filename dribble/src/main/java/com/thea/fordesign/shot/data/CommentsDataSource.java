package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleComment;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface CommentsDataSource {

    interface LoadCommentsCallback {

        void onCommentsLoaded(List<DribbbleComment> comments);

        void onDataNotAvailable();
    }

    interface GetCommentCallback {

        void onShotLoaded(DribbbleComment comment);

        void onDataNotAvailable();
    }

    interface CreateCommentCallback {

        void onCommentCreated(DribbbleComment comment);

        void onFailed(int errCode, String message);
    }

    void getComments(@NonNull String authorization, @Nullable String url, int page, LoadCommentsCallback callback);

    void getComment(@NonNull String authorization, int commentId, GetCommentCallback callback);

    void saveComment(@NonNull DribbbleComment comment);

    void likeComment(@NonNull DribbbleComment comment);

    void likeComment(int commentId);

    void dislikeComment(@NonNull DribbbleComment comment);

    void dislikeComment(int commentId);

    void createComment(@NonNull String authorization, int shotId, String commentBody,
                       CreateCommentCallback
            callback);

    void refreshComment();

    void deleteAllComment();

    void deleteComment(int commentId);

}

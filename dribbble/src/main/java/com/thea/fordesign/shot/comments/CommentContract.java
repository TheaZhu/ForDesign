package com.thea.fordesign.shot.comments;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.bean.DribbbleUser;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface CommentContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showComments(List<DribbbleComment> comments);

        void insertComments(List<DribbbleComment> comments);

        void showCommentLiked();

        void showCommentDisliked();

        void showUserDetailsUi(@NonNull DribbbleUser requestedUser, android.view.View v);

        String getNewComment();

        void setCanAddComment(boolean canAddComment);
    }

    interface Presenter extends BasePresenter {

        void loadComments();

        void loadMore(int page);

        void openUserDetails(@NonNull DribbbleUser requestedUser, android.view.View v);

        void likeComment(@NonNull DribbbleComment comment, boolean isCommented);

        void addComment();
    }
}

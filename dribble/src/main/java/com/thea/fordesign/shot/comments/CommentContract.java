package com.thea.fordesign.shot.comments;

import android.support.annotation.NonNull;

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

        void showComments(List<DribbbleComment> comments);

        void showCommentLiked();

        void showCommentDisliked();

        void showUserDetailsUi(int userId, android.view.View v);

        String getNewComment();

    }

    interface Presenter extends BasePresenter {

        void getComments(String url);

        void openUserDetails(@NonNull DribbbleUser requestedUser, android.view.View v);

        void likeComment(@NonNull DribbbleComment comment, boolean isCommented);

        void addComment();

        String formatTime(String timeStr);
    }
}

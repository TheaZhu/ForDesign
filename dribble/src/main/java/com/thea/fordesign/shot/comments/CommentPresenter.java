package com.thea.fordesign.shot.comments;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.shot.data.CommentsDataSource;
import com.thea.fordesign.shot.data.CommentsRepository;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class CommentPresenter implements CommentContract.Presenter {

    private final CommentContract.View mCommentView;
    private final CommentsRepository mRepository;

    public CommentPresenter(CommentContract.View commentView) {
        mCommentView = Preconditions.checkNotNull(commentView, "commentView cannot be null");
        mRepository = CommentsRepository.getInstance();
    }

    @Override
    public void getComments(String url) {
        mRepository.refreshComment();
        mRepository.getComments(url, new CommentsDataSource
                .LoadCommentsCallback() {

            @Override
            public void onCommentsLoaded(List<DribbbleComment> comments) {
                if (comments != null)
                    mCommentView.showComments(comments);
                else
                    mCommentView.showSnack(R.string.error_load_shot_comments);
            }

            @Override
            public void onDataNotAvailable() {
                mCommentView.showSnack(R.string.error_load_shot_comments);
            }
        });
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mCommentView.showUserDetailsUi(requestedUser.getId(), v);
    }

    @Override
    public void likeComment(DribbbleComment comment, boolean isCommented) {
        mCommentView.showSnack("登录功能开发中");
    }

    @Override
    public void addComment() {
        mCommentView.showSnack("登录功能开发中");
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void start() {
    }
}

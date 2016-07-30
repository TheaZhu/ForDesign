package com.thea.fordesign.shot.comments;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
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
    public static final String TAG = CommentPresenter.class.getSimpleName();

    private final CommentContract.View mCommentView;
    private final CommentsRepository mRepository;

    private UserModel mUserModel;
    private String mCommentsUrl;
    private int mShotId;

    public CommentPresenter(@NonNull CommentContract.View commentView, int shotId, String url,
                            @NonNull UserModel userModel) {
        mCommentView = Preconditions.checkNotNull(commentView, "commentView cannot be null");
        mRepository = CommentsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mCommentsUrl = url;
        mShotId = shotId;

        mCommentView.setPresenter(this);
    }

    @Override
    public void loadComments() {
        mCommentView.setRefreshingIndicator(true);
        mRepository.refreshComment();
        mRepository.getComments(mUserModel.getDribbbleAccessToken(), mCommentsUrl, 1, new
                CommentsDataSource.LoadCommentsCallback() {
                    @Override
                    public void onCommentsLoaded(List<DribbbleComment> comments) {
                        mCommentView.setRefreshingIndicator(false);
                        if (comments != null)
                            mCommentView.showComments(comments);
                        else
                            mCommentView.showSnack(R.string.error_load_shot_comments);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mCommentView.setRefreshingIndicator(false);
                        mCommentView.showSnack(R.string.error_load_shot_comments);
                    }
                });
    }

    @Override
    public void loadMore(int page) {
        mCommentView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshComment();

        mRepository.getComments(mUserModel.getDribbbleAccessToken(), mCommentsUrl, page, new
                CommentsDataSource.LoadCommentsCallback() {

                    @Override
                    public void onCommentsLoaded(List<DribbbleComment> comments) {
                        mCommentView.setLoadingIndicator(false, false, R.string.loading, false);
                        mCommentView.insertComments(comments);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mCommentView.setLoadingIndicator(true, false,
                                R.string.loading_error, true);
                        mCommentView.setLoadingError();
                    }
                });
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mCommentView.showUserDetailsUi(requestedUser, v);
    }

    @Override
    public void likeComment(@NonNull DribbbleComment comment, boolean isCommented) {
        mCommentView.showSnack("功能开发中");
    }

    @Override
    public void addComment() {
        String comment = mCommentView.getNewComment();
        if (TextUtils.isEmpty(comment))
            mCommentView.showSnack(R.string.msg_empty_comment);
        else {
            mRepository.createComment(mUserModel.getDribbbleUserAccessToken(), mShotId, comment, new
                    CommentsDataSource.CreateCommentCallback() {

                        @Override
                        public void onCommentCreated(DribbbleComment comment) {
                            mCommentView.showSnack(R.string.msg_empty_comment);
                        }

                        @Override
                        public void onFailed(int errCode, String message) {
                            mCommentView.showSnack(R.string.msg_add_comment_fail);
                        }
                    });
        }
    }

    @Override
    public void start() {
        mCommentView.setCanAddComment(mUserModel.canCreate());
    }
}

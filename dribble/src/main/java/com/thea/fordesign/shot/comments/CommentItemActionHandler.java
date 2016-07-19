package com.thea.fordesign.shot.comments;

import android.databinding.BaseObservable;
import android.view.View;

import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.bean.DribbbleUser;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class CommentItemActionHandler extends BaseObservable {
    public static final String TAG = CommentItemActionHandler.class.getSimpleName();

    private CommentContract.Presenter mListener;
    private boolean isCommented = false;

    public CommentItemActionHandler(CommentContract.Presenter listener) {
        mListener = listener;
    }

    public void likeChanged(DribbbleComment comment) {
        isCommented = !isCommented;
        mListener.likeComment(comment, isCommented);
    }

    public void userClicked(DribbbleUser user, View v) {
        mListener.openUserDetails(user, v);
    }

}

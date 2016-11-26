package com.thea.fordesign.setting.about;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.thea.fordesign.R;
import com.thea.fordesign.setting.feedback.FeedbackActivity;
import com.thea.fordesign.setting.licenses.LicensesActivity;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */

public class AboutPresenter implements AboutContract.Presenter {
    private final AboutContract.View mView;

    public AboutPresenter(@NonNull AboutContract.View view) {
        mView = Preconditions.checkNotNull(view, "view cannot be null");
    }

    @Override
    public void checkUpdate() {
        mView.showSnack(R.string.msg_is_the_latest_version);
    }

    @Override
    public void openVersionIllustration() {

    }

    @Override
    public void openFeedback(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    @Override
    public void openStar() {

    }

    @Override
    public void openCopyrightInformation(Context context) {
        context.startActivity(new Intent(context, LicensesActivity.class));
    }

    @Override
    public void start() {
    }
}

package com.thea.fordesign.team.teams;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.team.TeamItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface TeamsContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showTeams(List<DribbbleTeam> teams);

        void insertTeams(List<DribbbleTeam> teams);

        void showTeamDetailsUi(@NonNull DribbbleTeam team, android.view.View v);

    }

    interface Presenter extends TeamItemPresenter {

        void loadTeams();

        void loadMore(int page);

    }
}

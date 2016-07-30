package com.thea.fordesign.team.teams;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.team.data.TeamsDataSource;
import com.thea.fordesign.team.data.TeamsRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class TeamsPresenter implements TeamsContract.Presenter {
    public static final String TAG = TeamsPresenter.class.getSimpleName();

    private final TeamsContract.View mTeamsView;
    private final UserModel mUserModel;
    private String mTeamsUrl;

    private final TeamsRepository mRepository;

    public TeamsPresenter(TeamsContract.View teamsView, String teamsUrl, @NonNull UserModel
            userModel) {
        mTeamsView = Preconditions.checkNotNull(teamsView, "teamsView cannot be null");
        mRepository = TeamsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mTeamsUrl = teamsUrl;

        mTeamsView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadTeams() {
        mTeamsView.setRefreshingIndicator(true);
        mRepository.refreshTeams();

        mRepository.getTeams(mUserModel.getDribbbleAccessToken(), mTeamsUrl, 1, new
                TeamsDataSource.LoadTeamsCallback() {
                    @Override
                    public void onTeamsLoaded(List<DribbbleTeam> teams) {
                        mTeamsView.setRefreshingIndicator(false);
                        mTeamsView.showTeams(teams);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mTeamsView.setRefreshingIndicator(false);
                        mTeamsView.showSnack(R.string.error_load_user_teams);
                    }
                });
    }

    @Override
    public void loadMore(int page) {
        LogUtil.i(TAG, "load more: " + page);
        mTeamsView.setLoadingIndicator(true, true, R.string.loading, false);
        mRepository.refreshTeams();

        mRepository.getTeams(mUserModel.getDribbbleAccessToken(), mTeamsUrl, page, new
                TeamsDataSource.LoadTeamsCallback() {
                    @Override
                    public void onTeamsLoaded(List<DribbbleTeam> teams) {
                        mTeamsView.setLoadingIndicator(false, false, R.string.loading, false);
                        mTeamsView.insertTeams(teams);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mTeamsView.setLoadingIndicator(true, false,
                                R.string.loading_error, true);
                        mTeamsView.setLoadingError();
                    }
                });
    }

    @Override
    public void openTeamDetails(@NonNull DribbbleTeam requestedTeam, View v) {
        mTeamsView.showTeamDetailsUi(requestedTeam, v);
    }
}

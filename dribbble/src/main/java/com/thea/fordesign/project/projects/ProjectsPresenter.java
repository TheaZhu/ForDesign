package com.thea.fordesign.project.projects;

import android.support.annotation.NonNull;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.project.data.ProjectsDataSource;
import com.thea.fordesign.project.data.ProjectsRepository;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ProjectsPresenter implements ProjectsContract.Presenter {
    public static final String TAG = ProjectsPresenter.class.getSimpleName();

    private final ProjectsContract.View mProjectsView;
    private final UserModel mUserModel;

    private ProjectsRepository mRepository;

    private int mUserId;

    public ProjectsPresenter(int userId, @NonNull ProjectsContract.View projectsView,
                             @NonNull UserModel userModel) {
        mUserId = userId;
        mProjectsView = Preconditions.checkNotNull(projectsView, "shotsView cannot be null");
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");

        mRepository = ProjectsRepository.getInstance();

        mProjectsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadProjects() {
        loadProjects(mUserId, 1, true, false);
    }

    @Override
    public void loadMore(int page) {
        loadProjects(mUserId, page, false, true);
    }

    private void loadProjects(int userId, final int page, final boolean showLoadingUI, final boolean
            isLoadMore) {
        if (showLoadingUI)
            mProjectsView.setRefreshingIndicator(true);

        if (isLoadMore)
            mProjectsView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshProjects();

        mRepository.getProjects(mUserModel.getDribbbleAccessToken(), userId, page, new
                ProjectsDataSource.LoadProjectsCallback() {

            @Override
            public void onProjectsLoaded(List<DribbbleProject> projects) {
                if (showLoadingUI)
                    mProjectsView.setRefreshingIndicator(false);
                if (isLoadMore)
                    mProjectsView.setLoadingIndicator(false, false, R.string.loading, false);
                if (page == 1)
                    mProjectsView.showProjects(projects);
                else
                    mProjectsView.insertProjects(projects);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI) {
                    mProjectsView.setRefreshingIndicator(false);
                    mProjectsView.showSnack(R.string.error_load_projects);
                }
                if (isLoadMore) {
                    mProjectsView.setLoadingIndicator(true, false,
                            R.string.loading_error, true);
                    mProjectsView.setLoadingError();
                }
            }
        });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void openProjectShots(int projectId) {
        mProjectsView.showProjectShotsUi(projectId);
    }

    @Override
    public void start() {
    }
}

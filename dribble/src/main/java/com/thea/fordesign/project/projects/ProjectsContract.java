package com.thea.fordesign.project.projects;

import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.project.ProjectItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ProjectsContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showProjects(List<DribbbleProject> projects);

        void insertProjects(List<DribbbleProject> projects);

        void showProjectShotsUi(int projectId);

    }

    interface Presenter extends ProjectItemPresenter {

        void result(int requestCode, int resultCode);

        void loadProjects();

        void loadMore(int page);

    }
}

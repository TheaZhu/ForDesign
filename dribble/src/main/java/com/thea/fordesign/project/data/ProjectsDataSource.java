package com.thea.fordesign.project.data;

import android.support.annotation.NonNull;

import com.thea.fordesign.bean.DribbbleProject;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ProjectsDataSource {

    interface LoadProjectsCallback {

        void onProjectsLoaded(List<DribbbleProject> projects);

        void onDataNotAvailable();
    }

    interface GetProjectCallback {

        void onProjectLoaded(DribbbleProject project);

        void onDataNotAvailable();
    }

    void getProjects(@NonNull String authorization, int userId, int page,
                    LoadProjectsCallback callback);

    void getProject(@NonNull String authorization, int projectId, GetProjectCallback callback);

    void saveProject(@NonNull String authorization, @NonNull DribbbleProject project);

    void refreshProjects();

    void deleteAllProjects();

    void deleteProject(int projectId);

}

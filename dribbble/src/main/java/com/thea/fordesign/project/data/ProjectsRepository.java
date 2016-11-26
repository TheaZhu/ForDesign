package com.thea.fordesign.project.data;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ProjectsRepository implements ProjectsDataSource {
    public static final String TAG = ProjectsRepository.class.getSimpleName();

    private DribbbleService mService;

    Map<Integer, DribbbleProject> mCachedProjects;
    boolean mCacheIsDirty = false;

    private ProjectsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static ProjectsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getProjects(@NonNull String authorization, int userId, final int page, final
    LoadProjectsCallback callback) {
        if (mCachedProjects == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get projects");
            Call<List<DribbbleProject>> call;
            if (userId < 0)
                call = mService.getUserProjects(authorization, page);
            else
                call = mService.getUserProjects(authorization, userId, page);
            call.enqueue(new Callback<List<DribbbleProject>>() {
                @Override
                public void onResponse(Call<List<DribbbleProject>> call,
                                       Response<List<DribbbleProject>> response) {
                    LogUtil.i(TAG, "get projects code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleProject> projects = response.body();
                    refreshCache(page, projects);
                    if (callback != null)
                        callback.onProjectsLoaded(projects);
                }

                @Override
                public void onFailure(Call<List<DribbbleProject>> call, Throwable t) {
                    LogUtil.i(TAG, "get projects call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onProjectsLoaded(new ArrayList<>(mCachedProjects.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getProject(@NonNull String authorization, int projectId, final GetProjectCallback
            callback) {
        DribbbleProject cachedProject = getProjectWithId(projectId);

        // Respond immediately with cache if available
        if (cachedProject != null) {
            LogUtil.i(TAG, "get project local: " + projectId);
            if (callback != null)
                callback.onProjectLoaded(cachedProject);
        } else {
            LogUtil.i(TAG, "get project: " + projectId);
            Call<DribbbleProject> call = mService.getProject(authorization, projectId);
            call.enqueue(new Callback<DribbbleProject>() {
                @Override
                public void onResponse(Call<DribbbleProject> call, Response<DribbbleProject>
                        response) {
                    LogUtil.i(TAG, "get project code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onProjectLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleProject> call, Throwable t) {
                    LogUtil.i(TAG, "get project call executed: " + call.isExecuted() + ", url: "
                            + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        }

    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void saveProject(@NonNull String authorization, @NonNull DribbbleProject project) {

    }

    @Override
    public void refreshProjects() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllProjects() {
        if (mCachedProjects == null) {
            mCachedProjects = new LinkedHashMap<>();
        }
        mCachedProjects.clear();
    }

    @Override
    public void deleteProject(int projectId) {
        mCachedProjects.remove(projectId);
    }

    private void refreshCache(int page, List<DribbbleProject> projects) {
        if (mCachedProjects == null) {
            mCachedProjects = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedProjects.clear();
        for (DribbbleProject project : projects) {
            mCachedProjects.put(project.getId(), project);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleProject getProjectWithId(int id) {
        if (mCachedProjects == null || mCachedProjects.isEmpty()) {
            return null;
        } else {
            return mCachedProjects.get(id);
        }
    }

    private static class Singleton {
        private static final ProjectsRepository INSTANCE = new ProjectsRepository();
    }
}

package com.thea.fordesign.team.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleTeam;
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
public class TeamsRepository implements TeamsDataSource {
    public static final String TAG = TeamsRepository.class.getSimpleName();

    private DribbbleService mService;

    Map<Integer, DribbbleTeam> mCachedTeams;
    boolean mCacheIsDirty = false;

    private TeamsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static TeamsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void getTeams(@NonNull String authorization, @Nullable String url, final int page, final
    LoadTeamsCallback callback) {
        if (mCachedTeams == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get teams");
            Call<List<DribbbleTeam>> call = mService.getTeams(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleTeam>>() {
                @Override
                public void onResponse(Call<List<DribbbleTeam>> call,
                                       Response<List<DribbbleTeam>> response) {
                    LogUtil.i(TAG, "get teams code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleTeam> teams = response.body();
                    refreshCache(page, teams);
                    if (callback != null)
                        callback.onTeamsLoaded(teams);
                }

                @Override
                public void onFailure(Call<List<DribbbleTeam>> call, Throwable t) {
                    LogUtil.i(TAG, "get teams call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onTeamsLoaded(new ArrayList<>(mCachedTeams.values()));
        }
    }

    @Override
    public void getTeam(@NonNull String authorization, int teamId, final GetTeamCallback
            callback) {
        DribbbleTeam cachedTeam = getTeamWithId(teamId);

        // Respond immediately with cache if available
        if (cachedTeam != null) {
            LogUtil.i(TAG, "get team local: " + teamId);
            if (callback != null)
                callback.onTeamLoaded(cachedTeam);
        } else {
            LogUtil.i(TAG, "get team: " + teamId);
            /*Call<DribbbleTeam> call = mService.getTeam(authorization, teamId);
            call.enqueue(new Callback<DribbbleTeam>() {
                @Override
                public void onResponse(Call<DribbbleTeam> call, Response<DribbbleTeam>
                        response) {
                    LogUtil.i(TAG, "get team code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onTeamLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleTeam> call, Throwable t) {
                    LogUtil.i(TAG, "get team call executed: " + call.isExecuted() + ", url: "
                            + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });*/
        }

    }

    @Override
    public void saveTeam(@NonNull String authorization, @NonNull DribbbleTeam team) {

    }

    @Override
    public void refreshTeams() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTeams() {
        if (mCachedTeams == null) {
            mCachedTeams = new LinkedHashMap<>();
        }
        mCachedTeams.clear();
    }

    @Override
    public void deleteTeam(int teamId) {
        mCachedTeams.remove(teamId);
    }

    private void refreshCache(int page, List<DribbbleTeam> teams) {
        if (mCachedTeams == null) {
            mCachedTeams = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedTeams.clear();
        for (DribbbleTeam team : teams) {
            mCachedTeams.put(team.getId(), team);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleTeam getTeamWithId(int id) {
        if (mCachedTeams == null || mCachedTeams.isEmpty()) {
            return null;
        } else {
            return mCachedTeams.get(id);
        }
    }

    private static class Singleton {
        private static final TeamsRepository INSTANCE = new TeamsRepository();
    }
}

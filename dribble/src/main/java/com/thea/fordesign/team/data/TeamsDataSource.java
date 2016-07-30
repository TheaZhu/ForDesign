package com.thea.fordesign.team.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleTeam;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface TeamsDataSource {

    interface LoadTeamsCallback {

        void onTeamsLoaded(List<DribbbleTeam> teams);

        void onDataNotAvailable();
    }

    interface GetTeamCallback {

        void onTeamLoaded(DribbbleTeam team);

        void onDataNotAvailable();
    }

    void getTeams(@NonNull String authorization, @Nullable String url, int page,
                  LoadTeamsCallback callback);

    void getTeam(@NonNull String authorization, int teamId, GetTeamCallback callback);

    void saveTeam(@NonNull String authorization, @NonNull DribbbleTeam team);

    void refreshTeams();

    void deleteAllTeams();

    void deleteTeam(int teamId);

}

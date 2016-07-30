package com.thea.fordesign.team;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.bean.DribbbleTeam;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface TeamItemPresenter extends BasePresenter {

    void openTeamDetails(@NonNull DribbbleTeam requestedTeam, android.view.View v);
}

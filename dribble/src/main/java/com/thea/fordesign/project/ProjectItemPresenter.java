package com.thea.fordesign.project;

import com.thea.fordesign.base.BasePresenter;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ProjectItemPresenter extends BasePresenter {

    String formatTime(String timeStr);

    void openProjectShots(int projectId);
}

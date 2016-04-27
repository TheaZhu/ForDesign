package com.thea.fordesign.base;

import android.support.annotation.NonNull;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BaseView<T> {

    void setPresenter(@NonNull T presenter);

    void showSnack(String msg);
}

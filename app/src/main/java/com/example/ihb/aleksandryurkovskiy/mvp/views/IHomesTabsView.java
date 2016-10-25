package com.example.ihb.aleksandryurkovskiy.mvp.views;


import android.support.annotation.Nullable;

import com.example.ihb.aleksandryurkovskiy.mvp.presenters.IHomesTabsPresenter;

/**
 * Created by ihb on 24.10.16.
 */

public interface IHomesTabsView {
    void showMessage(String message);
    void showError(Throwable e);

    @Nullable
    IHomesTabsPresenter getPresenter();

    void setCurrentPage(int pageId);

    void startActivityForResult(Class clazz, Long characterId);
}

package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.support.annotation.Nullable;

import com.example.ihb.aleksandryurkovskiy.mvp.views.ISplashScreenView;

/**
 * Created by ihb on 24.10.16.
 */

public interface ISplashScreenPresentor {

    void takeView(ISplashScreenView splashScreenView);
    void dropView();
    void initView();

    @Nullable
    ISplashScreenView getView();
}

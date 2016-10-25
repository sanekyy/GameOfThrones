package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.ihb.aleksandryurkovskiy.mvp.models.SplashScreenModel;
import com.example.ihb.aleksandryurkovskiy.mvp.views.ISplashScreenView;
import com.example.ihb.aleksandryurkovskiy.ui.activities.HomesTabsActivity;
import com.example.ihb.aleksandryurkovskiy.ui.activities.SplashScreenActivity;
import com.example.ihb.aleksandryurkovskiy.utils.AppConfig;

/**
 * Created by ihb on 24.10.16.
 */

public class SplashScreenPresentor implements ISplashScreenPresentor {

    private static SplashScreenPresentor outInstance = new SplashScreenPresentor();
    private SplashScreenModel mSplashScreenModel;
    private ISplashScreenView mSplashScreenView;

    public static final int CHARACTER_ERROR = 1;
    public static final int HOME_ERROR = 2;

    private SplashScreenPresentor(){
        mSplashScreenModel = new SplashScreenModel();
        mSplashScreenModel.prepareData();
    }

    public static SplashScreenPresentor getInstance(){
        return outInstance;
    }


    @Override
    public void takeView(ISplashScreenView splashScreenView) {
        mSplashScreenView = splashScreenView;
    }

    @Override
    public void dropView() {
        mSplashScreenView = null;
    }

    @Override
    public void initView() {
    }

    @Nullable
    @Override
    public ISplashScreenView getView() {
        return mSplashScreenView;
    }

    @Override
    public void onResume() {
        if(getView()!=null) {
            getView().showLoad();
        }
    }

    public void dataPrepared(){
        if(getView()!=null){
            getView().startActivityAndFinish(HomesTabsActivity.class);
        }
    }

    public void dataPreparingError(int errorCode) {
        if(getView()!=null){
            switch (errorCode){
                case HOME_ERROR: getView().showMessage("Не удалось загрузить информацию о доме");
                    break;
                case CHARACTER_ERROR: getView().showMessage("Не удалось загрузить персонажа");
            }
        }
    }
}

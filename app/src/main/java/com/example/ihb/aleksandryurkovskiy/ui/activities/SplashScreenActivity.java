package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import com.example.ihb.aleksandryurkovskiy.BuildConfig;
import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.ISplashScreenPresentor;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.SplashScreenPresentor;
import com.example.ihb.aleksandryurkovskiy.mvp.views.ISplashScreenView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity implements ISplashScreenView {
    ISplashScreenPresentor mPresenter = SplashScreenPresentor.getInstance();

    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout mCoordinatorLayout;


    //region ============== Life cycle ==============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        mPresenter.takeView(this);
        mPresenter.initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    //endregion

    //region ============== IAuthView ==============

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if(BuildConfig.DEBUG){
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Извините, что-то пошло не так, попробуйз позже");
        }
    }

    @Override
    public void showLoad() {
        showProgress();
    }

    @Override
    public void hideLoad() {
        hideProgress();
    }

    @Override
    public ISplashScreenPresentor getPresenter() {
        return mPresenter;
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        startActivity(new Intent(SplashScreenActivity.this, clazz));
        finish();
    }

    //endregion

}


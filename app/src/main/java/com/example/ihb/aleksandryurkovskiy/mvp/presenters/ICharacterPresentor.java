package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.mvp.views.ICharacterView;

/**
 * Created by ihb on 24.10.16.
 */

public interface ICharacterPresentor {
    void takeView(ICharacterView characterView);
    void setCharacterId(Long characterId);
    void dropView();
    void initView();

    @Nullable
    ICharacterView getView();

    void onResume();

    void onNavigationItemClicked(MenuItem item);
    void onFatherClicked();
    void onMotherClicked();
}

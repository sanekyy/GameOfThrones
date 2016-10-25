package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.mvp.views.IHomesTabsView;

import java.util.List;

/**
 * Created by ihb on 24.10.16.
 */

public interface IHomesTabsPresenter {
    void takeView(IHomesTabsView homesTabsView);
    void dropView();
    void initView();

    @Nullable
    IHomesTabsView getView();
    
    List<Character> getCharactersList(int homeId);

    void onResume();

    void onNavigationItemClicked(MenuItem item);

    void onCharacterItemClicked(int homeId, int position);
}

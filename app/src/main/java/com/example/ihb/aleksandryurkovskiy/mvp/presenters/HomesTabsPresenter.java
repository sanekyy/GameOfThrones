package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.mvp.models.HomesTabsModel;
import com.example.ihb.aleksandryurkovskiy.mvp.views.IHomesTabsView;
import com.example.ihb.aleksandryurkovskiy.ui.activities.CharacterActivity;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import java.util.List;

/**
 * Created by ihb on 24.10.16.
 */

public class HomesTabsPresenter implements IHomesTabsPresenter{


    private static HomesTabsPresenter outInstance = new HomesTabsPresenter();
    private HomesTabsModel mHomesTabsModel;
    private IHomesTabsView mHomesTabsView;


    private HomesTabsPresenter(){
        mHomesTabsModel = new HomesTabsModel();
    }

    public static HomesTabsPresenter getInstance(){
        return outInstance;
    }

    @Override
    public void takeView(IHomesTabsView homesTabsView) {
        mHomesTabsView = homesTabsView;
    }

    @Override
    public void dropView() {
        mHomesTabsView = null;
    }

    @Override
    public void initView() {
        // TODO: 24.10.16 some logic
    }

    @Nullable
    @Override
    public IHomesTabsView getView() {
        return mHomesTabsView;
    }

    @Override
    public List<Character> getCharactersList(int homeId) {
        switch(homeId){
            case ConstantManager.STARKS_HOME: return mHomesTabsModel.getStarksList();
            case ConstantManager.LANNISTERS_HOME: return mHomesTabsModel.getLannistersList();
            case ConstantManager.TARGARYENS_HOME: return mHomesTabsModel.getTargaryensList();
        }
        return null;
    }

    @Override
    public void onResume() {
        if (getView() != null) {
            if (mHomesTabsModel.isEmpty(ConstantManager.STARKS_HOME)) {
                getView().showMessage("Список героев не может быть получен");
            }
        }
    }

    @Override
    public void onNavigationItemClicked(MenuItem item) {
        if(getView()!=null) {
            switch (item.getItemId()) {
                case R.id.starks_menu:
                    getView().setCurrentPage(ConstantManager.STARKS_PAGE);
                    break;
                case R.id.lannisters_menu:
                    getView().setCurrentPage(ConstantManager.LANNISTERS_PAGE);
                    break;
                case R.id.targaryens_menu:
                    getView().setCurrentPage(ConstantManager.TARGARYENS_PAGE);
            }
        }
    }

    @Override
    public void onCharacterItemClicked(int homeId, int position) {
        Long characterId;
        switch (homeId){
            case ConstantManager.STARKS_HOME: characterId = mHomesTabsModel.getStarksList().get(position).getId();
                break;
            case ConstantManager.LANNISTERS_HOME: characterId = mHomesTabsModel.getLannistersList().get(position).getId();
                break;
            case ConstantManager.TARGARYENS_HOME: characterId = mHomesTabsModel.getTargaryensList().get(position).getId();
                break;
            default: characterId = 0L;
        }

        getView().startActivityForResult(CharacterActivity.class, characterId);

    }
}

package com.example.ihb.aleksandryurkovskiy.mvp.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.mvp.models.CharacterModel;
import com.example.ihb.aleksandryurkovskiy.mvp.views.ICharacterView;
import com.example.ihb.aleksandryurkovskiy.ui.activities.CharacterActivity;
import com.example.ihb.aleksandryurkovskiy.ui.activities.HomesTabsActivity;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListFragment;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

/**
 * Created by ihb on 24.10.16.
 */

public class CharacterPresenter implements ICharacterPresentor{

    private CharacterModel mCharacterModel;
    private ICharacterView mCharacterView;

    public CharacterPresenter(){
        mCharacterModel = new CharacterModel();
    }

    @Override
    public void takeView(ICharacterView characterView) {
        mCharacterView = characterView;
    }

    @Override
    public void setCharacterId(Long characterId) {
        mCharacterModel.setCharacterId(characterId);
    }

    @Override
    public void dropView() {
        mCharacterView = null;
    }

    @Override
    public void initView() {
        if(getView()!=null) {

            getView().setTitle(mCharacterModel.getName());

            switch (mCharacterModel.getHome()) {
                case ConstantManager.LANNISTERS_HOME:
                    getView().setHomeImg(R.drawable.lannister);
                    getView().setHomeIcons(R.drawable.lanister_icon);
                    break;
                case ConstantManager.STARKS_HOME:
                    getView().setHomeImg(R.drawable.stark);
                    getView().setHomeIcons(R.drawable.stark_icon);
                    break;
                case ConstantManager.TARGARYENS_HOME:
                    getView().setHomeImg(R.drawable.targarien);
                    getView().setHomeIcons(R.drawable.targaryens_icon);
                    break;
            }


            if(mCharacterModel.getWords()==null||"".equals(mCharacterModel.getWords())){
                getView().hideWords();
            } else {
                getView().setWords(mCharacterModel.getWords());
            }

            if(mCharacterModel.getBorn()==null||"".equals(mCharacterModel.getBorn())){
                getView().hideBorn();
            } else {
                getView().setBorn(mCharacterModel.getBorn());
            }

            if(mCharacterModel.getDied()==null||"".equals(mCharacterModel.getDied())){
                getView().hideDied();
            } else {
                getView().setDied(mCharacterModel.getDied());
            }

            if(mCharacterModel.getTitles()==null||mCharacterModel.getTitles().size()==0){
                getView().hideTitles();
            } else {
                getView().setTitles(mCharacterModel.getTitles());
            }

            if(mCharacterModel.getAliases()==null||mCharacterModel.getAliases().size()==0){
                getView().hideAliases();
            } else {
                getView().setAliases(mCharacterModel.getAliases());
            }

            if(mCharacterModel.hasFather()){
                getView().setFather(mCharacterModel.getFatherName());
            } else {
                getView().hideFather();
            }

            if(mCharacterModel.hasMother()){
                getView().setMother(mCharacterModel.getMotherName());
            } else {
                getView().hideMother();
            }
        }
    }

    @Nullable
    @Override
    public ICharacterView getView() {
        return mCharacterView;
    }

    @Override
    public void onResume() {
        if(mCharacterModel.isDied()){
            getView().showMessage("Died " + mCharacterModel.getDied());
        }
    }

    @Override
    public void onNavigationItemClicked(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case R.id.starks_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 0);
                break;
            case R.id.lannisters_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 1);
                break;
            case R.id.targaryens_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 2);
                break;
        }
        getView().startActivityAndFinish(HomesTabsActivity.class, bundle, ConstantManager.OPEN_TABS);
    }

    @Override
    public void onFatherClicked() {
        Bundle bundle = new Bundle();
        bundle.putLong(CharacterActivity.CHARACTER_ID, mCharacterModel.getFatherId());
        getView().startActivity(CharacterActivity.class, bundle);
    }

    @Override
    public void onMotherClicked() {
        Bundle bundle = new Bundle();
        bundle.putLong(CharacterActivity.CHARACTER_ID, mCharacterModel.getMotherId());
        getView().startActivity(CharacterActivity.class, bundle);
    }
}

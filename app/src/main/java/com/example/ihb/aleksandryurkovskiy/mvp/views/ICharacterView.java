package com.example.ihb.aleksandryurkovskiy.mvp.views;

import android.content.Intent;
import android.os.Bundle;

import com.example.ihb.aleksandryurkovskiy.mvp.presenters.ICharacterPresentor;

import java.util.List;

/**
 * Created by ihb on 24.10.16.
 */

public interface ICharacterView {

    void showMessage(String message);
    void showError(Throwable e);

    ICharacterPresentor getPresenter();
    void startActivity(Class clazz, Bundle bundle);
    void startActivityAndFinish(Class clazz, Bundle bundle, int resultCode);

    void setTitle(String characterName);
    void setHomeImg(int imgId);
    void setHomeIcons(int iconId);

    void setWords(String words);
    void setBorn(String born);
    void setDied(String died);
    void setTitles(List<String> titles);
    void setAliases(List<String> aliases);
    void setFather(String father);
    void setMother(String mother);


    void hideWords();
    void hideBorn();
    void hideDied();
    void hideTitles();
    void hideAliases();
    void hideFather();
    void hideMother();
}

package com.example.ihb.aleksandryurkovskiy.mvp.models;

import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Alias;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Title;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 24.10.16.
 */

public class CharacterModel {
    DataManager mDataManager;
    CharacterDao mCharacterDao;

    Long mCharacterId;

    private int mHome;
    private String mName;
    private String mBorn;
    private String mDied;
    private String mWords;
    private List<String> mTitles;
    private List<String> mAliases;
    private Character mFather;
    private Character mMother;



    public CharacterModel(){
        mDataManager = DataManager.getInstance();
        mCharacterDao = mDataManager.getDaoSession().getCharacterDao();
    }

    public void setCharacterId(Long characterId) {
        mCharacterId = characterId;
        initModel(mDataManager.getDaoSession().getCharacterDao().load(characterId));
    }

    private void initModel(Character character) {
        mName = character.getName();
        mBorn = character.getBorn();
        mDied = character.getDied();
        mHome = character.getHome();
        mWords = character.getWords();

        mTitles = new ArrayList<>();
        for(Title title : character.getTitles()){
            mTitles.add(title.getTitle());
        }
        mAliases = new ArrayList<>();
        for(Alias aliase : character.getAliases()){
            mAliases.add(aliase.getAlias());
        }
        if(character.getFather()!=null&&!character.getFather().equals(0L)){
            mFather = mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(character.getFather()*1000, (character.getFather()+1)*1000)).unique();
        }
        if(character.getMother()!=null&&!character.getMother().equals(0L)){
            mMother = mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(character.getMother()*1000, (character.getMother()+1)*1000)).unique();
        }
    }


    public int getHome() {
        return mHome;
    }

    public String getName() {
        return mName;
    }

    public String getWords() {
        return mWords;
    }

    public String getBorn() {
        return mBorn;
    }

    public String getDied() {
        return mDied;
    }

    public List<String> getTitles() {
        return mTitles;
    }

    public List<String> getAliases() {
        return mAliases;
    }

    public boolean hasFather(){
        return mFather!=null;
    }

    public boolean hasMother(){
        return mMother!=null;
    }

    public Long getFatherId(){
        return mFather.getId();
    }

    public Long getMotherId(){
        return mMother.getId();
    }

    public String getFatherName(){
        return mFather.getName();
    }

    public String getMotherName(){
        return mMother.getName();
    }

    public boolean isDied() {
        return mDied!=null&&!"".equals(mDied);
    }

}

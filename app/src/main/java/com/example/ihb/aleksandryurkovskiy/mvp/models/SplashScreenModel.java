package com.example.ihb.aleksandryurkovskiy.mvp.models;

import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.network.res.CharacterModelRes;
import com.example.ihb.aleksandryurkovskiy.data.network.res.HomeModelRes;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Alias;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.AliasDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Title;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.TitleDao;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.SplashScreenPresentor;
import com.example.ihb.aleksandryurkovskiy.utils.AppConfig;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ihb on 24.10.16.
 */

public class SplashScreenModel {

    private DataManager mDataManager;
    private CharacterDao mCharacterDao;
    private AliasDao mAliasDao;
    private TitleDao mTitleDao;

    private List<Character> mCharacters = new ArrayList<>();
    private List<Alias> mAliases = new ArrayList<>();
    private List<Title> mTitles = new  ArrayList<>();

    private Callback<HomeModelRes> mCallback;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private int mIterator = 268;

    public SplashScreenModel(){
        mDataManager = DataManager.getInstance();
        mCharacterDao = mDataManager.getDaoSession().getCharacterDao();
        mAliasDao = mDataManager.getDaoSession().getAliasDao();
        mTitleDao = mDataManager.getDaoSession().getTitleDao();
    }

    private void downIterator(int num) {
        mIterator -= num;
        if (mIterator == 0) {
            mCharacterDao.insertOrReplaceInTx(mCharacters);
            mTitleDao.insertOrReplaceInTx(mTitles);
            mAliasDao.insertOrReplaceInTx(mAliases);
            SplashScreenPresentor.getInstance().dataPrepared();
        }
    }

    public boolean isEmpty() {
        return mCharacterDao.count() == 0;
    }

    public void downloadCharactersInDb() {
        Call<HomeModelRes> callStarks = mDataManager.getHomesFromNetwork(ConstantManager.STARKS_HOME);
        Call<HomeModelRes> callLannisters = mDataManager.getHomesFromNetwork(ConstantManager.LANNISTERS_HOME);
        Call<HomeModelRes> callTargaryens = mDataManager.getHomesFromNetwork(ConstantManager.TARGARYENS_HOME);

        setCallBack();

        callStarks.enqueue(mCallback);
        callLannisters.enqueue(mCallback);
        callTargaryens.enqueue(mCallback);
    }

    private void setCallBack() {
        mCallback = new Callback<HomeModelRes>() {
            @Override
            public void onResponse(final Call<HomeModelRes> call,final Response<HomeModelRes> response) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(response.code()==200){
                            int homeId = Utils.getHomeIdFromUrl(call.request().url().toString());

                            for(String characterUrl : response.body().swornMembers){
                                mCharacters.add(new Character(homeId, response.body().words, Long.valueOf(Utils.getCharacterIdFromUrl(characterUrl)+""+homeId)));
                            }

                            downloadCharacters(mCharacters);
                        } else {
                            SplashScreenPresentor.getInstance().dataPreparingError(SplashScreenPresentor.HOME_ERROR);
                            switch (Utils.getHomeIdFromUrl(call.request().url().toString())) {
                                case ConstantManager.STARKS_HOME:
                                    downIterator(ConstantManager.STARKS_POPULATION);
                                    break;
                                case ConstantManager.LANNISTERS_HOME:
                                    downIterator(ConstantManager.LANNISTERS_POPULATION);
                                    break;
                                case ConstantManager.TARGARYENS_HOME:
                                    downIterator(ConstantManager.TARGARYENS_POPULATION);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Call<HomeModelRes> call,final Throwable t) {
                executor.execute(new Runnable() {
                                     @Override
                                     public void run() {
                                         SplashScreenPresentor.getInstance().dataPreparingError(SplashScreenPresentor.HOME_ERROR);
                                         t.printStackTrace();
                                         SplashScreenPresentor.getInstance().dataPreparingError(SplashScreenPresentor.CHARACTER_ERROR);
                                         switch (Utils.getHomeIdFromUrl(call.request().url().toString())) {
                                             case ConstantManager.STARKS_HOME:
                                                 downIterator(ConstantManager.STARKS_POPULATION);
                                                 break;
                                             case ConstantManager.LANNISTERS_HOME:
                                                 downIterator(ConstantManager.LANNISTERS_POPULATION);
                                                 break;
                                             case ConstantManager.TARGARYENS_HOME:
                                                 downIterator(ConstantManager.TARGARYENS_POPULATION);
                                         }
                                     }
                });

            }
        };
    }

    private void downloadCharacters(List<Character> characters) {
        for(final Character character : characters){

            final Long remoteId = character.getRemoteId();
            Call<CharacterModelRes> call = mDataManager.getCharacterFromNetwork(remoteId/1000);

            call.enqueue(new Callback<CharacterModelRes>() {
                @Override
                public void onResponse(final Call<CharacterModelRes> call,final Response<CharacterModelRes> response) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(response.code()==200) {
                                character.fill(response.body());

                                for (String aliase : response.body().aliases) {
                                    mAliases.add(new Alias(remoteId, aliase));
                                }
                                for (String title : response.body().titles) {
                                    mTitles.add(new Title(remoteId, title));
                                }

                            } else {
                                SplashScreenPresentor.getInstance().dataPreparingError(SplashScreenPresentor.CHARACTER_ERROR);
                            }
                            downIterator(1);
                        }
                    });
                }

                @Override
                public void onFailure(Call<CharacterModelRes> call, Throwable t) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            downIterator(1);
                            mCharacters.remove(character);
                        }
                    });

                }
            });
        }
    }

    public void setIterator(int iterator) {
        mIterator = iterator;
    }

    public void prepareData() {

            if (isEmpty()) {
                downloadCharactersInDb();
            } else {
                setIterator(0);
            }

            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    if (mIterator == 0) {
                        mCharacterDao.insertOrReplaceInTx(mCharacters);
                        mTitleDao.insertOrReplaceInTx(mTitles);
                        mAliasDao.insertOrReplaceInTx(mAliases);
                        SplashScreenPresentor.getInstance().dataPrepared();
                    }
                }
            }, AppConfig.SPLASH_SCREEN_DELAY, TimeUnit.MILLISECONDS);
    }
}

package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.network.res.CharacterModelRes;
import com.example.ihb.aleksandryurkovskiy.data.network.res.HomeModelRes;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Aliase;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.AliaseDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Title;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.TitleDao;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListFragment;
import com.example.ihb.aleksandryurkovskiy.utils.AppConfig;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends BaseActivity {

    private DataManager mDataManager;
    private CharacterDao mCharacterDao;
    private AliaseDao mAliaseDao;
    private TitleDao mTitleDao;

    private Callback<HomeModelRes> mCallback;

    private Handler mHandler;

    private int iterator = 268;

    private void downIterator(int num) {
        iterator -= num;
        if (iterator == 0) {
            startActivityForResult(new Intent(this, HomesTabsActivity.class), ConstantManager.LAST_PAGE);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mDataManager = DataManager.getInstance();
        mCharacterDao = mDataManager.getDaoSession().getCharacterDao();
        mAliaseDao = mDataManager.getDaoSession().getAliaseDao();
        mTitleDao = mDataManager.getDaoSession().getTitleDao();

        mHandler = new Handler();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ConstantManager.LAST_PAGE){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();

        if(mCharacterDao.count()==0) {
            downloadInDb();
        } else {
            iterator = 0;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(iterator==0){
                    startActivityForResult(new Intent(SplashScreenActivity.this, HomesTabsActivity.class), ConstantManager.LAST_PAGE);
                }
            }
        }, AppConfig.SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    private void downloadInDb() {

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
            public void onResponse(Call<HomeModelRes> call, Response<HomeModelRes> response) {
                if(response.code()==200){
                    List<Character> characters = new ArrayList<>();

                    int homeId = Utils.getHomeIdFromUrl(call.request().url().toString());

                    for(String characterUrl : response.body().swornMembers){
                        characters.add(new Character(homeId, response.body().words, Long.valueOf(Utils.getCharacterIdFromUrl(characterUrl)+""+homeId)));
                    }

                    mCharacterDao.insertInTx(characters);

                    downloadCharacters(characters);
                } else {
                    showSnackbar("Список героев не может быть получен");
                }
            }

            @Override
            public void onFailure(Call<HomeModelRes> call, Throwable t) {
                showSnackbar(t.getMessage());
                call.enqueue(mCallback);
            }
        };
    }

    private void downloadCharacters(List<Character> characters) {
        for(final Character character : characters){

            final Long remoteId = character.getRemoteId();
            Call<CharacterModelRes> call = mDataManager.getCharacterFromNetwork(remoteId/1000);

            call.enqueue(new Callback<CharacterModelRes>() {
                @Override
                public void onResponse(Call<CharacterModelRes> call, Response<CharacterModelRes> response) {

                    if(response.code()==200) {
                        Character character = mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.eq(remoteId)).unique();
                        character.fill(response.body());
                        mCharacterDao.insertOrReplace(character);

                        List<Aliase> aliases = new ArrayList<>();
                        List<Title> titles = new ArrayList<>();

                        for (String aliase : response.body().aliases) {
                            aliases.add(new Aliase(remoteId, aliase));
                        }
                        for (String title : response.body().titles) {
                            titles.add(new Title(remoteId, title));
                        }

                        mAliaseDao.insertOrReplaceInTx(aliases);
                        mTitleDao.insertOrReplaceInTx(titles);

                    } else {
                        showSnackbar("Список героев не может быть получен");
                    }
                    downIterator(1);
                }

                @Override
                public void onFailure(Call<CharacterModelRes> call, Throwable t) {
                    downIterator(1);
                    mCharacterDao.delete(character);
                }
            });
        }
    }

    private void showSnackbar(String message){
        Snackbar.make(findViewById(R.id.main_coordinator_container), message, Snackbar.LENGTH_LONG).show();
    }
}


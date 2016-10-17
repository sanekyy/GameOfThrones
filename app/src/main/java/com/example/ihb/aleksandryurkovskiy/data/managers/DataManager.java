package com.example.ihb.aleksandryurkovskiy.data.managers;

import android.content.Context;

import com.example.ihb.aleksandryurkovskiy.data.network.RestService;
import com.example.ihb.aleksandryurkovskiy.data.network.ServiceGenerator;
import com.example.ihb.aleksandryurkovskiy.data.network.res.CharacterModelRes;
import com.example.ihb.aleksandryurkovskiy.data.network.res.HomeModelRes;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.DaoSession;
import com.example.ihb.aleksandryurkovskiy.utils.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by ihb on 14.10.16.
 */

public class DataManager {
    private static DataManager INSTANCE = new DataManager();

    private Context mContext;
    private PreferencesManager mPreferencesManager;
    private RestService mRestService;

    private DaoSession mDaoSession;

    public DataManager() {
        this.mPreferencesManager = new PreferencesManager();
        this.mContext = MyApplication.getContext();
        this.mRestService = ServiceGenerator.createService(RestService.class);
        this.mDaoSession = MyApplication.getDaoSession();
    }

    public static DataManager getInstance(){
        return INSTANCE;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Context getContext() {
        return mContext;
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }


    public Call<HomeModelRes> getHomesFromNetwork(int homeid) {
        return  mRestService.getHome(homeid);
    }

    public Call<CharacterModelRes> getCharacterFromNetwork(Long characterId) {

        return mRestService.getCharacter(characterId);
    }

    public List<Character> getCharacterOfHouseListFromDb(int houseNumber) {
        List<Character> userList = new ArrayList<>();

        try {
            userList = mDaoSession.getCharacterDao().queryBuilder().where(CharacterDao.Properties.Home.eq(houseNumber)).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}

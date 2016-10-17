package com.example.ihb.aleksandryurkovskiy.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ihb.aleksandryurkovskiy.data.storage.models.DaoMaster;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.DaoSession;
import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ihb on 14.10.16.
 */

public class MyApplication extends Application {

    private static Context sContext;
    private static SharedPreferences sSharedPreferences;
    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        sContext = getApplicationContext();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "WinterIsComing-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();

        Stetho.initializeWithDefaults(this);
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static Context getContext() {
        return sContext;
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }
}

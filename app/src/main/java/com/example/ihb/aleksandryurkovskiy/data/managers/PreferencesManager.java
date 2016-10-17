package com.example.ihb.aleksandryurkovskiy.data.managers;

import android.content.SharedPreferences;

import com.example.ihb.aleksandryurkovskiy.utils.MyApplication;

/**
 * Created by ihb on 14.10.16.
 */

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;

    public PreferencesManager(){
        this.mSharedPreferences = MyApplication.getSharedPreferences();
    }
}

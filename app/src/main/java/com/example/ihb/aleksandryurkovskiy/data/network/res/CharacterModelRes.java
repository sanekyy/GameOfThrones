package com.example.ihb.aleksandryurkovskiy.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 14.10.16.
 */

public class CharacterModelRes {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("born")
    @Expose
    public String born;
    @SerializedName("died")
    @Expose
    public String died;
    @SerializedName("titles")
    @Expose
    public List<String> titles = new ArrayList<String>();
    @SerializedName("aliases")
    @Expose
    public List<String> aliases = new ArrayList<String>();
    @SerializedName("father")
    @Expose
    public String father;
    @SerializedName("mother")
    @Expose
    public String mother;
}

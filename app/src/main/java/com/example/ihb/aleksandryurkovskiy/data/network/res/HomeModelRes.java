package com.example.ihb.aleksandryurkovskiy.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 14.10.16.
 */
public class HomeModelRes {

    @SerializedName("words")
    @Expose
    public String words;

    @SerializedName("swornMembers")
    @Expose
    public List<String> swornMembers = new ArrayList<String>();
}
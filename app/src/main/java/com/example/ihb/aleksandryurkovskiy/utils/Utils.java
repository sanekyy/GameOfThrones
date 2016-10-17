package com.example.ihb.aleksandryurkovskiy.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

/**
 * Created by ihb on 14.10.16.
 */

public class Utils {



    public static Long getCharacterIdFromUrl(String url){
        if(url==""){
            return 0L;
        }
        Matcher matcher = Pattern.compile("characters").matcher(url);
        matcher.find();

        return Long.valueOf(url.substring(matcher.start()+11, url.length()));
    }

    public static int getHomeIdFromUrl(String url) {
        Matcher matcher = Pattern.compile("houses").matcher(url);
        matcher.find();

        return Integer.valueOf(url.substring(matcher.start()+7, url.length()));
    }
    
    public static String joinString(List<String> list, String between){

        String result = list.get(0);
        for (int i=1; i<list.size();i++){
            result += between + list.get(i);
        }

        return result;
    }
}

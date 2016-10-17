package com.example.ihb.aleksandryurkovskiy.utils;

/**
 * Created by ihb on 14.10.16.
 */

public interface AppConfig {
    String BASE_URL = "http://anapioficeandfire.com/api/";
    int MAX_CONNECT_TIMEOUT = 5000;
    int MAX_READ_TIMEOUT = 5000;
    long SPLASH_SCREEN_DELAY = 3000; // 3 секунды слишком мало, показалось:)
}

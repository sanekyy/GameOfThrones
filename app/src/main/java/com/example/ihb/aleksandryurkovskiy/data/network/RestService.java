package com.example.ihb.aleksandryurkovskiy.data.network;

import com.example.ihb.aleksandryurkovskiy.data.network.res.CharacterModelRes;
import com.example.ihb.aleksandryurkovskiy.data.network.res.HomeModelRes;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by ihb on 14.10.16.
 */

public interface RestService {

    @GET("houses/{homeId}")
    Call<HomeModelRes> getHome(@Path("homeId") int homeId);

    @GET("characters/{characterId}")
    Call<CharacterModelRes> getCharacter(@Path("characterId") Long characterId);


}


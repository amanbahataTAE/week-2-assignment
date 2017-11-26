package com.example.aman1.songlistapplication.data.network.services;

import com.example.aman1.songlistapplication.data.network.services.RequestInterface;
import com.example.aman1.songlistapplication.data.network.services.constants.Api_List;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aman1 on 25/11/2017.
 */

/**
 * ServerConnection class serves as the networking layer of the
 * application
 */

public class ServerConnection {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static RequestInterface getServerConnection(){

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api_List.BASE_URL)
                .build();

        return retrofit.create(RequestInterface.class);

    }
}

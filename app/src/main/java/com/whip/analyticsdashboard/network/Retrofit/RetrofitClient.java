package com.whip.analyticsdashboard.network.Retrofit;

import com.whip.analyticsdashboard.NetworkUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitInstance;

    public static Retrofit getInstance() {
        if (retrofitInstance == null)
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(NetworkUtil.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())         // To use this converter to serialize JSON
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // To return calls as Observable type
                    .build();
        return retrofitInstance;
    }

    private RetrofitClient() {

    }
}

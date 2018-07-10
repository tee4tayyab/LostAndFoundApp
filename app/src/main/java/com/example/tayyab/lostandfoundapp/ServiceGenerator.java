package com.example.tayyab.lostandfoundapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    //private static final String BASE_URL = "http://192.168.8.101/jun/public/api/";
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit;


    public static <S> S createService(
            Class<S> serviceClass) {

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            builder.client(client);

            retrofit = builder.build();

        }

        return retrofit.create(serviceClass);
    }
}
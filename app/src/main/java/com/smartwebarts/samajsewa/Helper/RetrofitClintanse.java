package com.smartwebarts.samajsewa.Helper;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClintanse {


    /*    private static Retrofit retrofit;


        public static Retrofit getRetrofit() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl( Contraints.BaseUrl )
                        .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().setLenient().create() ) )
                        .build();
            }
            return retrofit;
        }*/
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            ////////////////////////////////////////////////////

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(60, TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .writeTimeout(60, TimeUnit.MINUTES)
                    .build();


            ////////////////////////////////////////////////////
            retrofit = new Retrofit.Builder()
                    .baseUrl(Contraints.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
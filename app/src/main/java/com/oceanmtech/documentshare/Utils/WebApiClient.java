package com.oceanmtech.documentshare.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApiClient {
    public static WebServices getInstance() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + PreferenceHelper.getString(Constants.API_TOKEN, ""))
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.connectTimeout(180, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEB_ADDRESS)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit.create(WebServices.class);
    }

    private static Retrofit retrofit = null;

//    public static WebServices getApiService() {
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .readTimeout(2, TimeUnit.MINUTES)
//                .writeTimeout(2, TimeUnit.MINUTES)
//
//                .addInterceptor(chain -> {
//                    Request original = chain.request();
//                    Request.Builder requestBuilder = requestBuilder = original.newBuilder()
//                            .method(original.method(), original.body());
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }).build();
//
//        if (retrofit == null) {
//
//            retrofit = new Retrofit
//                    .Builder()
//                    .baseUrl(Constants.WEB_ADDRESS)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//
//        }
//
//        return retrofit.create(WebServices.class);
//
//    }

    public static WebServices getInstance2() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.connectTimeout(180, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEB_ADDRESS)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit.create(WebServices.class);
    }
}

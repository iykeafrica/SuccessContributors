package com.example.successcontribution.retrofit.client;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.successcontribution.retrofit.api.ApiCall;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY;
import static com.example.successcontribution.shared.Constant.BASE_URL;
import static com.example.successcontribution.shared.Constant.MY_PREF;

public class AuthServiceClient {

    private static final String TAG = AuthServiceClient.class.getSimpleName();
    private static AuthServiceClient INSTANCE;
    private final Retrofit mRetrofit;

    public AuthServiceClient(Application application) {
        SharedPreferences mSharedPreferences = application.getSharedPreferences(MY_PREF, 0);
        String authorization = mSharedPreferences.getString(AUTHORIZATION_TOKEN_DEFAULT_KEY, "");

        Log.d(TAG, "AuthServiceClient: Authorization " + authorization);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", authorization);
                return chain.proceed(builder.build());
            }
        }).connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static synchronized AuthServiceClient getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new AuthServiceClient(application);
        }
        return INSTANCE;
    }

    public ApiCall getApi() {
        return mRetrofit.create(ApiCall.class);
    }
}

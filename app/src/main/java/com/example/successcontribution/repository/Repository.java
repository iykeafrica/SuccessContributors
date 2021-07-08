package com.example.successcontribution.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.successcontribution.model.request.UserDetailsRequestModel;
import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.response.HeaderResponse;
import com.example.successcontribution.repository.response.UserRestResponse;
import com.example.successcontribution.retrofit.client.RetrofitClient;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();

    private final RetrofitClient mClient;

    public Repository() {
        mClient = new RetrofitClient();
    }

    public HeaderResponse login(UserLoginRequestModel loginRequestModel){
        MutableLiveData<Headers> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().login(loginRequestModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Headers headerList = response.headers();
                    data.setValue(headerList);

                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            networkError.setValue(response.errorBody().string());
                            Log.d(TAG, "onResponse: " + e);

                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new HeaderResponse(data, networkError);
    }

    public UserRestResponse createUser(UserDetailsRequestModel detailsRequestModel) {
        MutableLiveData<UserRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().createUser(detailsRequestModel).enqueue(new Callback<UserRest>() {
            @Override
            public void onResponse(Call<UserRest> call, Response<UserRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });


        return new UserRestResponse(data, networkError);
    }

}

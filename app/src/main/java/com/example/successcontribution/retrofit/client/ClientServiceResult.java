package com.example.successcontribution.retrofit.client;

import android.app.Application;
import android.util.Log;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.UserRest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientServiceResult {

    private static final String TAG = ClientServiceResult.class.getSimpleName();
    private final AuthServiceClient mClient;

    public ClientServiceResult(Application application) {
        mClient = new AuthServiceClient(application);
    }

    public void networkCall(UserRestResult userRestResult, int page, int limit) {

        mClient.getApi().getUsers(page, limit).enqueue(new Callback<List<UserRest>>() {
            @Override
            public void onResponse(Call<List<UserRest>> call, Response<List<UserRest>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    List<UserRest> users;
                    if (response.body() != null) {
                        users = response.body();
                    } else {
                        users = new ArrayList<>();
                    }
                    userRestResult.onSuccess(users);
                } else {
                    try {
                        userRestResult.onError(response.errorBody() != null ? response.errorBody().string() : "Unknown Error");
                    } catch (IOException e) {
                        Log.d(TAG, "onResponse: Response failed while reading errorBody");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserRest>> call, Throwable t) {
                Log.d(TAG, "onFailure: Error, failed response");

                userRestResult.onError(t.getMessage() != null ?
                        t.getMessage() : "Unknown Error");
            }
        });
    }








    public interface UserRestResult {
        void onSuccess(List<UserRest> users);

        void onError(String errorMessage);
    }

    public interface LoanRestResult {
        void onSuccess(List<LoanRest> loans);

        void onError(String errorMessage);
    }


}

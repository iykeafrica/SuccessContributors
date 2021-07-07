package com.example.successcontribution.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.response.LoanRestResponse;
import com.example.successcontribution.retrofit.client.AuthServiceClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY;

public class AuthRepository {

    private static final String TAG = AuthRepository.class.getSimpleName();
    private final AuthServiceClient mClient;
    private final SharedPreferences mSharedPreferences;

    public AuthRepository(Application application) {
        mClient = new AuthServiceClient(application);
        mSharedPreferences = application.getSharedPreferences(MY_PREF, 0);
    }

    public LoanRestResponse requestLoan(LoanRequestModel loanRequestModel) {
        MutableLiveData<LoanRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        String userId = mSharedPreferences.getString(USER_ID_DEFAULT_KEY, "");

        mClient.getApi().requestLoan(userId, loanRequestModel).enqueue(new Callback<LoanRest>() {
            @Override
            public void onResponse(Call<LoanRest> call, Response<LoanRest> response) {
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
            public void onFailure(Call<LoanRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });


        return new LoanRestResponse(data, networkError);
    }


}

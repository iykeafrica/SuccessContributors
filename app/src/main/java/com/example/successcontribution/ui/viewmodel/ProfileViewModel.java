package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.UserRestResponse;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModel extends AndroidViewModel {

    private final LiveData<UserRest> mUserRestLiveData;
    private final LiveData<String> mNetworkError;

    public ProfileViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        UserRestResponse userRestResponse = repository.getUser();
        mUserRestLiveData = userRestResponse.getData();
        mNetworkError = userRestResponse.getNetworkError();
    }

    public LiveData<UserRest> getUserRestLiveData() {
        return mUserRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

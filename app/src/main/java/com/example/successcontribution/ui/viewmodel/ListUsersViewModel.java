package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.ListUserRestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListUsersViewModel extends AndroidViewModel {

    private final LiveData<List<UserRest>> mListUserRestLiveData;
    private final LiveData<String> mNetworkError;

    public ListUsersViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);

        ListUserRestResponse listUserRestResponse = repository.getUsers();

        mListUserRestLiveData = listUserRestResponse.getData();
        mNetworkError = listUserRestResponse.getNetworkError();
    }

    public LiveData<List<UserRest>> getListUserRestLiveData() {
        return mListUserRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

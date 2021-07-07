package com.example.successcontribution.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.repository.Repository;
import com.example.successcontribution.repository.response.HeaderResponse;

import okhttp3.Headers;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<UserLoginRequestModel> mUserLoginRequestModelData;
    private final LiveData<Headers> mHeadersLiveData;
    private final LiveData<String> mNetworkErrorLiveData;

    public LoginViewModel() {
        mUserLoginRequestModelData = new MutableLiveData<>();
        Repository repository = new Repository();

        LiveData<HeaderResponse> liveData = Transformations.map(mUserLoginRequestModelData,
                repository::login);

        mHeadersLiveData = Transformations.switchMap(liveData,
                HeaderResponse::getData);

        mNetworkErrorLiveData = Transformations.switchMap(liveData,
                HeaderResponse::getNetworkError);

    }

    public void setUserLoginRequestModelData(UserLoginRequestModel loginRequestModel) {
        mUserLoginRequestModelData.setValue(loginRequestModel);
    }

    public LiveData<Headers> getHeadersLiveData() {
        return mHeadersLiveData;
    }

    public LiveData<String> getNetworkErrorLiveData() {
        return mNetworkErrorLiveData;
    }
}

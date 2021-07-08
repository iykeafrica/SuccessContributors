package com.example.successcontribution.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.successcontribution.model.request.UserDetailsRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.Repository;
import com.example.successcontribution.repository.response.UserRestResponse;

public class CreateUserViewModel extends ViewModel {

    private final MutableLiveData<UserDetailsRequestModel> mUserDetailsRequestModelData;
    private final LiveData<UserRest> mUserRestLiveData;
    private final LiveData<String> mNetworkError;

    public CreateUserViewModel() {
        Repository repository = new Repository();
        mUserDetailsRequestModelData = new MutableLiveData<>();

        LiveData<UserRestResponse> liveData = Transformations.map(mUserDetailsRequestModelData,
                repository::createUser);

        mUserRestLiveData = Transformations.switchMap(liveData,
                UserRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                UserRestResponse::getNetworkError);
    }

    public void setUserDetailsRequestModelData(UserDetailsRequestModel requestModelData){
        mUserDetailsRequestModelData.setValue(requestModelData);
    }

    public LiveData<UserRest> getUserRestLiveData() {
        return mUserRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

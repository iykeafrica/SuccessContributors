package com.example.successcontribution.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.request.UserDepositedFundRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.UserRestResponse;

public class UpdateUserSavingsViewModel extends AndroidViewModel {

    private final MutableLiveData<UserSavingsCouple> mUserSavingsCoupleData;
    private final LiveData<UserRest> mUserRestLiveData;
    private final LiveData<String> mNetworkError;

    public UpdateUserSavingsViewModel(Application application) {
        super(application);
        AuthRepository repository = new AuthRepository(application);
        mUserSavingsCoupleData = new MutableLiveData<>();

        LiveData<UserRestResponse> liveData = Transformations.map(mUserSavingsCoupleData,
                input -> repository.updateUserSavings(input.userId, input.requestModel));

        mUserRestLiveData = Transformations.switchMap(liveData,
                UserRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                UserRestResponse::getNetworkError);
    }

    public void setUserSavingsCoupleData(String userId, UserDepositedFundRequestModel requestModel){
        UserSavingsCouple userSavingsCouple = new UserSavingsCouple(userId, requestModel);
        mUserSavingsCoupleData.setValue(userSavingsCouple);
    }

    public LiveData<UserRest> getUserRestLiveData() {
        return mUserRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    static class UserSavingsCouple {
        String userId;
        UserDepositedFundRequestModel requestModel;

        public UserSavingsCouple(String userId, UserDepositedFundRequestModel requestModel) {
            this.userId = userId;
            this.requestModel = requestModel;
        }
    }
}

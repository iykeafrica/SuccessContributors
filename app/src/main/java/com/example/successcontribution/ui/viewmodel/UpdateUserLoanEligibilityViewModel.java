package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.request.UserLoanEligibilityRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.UserRestResponse;

import org.jetbrains.annotations.NotNull;

public class UpdateUserLoanEligibilityViewModel extends AndroidViewModel {

    private final MutableLiveData<UserLoanEligibilityCouple> mUserLoanEligibilityCoupleData;
    private final LiveData<UserRest> mUserRestLiveData;
    private final LiveData<String> mNetworkError;

    public UpdateUserLoanEligibilityViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        mUserLoanEligibilityCoupleData = new MutableLiveData<>();

        LiveData<UserRestResponse> liveData = Transformations.map(mUserLoanEligibilityCoupleData,
                input -> repository.updateUserLoanEligibility(input.userId, input.requestModel));

        mUserRestLiveData = Transformations.switchMap(liveData,
                UserRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                UserRestResponse::getNetworkError);

    }

    public void setUserLoanEligibilityCoupleData (String userId, UserLoanEligibilityRequestModel requestModel){
        UserLoanEligibilityCouple userLoanEligibilityCouple = new UserLoanEligibilityCouple(userId, requestModel);
        mUserLoanEligibilityCoupleData.setValue(userLoanEligibilityCouple);
    }

    public LiveData<UserRest> getUserRestLiveData() {
        return mUserRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    static class UserLoanEligibilityCouple {
        String userId;
        UserLoanEligibilityRequestModel requestModel;

        public UserLoanEligibilityCouple(String userId, UserLoanEligibilityRequestModel requestModel) {
            this.userId = userId;
            this.requestModel = requestModel;
        }
    }
}

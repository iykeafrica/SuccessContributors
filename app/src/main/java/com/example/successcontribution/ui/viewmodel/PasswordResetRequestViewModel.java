package com.example.successcontribution.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.successcontribution.model.request.PasswordResetRequestModel;
import com.example.successcontribution.model.response.OperationStatusModel;
import com.example.successcontribution.repository.Repository;
import com.example.successcontribution.repository.response.OperationStatusResponse;

public class PasswordResetRequestViewModel extends ViewModel {

    private final MutableLiveData<PasswordResetRequestModel> mPasswordResetRequestData;
    private final LiveData<OperationStatusModel> mOperationStatusModelLiveData;
    private final LiveData<String> mNetworkError;

    public PasswordResetRequestViewModel() {
        Repository repository = new Repository();
        mPasswordResetRequestData = new MutableLiveData<>();

        LiveData<OperationStatusResponse> liveData = Transformations.map(mPasswordResetRequestData,
                repository::passwordResetRequest);

        mOperationStatusModelLiveData = Transformations.switchMap(liveData,
                OperationStatusResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                OperationStatusResponse::getNetworkError);
    }

    public void setPasswordResetRequestData(PasswordResetRequestModel passwordResetRequestData) {
        mPasswordResetRequestData.setValue(passwordResetRequestData);
    }

    public LiveData<OperationStatusModel> getOperationStatusModelLiveData() {
        return mOperationStatusModelLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

}

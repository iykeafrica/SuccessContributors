package com.example.successcontribution.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.LoanRestResponse;

public class LoanRequestViewModel extends AndroidViewModel {

    private final MutableLiveData<LoanRequestModel> mLoanRequestModelData;
    private final LiveData<LoanRest> mLoanRestLiveData;
    private final LiveData<String> mNetworkError;

    public LoanRequestViewModel(Application application) {
        super(application);
        AuthRepository repository = new AuthRepository(application);
        mLoanRequestModelData = new MutableLiveData<>();

       LiveData<LoanRestResponse> liveData = Transformations.map(mLoanRequestModelData,
               repository::requestLoan);

       mLoanRestLiveData = Transformations.switchMap(liveData,
               LoanRestResponse::getData);

       mNetworkError = Transformations.switchMap(liveData,
               LoanRestResponse::getNetworkError);
    }

    public void setLoanRequestModelData (LoanRequestModel loanRequestModel) {
        mLoanRequestModelData.setValue(loanRequestModel);
    }

    public LiveData<LoanRest> getLoanRestLiveData() {
        return mLoanRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

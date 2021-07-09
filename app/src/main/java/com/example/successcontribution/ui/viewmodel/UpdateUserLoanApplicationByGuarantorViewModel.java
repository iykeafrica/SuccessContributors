package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.request.GuarantorLoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.LoanRestResponse;

import org.jetbrains.annotations.NotNull;

public class UpdateUserLoanApplicationByGuarantorViewModel extends AndroidViewModel {

    private final MutableLiveData<UpdateLoanByGuarantorCouple> mUpdateLoanByGuarantorCoupleData;
    private final LiveData<LoanRest> mLoanRestLiveData;
    private final LiveData<String> mNetworkError;

    public UpdateUserLoanApplicationByGuarantorViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        mUpdateLoanByGuarantorCoupleData = new MutableLiveData<>();

        LiveData<LoanRestResponse> liveData = Transformations.map(mUpdateLoanByGuarantorCoupleData,
               input -> repository.updateUserLoanApplicationByGuarantor(input.userId, input.loanId, input.guarantorLoanRequestModel));

        mLoanRestLiveData = Transformations.switchMap(liveData,
                LoanRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                LoanRestResponse::getNetworkError);

    }

    public void setUpdateLoanByGuarantorCoupleData(String userId, String loanId, GuarantorLoanRequestModel guarantorLoanRequestModel) {
        UpdateLoanByGuarantorCouple transactionsCouple = new UpdateLoanByGuarantorCouple(userId, loanId, guarantorLoanRequestModel);
        mUpdateLoanByGuarantorCoupleData.setValue(transactionsCouple);
    }

    public LiveData<LoanRest> getLoanRestLiveData() {
        return mLoanRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    static class UpdateLoanByGuarantorCouple {
        private final String userId;
        private final String loanId;
        private final GuarantorLoanRequestModel guarantorLoanRequestModel;

        public UpdateLoanByGuarantorCouple(String userId, String loanId, GuarantorLoanRequestModel guarantorLoanRequestModel) {
            this.userId = userId;
            this.loanId = loanId;
            this.guarantorLoanRequestModel = guarantorLoanRequestModel;
        }
    }
}

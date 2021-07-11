package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.request.AdminLoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.LoanRestResponse;

import org.jetbrains.annotations.NotNull;

public class UpdateUserLoanApplicationByAdminViewModel extends AndroidViewModel {

    private final MutableLiveData<UpdateLoanByAdminCouple> mUpdateLoanByAdminCoupleData;
    private final LiveData<LoanRest> mLoanRestLiveData;
    private final LiveData<String> mNetworkError;

    public UpdateUserLoanApplicationByAdminViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        mUpdateLoanByAdminCoupleData = new MutableLiveData<>();

        LiveData<LoanRestResponse> liveData = Transformations.map(mUpdateLoanByAdminCoupleData,
                input -> repository.updateUserLoanApplicationByAdmin(input.userId, input.loanId, input.adminLoanRequestModel));

        mLoanRestLiveData = Transformations.switchMap(liveData,
                LoanRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                LoanRestResponse::getNetworkError);
    }

    public void setUpdateLoanByAdminCoupleData(String userId, String loanId, AdminLoanRequestModel adminLoanRequestModel) {
        UpdateLoanByAdminCouple transactionsCouple = new UpdateLoanByAdminCouple(userId, loanId, adminLoanRequestModel);
        mUpdateLoanByAdminCoupleData.setValue(transactionsCouple);
    }

    public LiveData<LoanRest> getLoanRestLiveData() {
        return mLoanRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    static class UpdateLoanByAdminCouple {
        private final String userId;
        private final String loanId;
        private final AdminLoanRequestModel adminLoanRequestModel;

        public UpdateLoanByAdminCouple(String userId, String loanId, AdminLoanRequestModel adminLoanRequestModel) {
            this.userId = userId;
            this.loanId = loanId;
            this.adminLoanRequestModel = adminLoanRequestModel;
        }
    }
}

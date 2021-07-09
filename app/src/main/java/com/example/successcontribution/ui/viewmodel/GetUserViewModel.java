package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.LoanRestResponse;

import org.jetbrains.annotations.NotNull;

public class GetUserViewModel extends AndroidViewModel {

    private final MutableLiveData<StringCouple> mStringCoupleData;
    private final LiveData<LoanRest> mLoanRestLiveData;
    private final LiveData<String> mNetworkError;

    public GetUserViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        mStringCoupleData = new MutableLiveData<>();

        LiveData<LoanRestResponse> liveData = Transformations.map(mStringCoupleData,
               input -> repository.getLoanApplication(input.userId, input.loanId));

        mLoanRestLiveData = Transformations.switchMap(liveData,
                LoanRestResponse::getData);

        mNetworkError = Transformations.switchMap(liveData,
                LoanRestResponse::getNetworkError);

    }

    public void setStringCoupleData(String userId, String loanId) {
        StringCouple transactionsCouple = new StringCouple(userId, loanId);
        mStringCoupleData.setValue(transactionsCouple);
    }

    public LiveData<LoanRest> getLoanRestLiveData() {
        return mLoanRestLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    static class StringCouple {
        private final String userId;
        private final String loanId;

        public StringCouple(String userId, String loanId) {
            this.userId = userId;
            this.loanId = loanId;
        }
    }
}

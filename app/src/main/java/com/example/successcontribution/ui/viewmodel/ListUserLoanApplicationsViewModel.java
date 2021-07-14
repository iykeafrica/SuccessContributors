package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.ListLoanRestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListUserLoanApplicationsViewModel extends AndroidViewModel {

    private final LiveData<List<LoanRest>> mListLiveData;
    private final LiveData<String> mNetworkError;

    private final MutableLiveData<Long> mLongMutableLiveData;
    private final LiveData<List<LoanRest>> mLoanRestLiveData;
    private final LiveData<List<LoanRest>> mOneLoanRestLiveData;

    public ListUserLoanApplicationsViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);
        mLongMutableLiveData = new MutableLiveData<>();

        ListLoanRestResponse restResponse = repository.getUserLoanApplications();
        mListLiveData = restResponse.getData();
        mNetworkError = restResponse.getNetworkError();

        mLoanRestLiveData = repository.getManyLoans();
        mOneLoanRestLiveData = Transformations.switchMap(mLongMutableLiveData, repository::getOneLoan);
    }


    public void setLoanMutableLiveData(long query){
        mLongMutableLiveData.setValue(query);
    }

    public LiveData<List<LoanRest>> getLoanRestLiveData() {
        return mLoanRestLiveData;
    }

    public LiveData<List<LoanRest>> getOneLoanRestLiveData() {
        return mOneLoanRestLiveData;
    }


    public LiveData<List<LoanRest>> getListLiveData() {
        return mListLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

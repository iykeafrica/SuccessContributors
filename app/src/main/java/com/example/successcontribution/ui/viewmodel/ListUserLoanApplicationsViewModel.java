package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.AuthRepository;
import com.example.successcontribution.repository.response.ListLoanRestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListUserLoanApplicationsViewModel extends AndroidViewModel {

    private final LiveData<List<LoanRest>> mListLiveData;
    private final LiveData<String> mNetworkError;

    public ListUserLoanApplicationsViewModel(@NonNull @NotNull Application application) {
        super(application);

        AuthRepository repository = new AuthRepository(application);

        ListLoanRestResponse restResponse = repository.getUserLoanApplications();
        mListLiveData = restResponse.getData();
        mNetworkError = restResponse.getNetworkError();
    }

    public LiveData<List<LoanRest>> getListLiveData() {
        return mListLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }
}

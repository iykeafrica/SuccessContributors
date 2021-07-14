package com.example.successcontribution.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.LoansRepository;
import com.example.successcontribution.repository.response.PagedLoansRestResponse;

import org.jetbrains.annotations.NotNull;

public class PagedLoanRestViewModel extends AndroidViewModel {

    private final MutableLiveData<Long> mQueryMutableLiveData;
    private final LiveData<PagedList<LoanRest>> mPagedListLiveData;
    private final LiveData<String> mNetworkError;
    private final LiveData<PagedList<LoanRest>> mPagedListByLoanLiveData;
    private final LiveData<String> mNetworkByLoanError;


    public PagedLoanRestViewModel(@NonNull @NotNull Application application) {
        super(application);

        LoansRepository repository = new LoansRepository(application);
        mQueryMutableLiveData = new MutableLiveData<>();

        PagedLoansRestResponse pagedLoansRestResponse = repository.getAllLoans();
        mPagedListLiveData = pagedLoansRestResponse.getData();
        mNetworkError = pagedLoansRestResponse.getNetworkError();

        LiveData<PagedLoansRestResponse> liveData = Transformations.map(mQueryMutableLiveData,
                repository::getLoan);

        mPagedListByLoanLiveData = Transformations.switchMap(liveData,
                PagedLoansRestResponse::getData);

        mNetworkByLoanError = Transformations.switchMap(liveData,
                PagedLoansRestResponse::getNetworkError);
    }

    public void setQueryMutableLiveData(Long query) {
        mQueryMutableLiveData.setValue(query);
    }

    public LiveData<PagedList<LoanRest>> getPagedListLiveData() {
        return mPagedListLiveData;
    }

    public LiveData<String> getNetworkError() {
        return mNetworkError;
    }

    public LiveData<PagedList<LoanRest>> getPagedListByLoanLiveData() {
        return mPagedListByLoanLiveData;
    }

    public LiveData<String> getNetworkByLoanError() {
        return mNetworkByLoanError;
    }
}

package com.example.successcontribution.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.example.successcontribution.db.LoansLocalCache;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.retrofit.client.ClientServiceResult;

import java.util.List;

public class LoansBoundaryCallback extends PagedList.BoundaryCallback<LoanRest> implements ClientServiceResult.LoanRestResult{
    private static final String TAG = UsersBoundaryCallback.class.getSimpleName();
    private final LoansLocalCache mLoansLocalCache;
    private final ClientServiceResult mClientServiceResult;
    private int lastPageRequested = 1;
    //    private final String mQuery;
    public static final int NETWORK_PAGE_SIZE = 3;
    boolean isStillSearching = false;
    private final MutableLiveData<String> networkError = new MutableLiveData<>();

    public LoansBoundaryCallback(Application application) {
        mLoansLocalCache = new LoansLocalCache(application);
        mClientServiceResult = new ClientServiceResult(application);
    }
    private void searchAndSave(){
        if (isStillSearching){
            return;
        }
        isStillSearching = true;

        mClientServiceResult.networkCallLoanRest(this);
    }

    public DataSource.Factory<Integer, LoanRest> getLoan(long query) {
        return mLoansLocalCache.getLoan(query);
    }

    public DataSource.Factory<Integer, LoanRest> getAllLoans() {
        return mLoansLocalCache.getAllLoans();
    }

    @Override
    public void onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded: Started");
        searchAndSave();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull LoanRest itemAtEnd) {
        Log.d(TAG, "onItemAtEndLoaded: Started");
        searchAndSave();
    }

    public MutableLiveData<String> getNetworkError() {
        return networkError;
    }

    @Override
    public void onSuccess(List<LoanRest> loans) {
        mLoansLocalCache.insert(loans, new LoansLocalCache.Callback() {
            @Override
            public void insertFinished() {
                searchAndSave();
                lastPageRequested++;
                isStillSearching = false;
            }
        });
    }

    @Override
    public void onError(String errorMessage) {
        networkError.postValue(errorMessage);
        isStillSearching = false;
    }
}

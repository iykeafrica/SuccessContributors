package com.example.successcontribution.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.repository.response.PagedLoansRestResponse;

public class LoansRepository {
    private static final String TAG = LoansRepository.class.getSimpleName();
    private final LoansBoundaryCallback mBoundaryCallback;
    public static final int STORAGE_PAGE_SIZE = 5;

    public LoansRepository(Application application) {
        mBoundaryCallback = new LoansBoundaryCallback(application);
    }

    public PagedLoansRestResponse getAllLoans() {
        LiveData<String> networkError = mBoundaryCallback.getNetworkError();

        DataSource.Factory<Integer, LoanRest> factory = mBoundaryCallback.getAllLoans();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(STORAGE_PAGE_SIZE)
                .build();

        LiveData<PagedList<LoanRest>> data = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(mBoundaryCallback)
                .build();

        return new PagedLoansRestResponse(data, networkError);
    }

    public PagedLoansRestResponse getLoan(long query) {
        LiveData<String> networkError = mBoundaryCallback.getNetworkError();

        DataSource.Factory<Integer, LoanRest> factory = mBoundaryCallback.getLoan(query);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(STORAGE_PAGE_SIZE)
                .build();

        LiveData<PagedList<LoanRest>> data = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(mBoundaryCallback)
                .build();

        return new PagedLoansRestResponse(data, networkError);
    }

}

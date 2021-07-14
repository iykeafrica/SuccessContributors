package com.example.successcontribution.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.example.successcontribution.db.UsersLocalCache;
import com.example.successcontribution.retrofit.client.ClientServiceResult;
import com.example.successcontribution.model.response.UserRest;

import java.util.List;

import static com.example.successcontribution.shared.Constant.ADMIN;
import static com.example.successcontribution.shared.Constant.EXCO;
import static com.example.successcontribution.shared.Constant.LOAN_CHECKER;
import static com.example.successcontribution.shared.Constant.PRESIDENT;
import static com.example.successcontribution.shared.Constant.SUPER_ADMIN;

public class UsersBoundaryCallback extends PagedList.BoundaryCallback<UserRest> implements ClientServiceResult.UserRestResult {
    private static final String TAG = UsersBoundaryCallback.class.getSimpleName();
    private final UsersLocalCache mUsersLocalCache;
    private final ClientServiceResult mClientServiceResult;
    private int lastPageRequested = 1;
//    private final String mQuery;
    public static final int NETWORK_PAGE_SIZE = 3;
    boolean isStillSearching = false;
    private final MutableLiveData<String> networkError = new MutableLiveData<>();

    public UsersBoundaryCallback(Application application) {
        mUsersLocalCache = new UsersLocalCache(application);
        mClientServiceResult = new ClientServiceResult(application);
    }

    private void searchAndSave(){
        if (isStillSearching){
            return;
        }
        isStillSearching = true;

        mClientServiceResult.networkCallUserRest(this, lastPageRequested, NETWORK_PAGE_SIZE);
    }

    public DataSource.Factory<Integer, UserRest> getUser(String query) {
        return mUsersLocalCache.getUser(query);
    }

    public DataSource.Factory<Integer, UserRest> getAllUsers() {
        return mUsersLocalCache.getAllUsers();
    }

    @Override
    public void onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded: Started");
        searchAndSave();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull UserRest itemAtEnd) {
        Log.d(TAG, "onItemAtEndLoaded: Started");
        searchAndSave();
    }

    public MutableLiveData<String> getNetworkError() {
        return networkError;
    }

    @Override
    public void onSuccess(List<UserRest> users) {
        UserRest userRest = new UserRest();

        for (int i = 0; i < users.size(); i++) {
            userRest = users.get(i);

            if (userRest.getFirstName().equals(EXCO))
                users.remove(userRest);

            if (userRest.getFirstName().equals(LOAN_CHECKER))
                users.remove(userRest);

            if (userRest.getFirstName().equals(PRESIDENT))
                users.remove(userRest);

            if (userRest.getFirstName().equals(ADMIN))
                users.remove(userRest);

            if (userRest.getFirstName().equals(SUPER_ADMIN))
                users.remove(userRest);
        }

        mUsersLocalCache.insert(users, new UsersLocalCache.Callback() {
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

package com.example.successcontribution.db;

import android.app.Application;
import android.util.Log;

import androidx.paging.DataSource;

import com.example.successcontribution.model.response.LoanRest;

import java.util.List;

public class LoansLocalCache {

    public static final String TAG = LoansLocalCache.class.getSimpleName();
    private final LoansDao mLoansDao;

    public LoansLocalCache(Application application) {
        UsersDB userDB = UsersDB.getInstance(application);
        mLoansDao = userDB.loansDao();
    }

    public void insert(List<LoanRest> loans, Callback callback) {
        Log.d(TAG, "insert: inserting " + loans.size() + " loans");

        UsersDB.ioExecutor.execute(() -> {
            mLoansDao.insert(loans);
            callback.insertFinished();
        });
    }

    public DataSource.Factory<Integer, LoanRest> getLoan(long query) {
        return mLoansDao.getLoan(query);
    }

    public DataSource.Factory<Integer, LoanRest> getAllLoans() {
        return mLoansDao.getAllLoans();
    }

    public interface Callback {
        void insertFinished();
    }
}

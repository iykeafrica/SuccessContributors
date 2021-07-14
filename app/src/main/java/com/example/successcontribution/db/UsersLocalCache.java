package com.example.successcontribution.db;

import android.app.Application;
import android.util.Log;

import androidx.paging.DataSource;

import com.example.successcontribution.model.response.UserRest;

import java.util.List;

public class UsersLocalCache {

    public static final String TAG = UsersLocalCache.class.getSimpleName();
    private final UsersDao mUsersDao;

    public UsersLocalCache(Application application) {
        UsersDB userDB = UsersDB.getInstance(application);
        mUsersDao = userDB.usersDao();
    }

    public void insert(List<UserRest> users, Callback callback) {
        Log.d(TAG, "insert: inserting " + users.size() + " users");

        UsersDB.ioExecutor.execute(() -> {
            mUsersDao.insert(users);
            callback.insertFinished();
        });
    }

    public DataSource.Factory<Integer, UserRest> getUserRepo(String query) {
        String formatQuery = "%" + query.replace(' ', '%') + "%";
        return mUsersDao.getUsers(formatQuery);
        //return mDao.getRecipeRepo(query);
    }

    public DataSource.Factory<Integer, UserRest> getAllUserRepo() {
        return mUsersDao.getAllUsers();
    }

    public interface Callback {
        void insertFinished();
    }

}

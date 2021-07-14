package com.example.successcontribution.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.successcontribution.model.response.UserRest;

import java.util.List;

import androidx.paging.DataSource;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserRest> users);

    @Query("SELECT * FROM users")
    DataSource.Factory<Integer, UserRest> getAllUsers();

    @Query("SELECT * FROM users WHERE (firstName LIKE :queryString) OR " +
            "(lastName LIKE :queryString) ORDER BY firstName ASC, sapNo DESC")
    DataSource.Factory<Integer, UserRest> getUsers(String queryString);

    @Query("SELECT * FROM users WHERE (firstName LIKE '%' || :name || '%' ) OR " +
            "(lastName LIKE '%' || :name || '%' )")
    LiveData<List<UserRest>> getUserByName(String name);

}

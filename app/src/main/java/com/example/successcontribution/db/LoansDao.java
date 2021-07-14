package com.example.successcontribution.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.successcontribution.model.response.LoanRest;

import java.util.List;

@Dao
public interface LoansDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<LoanRest> loans);

    @Query("SELECT * FROM loans")
    DataSource.Factory<Integer, LoanRest> getAllLoans();

    @Query("SELECT * FROM loans WHERE (requestDate LIKE :queryString) OR " +
            "(statusDate LIKE :queryString) ORDER BY requestDate DESC")
    DataSource.Factory<Integer, LoanRest> getLoan(long queryString);

    @Query("SELECT * FROM loans")
    LiveData<List<LoanRest>> getManyLoans();

    @Query("SELECT * FROM loans WHERE (requestDate LIKE :queryString ) OR (statusDate LIKE :queryString )")
    LiveData<List<LoanRest>> getOneLoan(long queryString);

}

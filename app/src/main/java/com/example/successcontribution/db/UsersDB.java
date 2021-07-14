package com.example.successcontribution.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.shared.DataConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserRest.class, LoanRest.class}, version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class UsersDB extends RoomDatabase {

    private static volatile UsersDB INSTANCE;
    public abstract UsersDao usersDao();
    public abstract LoansDao loansDao();
    private static final int NO_OF_THREADS = 4;
    public static ExecutorService ioExecutor = Executors.newFixedThreadPool(NO_OF_THREADS);

    public static UsersDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UsersDB.class, "users_db")
                        .build();
            }
        }
        return INSTANCE;
    }
}

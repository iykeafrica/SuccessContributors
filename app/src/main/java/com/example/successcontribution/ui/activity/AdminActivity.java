package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.successcontribution.databinding.ActivityAdminBinding;
import com.example.successcontribution.ui.activity.admin.UpdateUserLoanStatusActivity;
import com.example.successcontribution.ui.activity.admin.UpdateUserSavingsActivity;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding mBinding;
    private String mUserRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
        mUserRole = preferences.getString(LOGIN_ROLE_KEY, "");

        searchUserLoan();
        updateUserLoanStatus();
        updateUserSavings();
        listContributors();
    }

    private void updateUserSavings() {
        mBinding.updateUserSavings.setOnClickListener(v -> {
            startActivity(new Intent(this, UpdateUserSavingsActivity.class));
        });
    }

    private void updateUserLoanStatus() {
        mBinding.updateUserLoanStatus.setOnClickListener(v -> {
            startActivity(new Intent(this, UpdateUserLoanStatusActivity.class));
        });
    }

    private void searchUserLoan() {
        mBinding.searchUserLoan.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetUserLoanActivity.class);
            intent.putExtra(LOGIN_ROLE_KEY, mUserRole);
            intent.putExtra(APPROVE_LOAN_KEY, APPROVE_LOAN_VALUE);
            startActivity(intent);
        });
    }

    private void listContributors() {
        mBinding.getUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListUsersActivity.class);
            startActivity(intent);
        });
    }

}
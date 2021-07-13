package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.successcontribution.databinding.ActivityAdminBinding;
import com.example.successcontribution.ui.activity.admin.UpdateUserLoanStatusActivity;
import com.example.successcontribution.ui.activity.admin.UpdateUserSavingsActivity;

import static com.example.successcontribution.shared.Constant.ADMIN;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.PRESIDENT;
import static com.example.successcontribution.shared.Constant.SUPER_ADMIN;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding mBinding;
    private String mUserRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        searchUserLoan();
        listContributors();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
        mUserRole = preferences.getString(LOGIN_ROLE_KEY, "");

        if (PRESIDENT.equals(mUserRole) || ADMIN.equals(mUserRole) || SUPER_ADMIN.equals(mUserRole)){
            mBinding.updateUserLoanStatus.setVisibility(View.VISIBLE);
            mBinding.updateUserSavings.setVisibility(View.VISIBLE);
            updateUserLoanStatus();
            updateUserSavings();
        }
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
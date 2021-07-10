package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.successcontribution.databinding.ActivityAdminBinding;
import com.example.successcontribution.ui.activity.admin.UpdateUserLoanStatusActivity;
import com.example.successcontribution.ui.activity.admin.UpdateUserSavingsActivity;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_VALUE;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        searchUserLoan();
        updateUserLoanStatus();
        updateUserSavings();
        listContributors();
    }

    private void updateUserSavings() {
        mBinding.searchUserLoan.setOnClickListener(v -> {
            startActivity(new Intent(this, UpdateUserSavingsActivity.class));
        });
    }

    private void updateUserLoanStatus() {
        mBinding.updateUserLoanStatus.setOnClickListener(v -> {
            startActivity(new Intent(this, UpdateUserLoanStatusActivity.class));
        });    }

    private void searchUserLoan() {
        mBinding.searchUserLoan.setOnClickListener(v -> {
            Intent intent = new Intent(this, GetUserLoanActivity.class);
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
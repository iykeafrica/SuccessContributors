package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityAdminBinding;
import com.example.successcontribution.ui.activity.admin.SearchUserLoanActivity;
import com.example.successcontribution.ui.activity.admin.UpdateUserLoanStatusActivity;
import com.example.successcontribution.ui.activity.admin.UpdateUserSavingsActivity;

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
    }

    private void updateUserSavings() {
        startActivity(new Intent(this, UpdateUserSavingsActivity.class));
    }

    private void updateUserLoanStatus() {
        startActivity(new Intent(this, UpdateUserLoanStatusActivity.class));
    }

    private void searchUserLoan() {
        startActivity(new Intent(this, SearchUserLoanActivity.class));
        finish();
    }
}
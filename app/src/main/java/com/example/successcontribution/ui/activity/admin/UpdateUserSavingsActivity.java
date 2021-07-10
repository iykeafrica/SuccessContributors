package com.example.successcontribution.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityUpdateUserLoanStatusBinding;

public class UpdateUserSavingsActivity extends AppCompatActivity {

    ActivityUpdateUserLoanStatusBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityUpdateUserLoanStatusBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


    }
}
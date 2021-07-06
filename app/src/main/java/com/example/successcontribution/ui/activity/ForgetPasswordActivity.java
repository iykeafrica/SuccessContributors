package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        submit();
    }

    private void submit() {
        mBinding.btnSubmit.setOnClickListener(v -> {
            if (!mBinding.etEmail.getText().toString().toString().isEmpty()) {
                sendResetLink();
            }
        });
    }

    private void sendResetLink() {

    }
}
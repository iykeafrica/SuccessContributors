package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        forgetPassword();
        signUp();
        login();
    }

    private void forgetPassword() {
        mBinding.tvForgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgetPasswordActivity.class));
        });
    }

    private void signUp() {
        mBinding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });
    }

    private void login() {
        String username = mBinding.etUsername.getText().toString().trim();
        String password = mBinding.etPassword.getText().toString().trim();

        mBinding.btnLogin.setOnClickListener(v -> {
            if (!username.isEmpty() && !password.isEmpty()) {
                signIn();
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {

    }
}
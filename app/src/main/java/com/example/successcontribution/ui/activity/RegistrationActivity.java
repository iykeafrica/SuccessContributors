package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityRegistrationBinding;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding mBinding;
    private String mName;
    private String mGender;
    private String mSapNumber;
    private String mEmail;
    private String mMobileNumber;
    private String mAddress;
    private String mWhatsapp;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        hideKeyBoard(mBinding.name);

        getUserDetails();
    }

    private void getUserDetails() {
        mBinding.submit.setOnClickListener(v -> {
            if (mBinding.name.getText().toString().trim().isEmpty()) {
                mBinding.nameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.nameRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.nameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.nameRequiredText.setTextColor(getResources().getColor(R.color.black));
                mName = mBinding.name.getText().toString().trim();
            }

            if (mBinding.gender.getText().toString().trim().isEmpty()) {
                mBinding.genderRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.genderRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.genderRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.genderRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGender = mBinding.genderRequiredText.getText().toString().trim();
            }

            if (mBinding.sapNumber.getText().toString().trim().isEmpty()) {
                mBinding.sapNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.sapNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.sapNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.sapNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mSapNumber = mBinding.sapNumberRequiredText.getText().toString().trim();
            }

            if (mBinding.email.getText().toString().trim().isEmpty()) {
                mBinding.emailRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.emailRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.emailRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.emailRequiredText.setTextColor(getResources().getColor(R.color.black));
                mEmail = mBinding.emailRequiredText.getText().toString().trim();
            }

            if (mBinding.mobileNumber.getText().toString().trim().isEmpty()) {
                mBinding.mobileNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.mobileNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.mobileNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.mobileNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mMobileNumber = mBinding.mobileNumberRequiredText.getText().toString().trim();
            }

            if (mBinding.address.getText().toString().trim().isEmpty()) {
                mBinding.addressRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.addressRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.addressRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.addressRequiredText.setTextColor(getResources().getColor(R.color.black));
                mAddress = mBinding.addressRequiredText.getText().toString().trim();
            }

            if (mBinding.whatsappNumber.getText().toString().trim().isEmpty()) {
                mBinding.whatsappNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.whatsappNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.whatsappNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.whatsappNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mWhatsapp = mBinding.whatsappNumberRequiredText.getText().toString().trim();
            }

            if (mBinding.passwordOne.getText().toString().trim().isEmpty()) {
                mBinding.passwordOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordOneRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.passwordOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.passwordOneRequiredText.setTextColor(getResources().getColor(R.color.black));
            }

            if (mBinding.passwordTwo.getText().toString().trim().isEmpty()) {
                mBinding.passwordTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.passwordTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.passwordTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
            }

            if (mBinding.passwordOne.getText().toString().trim().equals(mBinding.passwordTwo.getText().toString().trim())
                    && !mBinding.passwordOne.getText().toString().trim().isEmpty() && !mBinding.passwordTwo.getText().toString().trim().isEmpty()) {
                mBinding.passwordOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.passwordOneRequiredText.setTextColor(getResources().getColor(R.color.black));
                mBinding.passwordOneRequiredText.setText("Required");
                mBinding.passwordTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.passwordTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
                mBinding.passwordTwoRequiredText.setText("Required");
                mPassword = mBinding.passwordTwoRequiredText.toString();
            } else if (!mBinding.passwordOne.getText().toString().trim().equals(mBinding.passwordTwo.getText().toString().trim())
                    && !mBinding.passwordOne.getText().toString().trim().isEmpty() || !mBinding.passwordTwo.getText().toString().trim().isEmpty()) {
                mBinding.passwordOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordOneRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.passwordOneRequiredText.setText("Password did not match");
                mBinding.passwordTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.passwordTwoRequiredText.setText("Password did not match");
            } else {
                mBinding.passwordOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordOneRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.passwordOneRequiredText.setText("Required");
                mBinding.passwordTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.passwordTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.passwordTwoRequiredText.setText("Required");
                mPassword = mBinding.passwordTwoRequiredText.toString();
            }

            submit();
        });
    }

    private void submit() {
        if (!mName.isEmpty() && !mGender.isEmpty() & !mSapNumber.isEmpty() && !mEmail.isEmpty() &&
                !mMobileNumber.isEmpty() && !mAddress.isEmpty() && !mWhatsapp.isEmpty() && !mPassword.isEmpty()) {
            Toast.makeText(this, "Submit successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyBoard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(v -> {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.requestFocus();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        });
    }
}
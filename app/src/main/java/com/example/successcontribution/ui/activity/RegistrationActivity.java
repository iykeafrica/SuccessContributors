package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityRegistrationBinding;
import com.example.successcontribution.model.request.UserDetailsRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.viewmodel.CreateUserViewModel;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private ActivityRegistrationBinding mBinding;
    private String mFirstName;
    private String mLastName;
    private String mDepartment;
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
        hideOpeningKeyBoard(mBinding.firstName);

        getUserDetails();
    }

    private void getUserDetails() {
        mBinding.submit.setOnClickListener(v -> {
            if (mBinding.firstName.getText().toString().trim().isEmpty()) {
                mBinding.firstNameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.firstNameRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.firstNameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.firstNameRequiredText.setTextColor(getResources().getColor(R.color.black));
                mFirstName = mBinding.firstName.getText().toString().trim();
            }

            if (mBinding.lastName.getText().toString().trim().isEmpty()) {
                mBinding.lastNameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.lastNameRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.lastNameRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.lastNameRequiredText.setTextColor(getResources().getColor(R.color.black));
                mLastName = mBinding.lastName.getText().toString().trim();
            }

            if (mBinding.departments.getText().toString().trim().isEmpty()) {
                mBinding.departmentsRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.departmentsRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.departmentsRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.departmentsRequiredText.setTextColor(getResources().getColor(R.color.black));
                mDepartment = mBinding.departments.getText().toString().trim();
            }

            if (mBinding.sapNumber.getText().toString().trim().isEmpty()) {
                mBinding.sapNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.sapNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.sapNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.sapNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mSapNumber = mBinding.sapNumber.getText().toString().trim();
            }

            if (mBinding.email.getText().toString().trim().isEmpty()) {
                mBinding.emailRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.emailRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.emailRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.emailRequiredText.setTextColor(getResources().getColor(R.color.black));
                mEmail = mBinding.email.getText().toString().trim();
            }

            if (mBinding.mobileNumber.getText().toString().trim().isEmpty()) {
                mBinding.mobileNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.mobileNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.mobileNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.mobileNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mMobileNumber = mBinding.mobileNumber.getText().toString().trim();
            }

            if (mBinding.address.getText().toString().trim().isEmpty()) {
                mBinding.addressRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.addressRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.addressRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.addressRequiredText.setTextColor(getResources().getColor(R.color.black));
                mAddress = mBinding.address.getText().toString().trim();
            }

            if (mBinding.whatsappNumber.getText().toString().trim().isEmpty()) {
                mBinding.whatsappNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.whatsappNumberRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.whatsappNumberRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.whatsappNumberRequiredText.setTextColor(getResources().getColor(R.color.black));
                mWhatsapp = mBinding.whatsappNumber.getText().toString().trim();
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
                mPassword = mBinding.passwordTwo.getText().toString().trim();
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
            }

            submit();
        });
    }

    private void submit() {
        if (!mBinding.firstName.getText().toString().trim().isEmpty() && !mBinding.lastName.getText().toString().trim().isEmpty() &&
                !mBinding.departments.getText().toString().trim().isEmpty() && !mBinding.sapNumber.getText().toString().trim().isEmpty() &&
                !mBinding.email.getText().toString().trim().isEmpty() && !mBinding.mobileNumber.getText().toString().trim().isEmpty() &&
                !mBinding.address.getText().toString().trim().isEmpty() && !mBinding.whatsappNumber.getText().toString().trim().isEmpty() &&
                !mBinding.passwordOne.getText().toString().trim().isEmpty() && !mBinding.passwordTwo.getText().toString().trim().isEmpty()
                && mBinding.passwordOne.getText().toString().trim().equals(mBinding.passwordTwo.getText().toString().trim())) {

            CreateUserViewModel viewModel = new ViewModelProvider(this).get(CreateUserViewModel.class);
            UserDetailsRequestModel requestModel = new UserDetailsRequestModel();
            requestModel.setFirstName(mFirstName);
            requestModel.setLastName(mLastName);
            requestModel.setDepartment(mDepartment);
            requestModel.setSapNo(mSapNumber);
            requestModel.setEmail(mEmail);
            requestModel.setFcmToken("qejdnbdbdndndndb");
            requestModel.setPhoneNo(mMobileNumber);
            requestModel.setAddress(mAddress);
            requestModel.setWhatsappNo(mWhatsapp);
            requestModel.setPassword(mPassword);

            viewModel.setUserDetailsRequestModelData(requestModel);

            attemptConnection(viewModel);

        } else {
            Toast.makeText(this, "Required field(s)", Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptConnection(CreateUserViewModel viewModel) {
        hideKeyboard(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        viewModel.getUserRestLiveData().observe(this, new Observer<UserRest>() {
            @Override
            public void onChanged(UserRest userRest) {
                successConnection(userRest, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });

    }

    private void successConnection(UserRest userRest, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Log.d(TAG, "successConnection: " + userRest.getUserId());
        Toast.makeText(this, "Registration successful. Please, check email and verify before login in", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        getViewModelStore().clear();
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        Toast.makeText(this, "" + errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        getViewModelStore().clear();
    }

    private void hideOpeningKeyBoard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(v -> {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.requestFocus();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
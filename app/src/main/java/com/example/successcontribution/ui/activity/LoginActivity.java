package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.successcontribution.databinding.ActivityLoginBinding;
import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.shared.Utils;
import com.example.successcontribution.ui.viewmodel.LoginViewModel;

import okhttp3.Headers;

import static com.example.successcontribution.shared.Constant.AUTHORIZATION_HEADER_STRING;
import static com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY;
import static com.example.successcontribution.shared.Constant.FIRST_NAME;
import static com.example.successcontribution.shared.Constant.FIRST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.LAST_NAME;
import static com.example.successcontribution.shared.Constant.LAST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.SAVINGS_BALANCE;
import static com.example.successcontribution.shared.Constant.SAVINGS_BALANCE_KEY;
import static com.example.successcontribution.shared.Constant.USER_ID;
import static com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private String mUsername;
    private String mPassword;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        hideOpeningKeyBoard(mBinding.etUsername);
        mPreferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
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
        mBinding.btnLogin.setOnClickListener(v -> {
            hideKeyboard(this);

            mUsername = mBinding.etUsername.getText().toString().trim();
            mPassword = mBinding.etPassword.getText().toString().trim();

            if (!mUsername.isEmpty() && !mPassword.isEmpty()) {
                signIn();
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        Utils utils = new Utils();
        UserLoginRequestModel loginRequestModel = new UserLoginRequestModel();
        loginRequestModel.setPassword(mPassword);

        if (utils.isNumber(mUsername) && mUsername.length() >= 11) { //Phone No.
            loginRequestModel.setPhoneNo(mUsername);
        }
        if (mUsername.contains(Character.toString('@'))) {
            loginRequestModel.setEmail(mUsername);
        }
        if (utils.isNumber(mUsername) && mUsername.length() < 11) { //Sap No.
            loginRequestModel.setSapNo(mUsername);
        }
        if (mUsername.length() == 30) {
            loginRequestModel.setUserId(mUsername);
        }

        attemptConnection(loginRequestModel);
    }

    private void attemptConnection(UserLoginRequestModel loginRequestModel) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.setUserLoginRequestModelData(loginRequestModel);

        viewModel.getHeadersLiveData().observe(this, new Observer<Headers>() {
            @Override
            public void onChanged(Headers pairs) {
                successConnection(pairs, progressDialog);
            }
        });

        viewModel.getNetworkErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });
    }

    private void successConnection(Headers pairs, ProgressDialog progressDialog) {
        String authorization = pairs.get(AUTHORIZATION_HEADER_STRING);
        String userId = pairs.get(USER_ID);
        String loginRole = pairs.get(LOGIN_ROLE);
        String balance = pairs.get(SAVINGS_BALANCE);
        String firstName = pairs.get(FIRST_NAME);
        String lastName = pairs.get(LAST_NAME);

        mPreferences.edit().putString(AUTHORIZATION_TOKEN_DEFAULT_KEY, authorization).apply();
        mPreferences.edit().putString(USER_ID_DEFAULT_KEY, userId).apply();
        mPreferences.edit().putString(LOGIN_ROLE_KEY, loginRole).apply();
        mPreferences.edit().putString(FIRST_NAME_KEY, firstName).apply();
        mPreferences.edit().putString(LAST_NAME_KEY, lastName).apply();

        progressDialog.dismiss();
        getViewModelStore().clear();
        Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
        intent.putExtra(LOGIN_ROLE_KEY, loginRole);
        intent.putExtra(SAVINGS_BALANCE_KEY, balance);
        intent.putExtra(FIRST_NAME_KEY, firstName);
        startActivity(intent);
        finish();
        getViewModelStore().clear();
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "" + errorMessage , Toast.LENGTH_SHORT).show();
        getViewModelStore().clear();
    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
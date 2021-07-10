package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.successcontribution.databinding.ActivityGetUserLoanBinding;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.ui.viewmodel.GetUserViewModel;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.FIRST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_PARCELABLE_EXTRA_KEY;
import static com.example.successcontribution.shared.Constant.LAST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.LOAN_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.SEARCH_USER_BY_GUARANTOR_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_STRING_EXTRA_TWO;
import static com.example.successcontribution.shared.Constant.USER;
import static com.example.successcontribution.shared.Constant.USER_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE;

public class GetUserLoanActivity extends AppCompatActivity {

    private ActivityGetUserLoanBinding mBinding;
    private static final String TAG = GetUserLoanActivity.class.getSimpleName();
    private SharedPreferences mPreferences;
    private String mUserId;
    private String mRole;
    private String mFullName;
    private String mOwnerFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityGetUserLoanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPreferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
        mOwnerFullName = mPreferences.getString(FIRST_NAME_KEY, "") + " " + mPreferences.getString(LAST_NAME_KEY, "");

        mRole = getIntent().getStringExtra(LOGIN_ROLE_KEY);
        Intent intent = getIntent();

        if (intent.hasExtra(GUARANTEE_LOAN_KEY) && intent.hasExtra(LOGIN_ROLE_KEY)) {
            guaranteeLoan();
        } else if (intent.hasExtra(APPROVE_LOAN_KEY)) {
            guaranteeLoan();
        }
    }

    private void guaranteeLoan() {
        mBinding.listUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListUsersActivity.class);
            intent.putExtra(SELECT_USER_BY_GUARANTOR_KEY, true);
            startActivityForResult(intent, SEARCH_USER_BY_GUARANTOR_REQUEST_CODE);
        });

        buttonSearch();
    }

    private void buttonSearch() {
        mBinding.btnSearch.setOnClickListener(v -> {
            if (!mBinding.userName.getText().toString().isEmpty() && !mBinding.etLoanId.getText().toString().trim().isEmpty()) {
                String loanId = mBinding.etLoanId.getText().toString().trim();

                findUserLoan(mUserId, loanId);
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findUserLoan(String userId, String loanId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        GetUserViewModel viewModel = provider.get(GetUserViewModel.class);
        viewModel.setStringCoupleData(userId, loanId);

        viewModel.getLoanRestLiveData().observe(this, new Observer<LoanRest>() {
            @Override
            public void onChanged(LoanRest loanRest) {
                successConnection(loanRest, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });
    }

    private void successConnection(LoanRest loanRest, ProgressDialog progressDialog) {
        progressDialog.dismiss();

        if (mRole.equals(USER) || USER.equals(mPreferences.getString(LOGIN_ROLE_KEY, ""))) { //user
            if (!mFullName.equals(loanRest.getName())) {
                Toast.makeText(this, "Please, select the correct person you want to guarantee", Toast.LENGTH_SHORT).show();
            } else if (mOwnerFullName.equals(loanRest.getName())) {
                Toast.makeText(this, "You cannot guarantee your own loan", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, LoanRequestFormActivity.class);
                intent.putExtra(GUARANTEE_LOAN_KEY, true);
                intent.putExtra(LOGIN_ROLE_USER_KEY, mPreferences.getString(LOGIN_ROLE_KEY, ""));
                intent.putExtra(GUARANTEE_LOAN_PARCELABLE_EXTRA_KEY, loanRest);
                intent.putExtra(USER_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE, mUserId);
                intent.putExtra(LOAN_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE, mBinding.etLoanId.getText().toString().trim());
                startActivity(intent);
                finish();
            }

        } else { //Admin
            Log.d(TAG, "findUserLoan: " + "Admin user");
        }
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();

        String specificMessage = errorMessage.substring(errorMessage.indexOf("message") + 10, errorMessage.length() - 2);

        if (!errorMessage.contains(specificMessage)) {
            return;
        } else {
            errorMessage = specificMessage;
        }

        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_USER_BY_GUARANTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: " + "Testing");

            mFullName = data.getStringExtra(SELECT_USER_BY_GUARANTOR_STRING_EXTRA_ONE);
            mUserId = data.getStringExtra(SELECT_USER_BY_GUARANTOR_STRING_EXTRA_TWO);

            Log.d(TAG, "onActivityResult: " + mFullName);
            Log.d(TAG, "onActivityResult: " + mUserId);

            mBinding.userName.setText(mFullName);
            if (mOwnerFullName.equals(mBinding.userName.getText().toString())) {
                Toast.makeText(this, "You have selected your own name", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
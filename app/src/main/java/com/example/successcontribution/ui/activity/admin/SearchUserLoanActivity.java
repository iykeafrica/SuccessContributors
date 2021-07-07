package com.example.successcontribution.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.successcontribution.databinding.ActivitySearchUserLoanBinding;
import com.example.successcontribution.ui.activity.ListUsersActivity;
import com.example.successcontribution.ui.activity.LoanRequestFormActivity;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.SEARCH_USER_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.USER_ID_FROM_INTENT;
import static com.example.successcontribution.shared.Constant.USER_NAME_FROM_INTENT;

public class SearchUserLoanActivity extends AppCompatActivity {

    private ActivitySearchUserLoanBinding mBinding;
    private static final String TAG = SearchUserLoanActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySearchUserLoanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.listUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListUsersActivity.class);
            startActivityForResult(intent, SEARCH_USER_REQUEST_CODE);
        });

        Intent intent = getIntent();

        if (intent.hasExtra(GUARANTEE_LOAN_KEY)) {
            guaranteeLoan();
        } else if (intent.hasExtra(APPROVE_LOAN_KEY)) {
            approveLoan();
        }

        regularSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.userNameHeader.setVisibility(View.GONE);
        mBinding.etUserId.setText("");
    }

    private void guaranteeLoan() {
        mBinding.btnSearch.setOnClickListener(v -> {
            if (!mBinding.etUserId.getText().toString().isEmpty() && !mBinding.etLoanId.getText().toString().trim().isEmpty()){
                searchByGuarantor();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchByGuarantor() {
        successfulByGuarantor();
    }

    private void successfulByGuarantor() {
        Intent intent = new Intent(this, LoanRequestFormActivity.class);
        intent.putExtra(GUARANTEE_LOAN_KEY, GUARANTEE_LOAN_VALUE);
//        add other extras to populate in the loan request form
        startActivity(intent);
    }

    private void approveLoan() {
        mBinding.btnSearch.setOnClickListener(v -> {
            if (!mBinding.etUserId.getText().toString().isEmpty() && !mBinding.etLoanId.getText().toString().trim().isEmpty()){
                searchByAdmin();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchByAdmin() {
        successfulByAdmin();
    }

    private void successfulByAdmin() {
        Intent intent = new Intent(this, LoanRequestFormActivity.class);
        intent.putExtra(APPROVE_LOAN_KEY, APPROVE_LOAN_VALUE);
        //        add other extras to populate in the loan request form
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_USER_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: " + "Testing");

            String userId = data.getStringExtra(USER_ID_FROM_INTENT);
            String userName = data.getStringExtra(USER_NAME_FROM_INTENT);
            mBinding.etUserId.setText(userId);
            mBinding.userNameHeader.setVisibility(View.VISIBLE);
            mBinding.accountName.setText(userName);
        }
    }

    private void regularSearch() {
        Toast.makeText(this, "Result of search", Toast.LENGTH_SHORT).show();
    }

}
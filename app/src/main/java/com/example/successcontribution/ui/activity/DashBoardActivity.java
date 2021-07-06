package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.successcontribution.databinding.ActivityDashBoardBinding;
import com.example.successcontribution.ui.activity.admin.LoanRequestFormActivity;
import com.example.successcontribution.ui.activity.admin.SearchUserLoanActivity;

import static com.example.successcontribution.utils.Constant.ADMIN;
import static com.example.successcontribution.utils.Constant.EXCO;
import static com.example.successcontribution.utils.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.utils.Constant.GUARANTEE_LOAN_VALUE;
import static com.example.successcontribution.utils.Constant.LOAN_CHECKER;
import static com.example.successcontribution.utils.Constant.REQUEST_LOAN_KEY;
import static com.example.successcontribution.utils.Constant.REQUEST_LOAN_VALUE;
import static com.example.successcontribution.utils.Constant.SUPER_ADMIN;
import static com.example.successcontribution.utils.Constant.USER;

public class DashBoardActivity extends AppCompatActivity {

    private ActivityDashBoardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        checkUserSignIn();
    }

    private void checkUserSignIn() {
        String loan_checker = LOAN_CHECKER;
        String user = USER;
        String admin = ADMIN;
        String super_admin = SUPER_ADMIN;
        String exco = EXCO;

        if (user.equals(USER)) {
            mBinding.userSignedIn.setVisibility(View.VISIBLE);

            checkLoanApplications();
            requestLoan();
            profile();
            listUsers();
            guaranteeLoan();
        } else {
            mBinding.admin.setVisibility(View.VISIBLE);

            goToAdminPage();
        }
    }

    private void checkLoanApplications() {
        mBinding.loanApplications.setOnClickListener(v -> {

        });
    }

    private void requestLoan() {
        Intent intent = new Intent(this, LoanRequestFormActivity.class);
        intent.putExtra(REQUEST_LOAN_KEY, REQUEST_LOAN_VALUE);
        startActivity(intent);
    }

    private void profile() {

    }

    private void listUsers() {

    }

    private void guaranteeLoan() {
        Intent intent = new Intent(this, SearchUserLoanActivity.class);
        intent.putExtra(GUARANTEE_LOAN_KEY, GUARANTEE_LOAN_VALUE);
        startActivity(intent);
    }

    private void goToAdminPage() {
        mBinding.admin.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminActivity.class));
        });
    }
}
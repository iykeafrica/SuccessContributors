package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.successcontribution.databinding.ActivityDashBoardBinding;
import com.example.successcontribution.ui.activity.admin.SearchUserLoanActivity;

import static com.example.successcontribution.shared.Constant.FIRST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.REQUEST_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.REQUEST_LOAN_VALUE;
import static com.example.successcontribution.shared.Constant.USER;

public class DashBoardActivity extends AppCompatActivity {

    private static final String TAG = DashBoardActivity.class.getSimpleName();
    private ActivityDashBoardBinding mBinding;
    private SharedPreferences mPreferences;
    private String mUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPreferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
        Intent intent = getIntent();

        if (intent.hasExtra(LOGIN_ROLE_KEY)){
            checkUserSignIn();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.userSignedIn.setVisibility(View.GONE);
        mBinding.admin.setVisibility(View.GONE);
        checkUserSignIn();
    }

    private void checkUserSignIn() {
        String role = getIntent().getStringExtra(LOGIN_ROLE_KEY);

        if (role.equals(USER) || USER.equals(mPreferences.getString(LOGIN_ROLE_KEY, ""))) {
            mBinding.userSignedIn.setVisibility(View.VISIBLE);
            mBinding.name.setText((mPreferences.getString(FIRST_NAME_KEY, "")));

            mUserRole = mPreferences.getString(LOGIN_ROLE_KEY, "");

            Log.d(TAG, "checkUserSignIn: LOGIN_ROLE_KEY " + mPreferences.getString(LOGIN_ROLE_KEY, ""));
            Log.d(TAG, "checkUserSignIn: FIRST_NAME_KEY " + mPreferences.getString(FIRST_NAME_KEY, ""));

            checkLoanApplications();
            requestLoan();
            profile();
            listUsers();
            guaranteeLoan();
        } else {
            mBinding.admin.setVisibility(View.VISIBLE);
            mBinding.name.setText((mPreferences.getString(FIRST_NAME_KEY, "")));
            goToAdminPage();
        }
    }

    private void checkLoanApplications() {
        mBinding.loanApplications.setOnClickListener(v -> {

        });
    }

    private void requestLoan() {
        mBinding.requestLoan.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoanRequestFormActivity.class);
            intent.putExtra(LOGIN_ROLE_USER_KEY, mUserRole);
            startActivity(intent);
        });
    }

    private void profile() {
        mBinding.profile.setOnClickListener(v -> {

        });
    }

    private void listUsers() {
        mBinding.contributors.setOnClickListener(v -> {

        });
    }

    private void guaranteeLoan() {
        mBinding.guaranteeLoan.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchUserLoanActivity.class);
            intent.putExtra(LOGIN_ROLE_KEY, mUserRole);
            intent.putExtra(GUARANTEE_LOAN_KEY, true);
            startActivity(intent);
        });
    }

    private void goToAdminPage() {
        mBinding.admin.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminActivity.class));
        });
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

}
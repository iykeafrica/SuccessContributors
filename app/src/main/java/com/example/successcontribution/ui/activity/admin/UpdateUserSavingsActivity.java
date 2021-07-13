package com.example.successcontribution.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityUpdateUserLoanStatusBinding;
import com.example.successcontribution.databinding.ActivityUpdateUserSavingsBinding;
import com.example.successcontribution.model.request.UserDepositedFundRequestModel;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.activity.ListUsersActivity;
import com.example.successcontribution.ui.viewmodel.UpdateUserSavingsViewModel;

import static com.example.successcontribution.shared.Constant.SEARCH_USER_BY_ADMIN_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_STRING_EXTRA_TWO;

public class UpdateUserSavingsActivity extends AppCompatActivity {

    ActivityUpdateUserSavingsBinding mBinding;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityUpdateUserSavingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        getUserDetails();
        updateSavings();
    }

    private void updateSavings() {
        mBinding.btnUpdate.setOnClickListener(v -> {
            if (!mBinding.userName.getText().toString().trim().isEmpty() && !mBinding.etAmount.getText().toString().trim().isEmpty()) {
                confirmUpdate();
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendSavingsUpdate() {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        UpdateUserSavingsViewModel viewModel = provider.get(UpdateUserSavingsViewModel.class);

        UserDepositedFundRequestModel requestModel = new UserDepositedFundRequestModel();
        requestModel.setDepositedFund(mBinding.etAmount.getText().toString().trim());

        viewModel.setUserSavingsCoupleData(mUserId, requestModel);

        attemptConnection(viewModel, progressDialog);
    }

    private void attemptConnection(UpdateUserSavingsViewModel viewModel, ProgressDialog progressDialog) {
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
        Toast.makeText(this,  userRest.getFirstName()
                + " account has been updated with \n" + "₦" + userRest.getDepositedFund(), Toast.LENGTH_LONG).show();

        mBinding.userName.setText("");
        mBinding.userName.setHint("Select user from list");
        mBinding.etAmount.setText("");
        mBinding.etAmount.setHint("Amount");
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        getViewModelStore().clear();
    }

    private void getUserDetails() {
        mBinding.listUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListUsersActivity.class);
            intent.putExtra(SELECT_USER_BY_ADMIN_KEY, true);
            startActivityForResult(intent, SEARCH_USER_BY_ADMIN_REQUEST_CODE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_USER_BY_ADMIN_REQUEST_CODE && resultCode == RESULT_OK) {
            String fullName = data.getStringExtra(SELECT_USER_BY_ADMIN_STRING_EXTRA_ONE);
            mUserId = data.getStringExtra(SELECT_USER_BY_ADMIN_STRING_EXTRA_TWO);

            mBinding.userName.setText(fullName);

            confirmUpdate();
        }
    }

    private void confirmUpdate() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Confirm you want to update user savings?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                sendSavingsUpdate();
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
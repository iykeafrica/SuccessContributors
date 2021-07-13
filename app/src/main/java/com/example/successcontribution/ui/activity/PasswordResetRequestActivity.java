package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityPasswordResetRequestBinding;
import com.example.successcontribution.model.request.PasswordResetRequestModel;
import com.example.successcontribution.model.response.OperationStatusModel;
import com.example.successcontribution.ui.viewmodel.PasswordResetRequestViewModel;

public class PasswordResetRequestActivity extends AppCompatActivity {

    ActivityPasswordResetRequestBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityPasswordResetRequestBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        hideOpeningKeyBoard(mBinding.etEmail);

        submit();
    }

    private void submit() {
        mBinding.btnSubmit.setOnClickListener(v -> {
            if (!mBinding.etEmail.getText().toString().trim().isEmpty()) {
                sendResetLink();
            } else {
                Toast.makeText(this, "Please, enter your email address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResetLink() {
        hideKeyboard(this);
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        PasswordResetRequestViewModel viewModel = new ViewModelProvider(this).get(PasswordResetRequestViewModel.class);
        PasswordResetRequestModel model = new PasswordResetRequestModel();
        model.setEmail(mBinding.etEmail.getText().toString().trim());

        viewModel.setPasswordResetRequestData(model);
        viewModel.getOperationStatusModelLiveData().observe(this, new Observer<OperationStatusModel>() {
            @Override
            public void onChanged(OperationStatusModel operationStatusModel) {
                successConnection(operationStatusModel, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });
    }

    private void successConnection(OperationStatusModel operationStatusModel, ProgressDialog progressDialog) {
        progressDialog.dismiss();

        Toast.makeText(this, operationStatusModel.getOperationResult(), Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();

        if (errorMessage.length() > 15) {
            String specificMessage = errorMessage.substring(errorMessage.indexOf("message") + 10, errorMessage.length() - 2);

            if (!errorMessage.contains(specificMessage)) {
                return;
            } else {
                errorMessage = specificMessage;
            }
        }

        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
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
package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityProfileBinding;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();

        mBinding.scrollview.setBackground(ContextCompat.getDrawable(this, R.drawable.background_two));
        mBinding.layoutMain.setVisibility(View.GONE);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving your information please wait...");
        progressDialog.show();

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        ProfileViewModel viewModel = provider.get(ProfileViewModel.class);

        viewModel.getUserRestLiveData().observe(this, new Observer<UserRest>() {
            @Override
            public void onChanged(UserRest userRest) {
                successConnection (userRest, progressDialog);
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
        mBinding.scrollview.setBackgroundColor(getResources().getColor(R.color.white));
        mBinding.layoutMain.setVisibility(View.VISIBLE);
        mBinding.name.setText(userRest.getFirstName() + " " + userRest.getLastName());
        mBinding.savingBalance.setText("₦" + userRest.getDepositedFund());
        mBinding.email.setText(userRest.getEmail());
        mBinding.department.setText(userRest.getDepartment());
        mBinding.phoneNumber.setText(userRest.getPhoneNo());
        mBinding.address.setText(userRest.getAddress());
        mBinding.whatsappNumber.setText(userRest.getWhatsappNo());
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        Toast.makeText(this, "" + errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        getViewModelStore().clear();
    }
}
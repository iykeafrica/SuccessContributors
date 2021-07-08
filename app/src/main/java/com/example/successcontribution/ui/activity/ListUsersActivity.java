package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityListUsersBinding;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.adapter.ListUsersAdapter;
import com.example.successcontribution.ui.viewmodel.ListUsersViewModel;

import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private ActivityListUsersBinding mBinding;
    private ListUsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityListUsersBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListUsersAdapter();
        mBinding.recyclerview.setAdapter(mAdapter);

        listUsers();
    }

    private void listUsers() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        ListUsersViewModel viewModel = provider.get(ListUsersViewModel.class);
        attemptConnection(viewModel);
    }

    private void attemptConnection(ListUsersViewModel viewModel) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        viewModel.getListUserRestLiveData().observe(this, new Observer<List<UserRest>>() {
            @Override
            public void onChanged(List<UserRest> userRests) {
                successConnection(userRests, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });
    }

    private void successConnection(List<UserRest> userRests, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        mAdapter.submitList(userRests);

        mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
            @Override
            public void selectUser(int position, UserRest userRest) {
                Toast.makeText(ListUsersActivity.this, "" + userRest.getUserId(), Toast.LENGTH_SHORT).show();
            }
        });
        getViewModelStore().clear();
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }
}
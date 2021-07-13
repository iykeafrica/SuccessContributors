package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityListLoanApplicationsBinding;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.ui.adapter.ListLoanApplicationsAdapter;
import com.example.successcontribution.ui.adapter.ListUsersAdapter;
import com.example.successcontribution.ui.viewmodel.ListUserLoanApplicationsViewModel;

import java.util.List;

public class ListLoanApplicationsActivity extends AppCompatActivity {

    private ActivityListLoanApplicationsBinding mBinding;
    private ListLoanApplicationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityListLoanApplicationsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListLoanApplicationsAdapter();
        mBinding.recyclerview.setAdapter(mAdapter);

        listUserLoans();
    }

    private void listUserLoans() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        ListUserLoanApplicationsViewModel viewModel = provider.get(ListUserLoanApplicationsViewModel.class);

        attemptConnection(viewModel);
    }

    private void attemptConnection(ListUserLoanApplicationsViewModel viewModel) {

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        viewModel.getListLiveData().observe(this, new Observer<List<LoanRest>>() {
            @Override
            public void onChanged(List<LoanRest> loanRests) {
                successConnection(loanRests, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });

    }

    private void successConnection(List<LoanRest> loanRests, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        mAdapter.submitList(loanRests);

        mAdapter.setClickListener(new ListLoanApplicationsAdapter.ClickListener() {
            @Override
            public void selectLoan(int position, LoanRest loanRest) {
                Toast.makeText(ListLoanApplicationsActivity.this, "" + loanRest.getLoanId(), Toast.LENGTH_SHORT).show();
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
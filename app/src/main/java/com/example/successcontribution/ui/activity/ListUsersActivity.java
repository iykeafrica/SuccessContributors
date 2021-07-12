package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.successcontribution.databinding.ActivityListUsersBinding;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.adapter.ListUsersAdapter;
import com.example.successcontribution.ui.viewmodel.ListUsersViewModel;

import java.util.List;

import static com.example.successcontribution.shared.Constant.FROM_DASH_BOARD_ACTIVITY_TO_LIST_USERS_ACTIVITY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_ONE_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_ONE_STRING_EXTRA;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_TWO_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_TWO_STRING_EXTRA;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_ADMIN_STRING_EXTRA_TWO;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.SELECT_USER_BY_GUARANTOR_STRING_EXTRA_TWO;

public class ListUsersActivity extends AppCompatActivity {

    private static final String TAG = ListUsersActivity.class.getSimpleName();
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
        incomingIntent();
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
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    private void incomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(SELECT_GUARANTOR_ONE_KEY)) {
            mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
                @Override
                public void selectUser(int position, UserRest userRest) {
                    String fullName = userRest.getFirstName() + " " + userRest.getLastName();
                    Intent intent = new Intent(ListUsersActivity.this, LoanRequestFormActivity.class);
                    intent.putExtra(SELECT_GUARANTOR_ONE_STRING_EXTRA, fullName);
                    setResult(RESULT_OK, intent);
                    onBackPressed();

                    Toast.makeText(ListUsersActivity.this, fullName + " Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (intent.hasExtra(SELECT_GUARANTOR_TWO_KEY)) {
            mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
                @Override
                public void selectUser(int position, UserRest userRest) {
                    String fullName = userRest.getFirstName() + " " + userRest.getLastName();
                    Intent intent = new Intent(ListUsersActivity.this, LoanRequestFormActivity.class);
                    intent.putExtra(SELECT_GUARANTOR_TWO_STRING_EXTRA, fullName);
                    setResult(RESULT_OK, intent);
                    onBackPressed();

                    Toast.makeText(ListUsersActivity.this, fullName + " Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (intent.hasExtra(SELECT_USER_BY_GUARANTOR_KEY)) {
            mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
                @Override
                public void selectUser(int position, UserRest userRest) {
                    String fullName = userRest.getFirstName() + " " + userRest.getLastName();
                    Intent intent = new Intent(ListUsersActivity.this, LoanRequestFormActivity.class);
                    intent.putExtra(SELECT_USER_BY_GUARANTOR_STRING_EXTRA_ONE, fullName);
                    intent.putExtra(SELECT_USER_BY_GUARANTOR_STRING_EXTRA_TWO, userRest.getUserId());
                    setResult(RESULT_OK, intent);
                    onBackPressed();

                    Toast.makeText(ListUsersActivity.this, fullName + " Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (intent.hasExtra(SELECT_USER_BY_ADMIN_KEY)) {
            mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
                @Override
                public void selectUser(int position, UserRest userRest) {
                    String fullName = userRest.getFirstName() + " " + userRest.getLastName();
                    Intent intent = new Intent(ListUsersActivity.this, LoanRequestFormActivity.class);
                    intent.putExtra(SELECT_USER_BY_ADMIN_STRING_EXTRA_ONE, fullName);
                    intent.putExtra(SELECT_USER_BY_ADMIN_STRING_EXTRA_TWO, userRest.getUserId());
                    setResult(RESULT_OK, intent);
                    onBackPressed();

                    Toast.makeText(ListUsersActivity.this, fullName + " Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (intent.hasExtra(FROM_DASH_BOARD_ACTIVITY_TO_LIST_USERS_ACTIVITY)) {
            mAdapter.setClickListener(new ListUsersAdapter.ClickListener() {
                @Override
                public void selectUser(int position, UserRest userRest) {
                    Toast.makeText(ListUsersActivity.this, userRest.getFirstName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "incomingIntent: " + intent.getBooleanExtra(FROM_DASH_BOARD_ACTIVITY_TO_LIST_USERS_ACTIVITY, false));
                }
            });
        }
    }

}
package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityListUsersBinding;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.ui.adapter.ListUsersAdapter;
import com.example.successcontribution.ui.viewmodel.ListUsersViewModel;
import com.example.successcontribution.ui.viewmodel.PagedUserRestViewModel;

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

        hideOpeningKeyBoard(mBinding.searchUser);

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListUsersAdapter();
        mBinding.recyclerview.setAdapter(mAdapter);

        listUsers();

        initSearch();
        mBinding.searchUser.addTextChangedListener(mTextWatcher);

        incomingIntent();
        refreshLayout();
    }

    private void refreshLayout() {
        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listUsers();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mBinding.swipe.isRefreshing()) {
                            mBinding.swipe.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void listUsers() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

//        ListUsersViewModel viewModel = provider.get(ListUsersViewModel.class);
        PagedUserRestViewModel viewModel = provider.get(PagedUserRestViewModel.class);
        attemptConnection(viewModel);
    }

//    private void attemptConnection(ListUsersViewModel viewModel) {
//        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
//        progressDialog.setMessage("Authenticating please wait...");
//        progressDialog.show();
//
//        viewModel.getListUserRestLiveData().observe(this, new Observer<List<UserRest>>() {
//            @Override
//            public void onChanged(List<UserRest> userRests) {
//                successConnection(userRests, progressDialog);
//            }
//        });
//
//        viewModel.getNetworkError().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String errorMessage) {
//                errorConnection(errorMessage, progressDialog);
//            }
//        });
//    }

    private void attemptConnection(PagedUserRestViewModel viewModel) {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        viewModel.getPagedListLiveData().observe(this, new Observer<PagedList<UserRest>>() {
            @Override
            public void onChanged(PagedList<UserRest> userRests) {
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

//    private void successConnection(List<UserRest> userRests, ProgressDialog progressDialog) {
//        progressDialog.dismiss();
//        mAdapter.submitList(userRests);
//    }

    private void successConnection(PagedList<UserRest> userRests, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        mAdapter.submitList(userRests);
        mAdapter.notifyDataSetChanged();

        Log.d(TAG, "successConnection: userRest size = " + userRests.size());
    }

    private void searchUser() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        PagedUserRestViewModel viewModel = provider.get(PagedUserRestViewModel.class);

        viewModel.setQueryMutableLiveData(mBinding.searchUser.getText().toString().trim());
        viewModel.getPagedListByUserLiveData().observe(this, new Observer<PagedList<UserRest>>() {
            @Override
            public void onChanged(PagedList<UserRest> userRests) {
                if (userRests == null) {
                    Toast.makeText(ListUsersActivity.this, "The user you are looking for, does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.submitList(userRests);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getNetworkByUserError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ListUsersActivity.this, "No user", Toast.LENGTH_SHORT).show();
            }
        });
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
                    hideKeyboard(ListUsersActivity.this);

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
                    hideKeyboard(ListUsersActivity.this);

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
                    hideKeyboard(ListUsersActivity.this);

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
                    hideKeyboard(ListUsersActivity.this);

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

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchUser();
            mBinding.searchUser.setCursorVisible(true);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mBinding.searchUser.getText().toString().trim().equals("")){
                listUsers();
                mBinding.searchUser.setCursorVisible(false);
            }
        }
    };

    private void initSearch() {
        mBinding.searchUser.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                searchUser();
                hideKeyboard(this);
                return true;
            } else {
                return false;
            }
        });

        mBinding.searchUser.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser();
                hideKeyboard(this);
                return true;
            } else {
                return false;
            }
        });
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
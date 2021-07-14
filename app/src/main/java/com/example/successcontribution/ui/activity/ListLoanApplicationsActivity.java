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
import com.example.successcontribution.databinding.ActivityListLoanApplicationsBinding;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.shared.DatePicker;
import com.example.successcontribution.ui.adapter.ListLoanApplicationsAdapter;
import com.example.successcontribution.ui.adapter.ListLoanApplicationsAdapter2;
import com.example.successcontribution.ui.adapter.ListUsersAdapter;
import com.example.successcontribution.ui.viewmodel.ListUserLoanApplicationsViewModel;
import com.example.successcontribution.ui.viewmodel.PagedLoanRestViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListLoanApplicationsActivity extends AppCompatActivity {

    public static final String TAG = ListLoanApplicationsActivity.class.getSimpleName();
    private ActivityListLoanApplicationsBinding mBinding;
    private ListLoanApplicationsAdapter mAdapter;
    private DatePicker mDatePicker;
    private Calendar mCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityListLoanApplicationsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListLoanApplicationsAdapter();
        mBinding.recyclerview.setAdapter(mAdapter);

        hideOpeningKeyBoard(mBinding.searchLoan);

        listUserLoans();

        mCal = Calendar.getInstance();
        setDateApplied();

        mBinding.searchLoan.addTextChangedListener(mTextWatcher);

        initSearch();

        refreshLayout();
    }

    private void setDateApplied() {
        mDatePicker = new DatePicker(this, mCal, mBinding.searchLoan);
        mDatePicker.setDateText();
    }

    private void refreshLayout() {
        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listUserLoans();

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

    private void listUserLoans() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

//        ListUserLoanApplicationsViewModel viewModel = provider.get(ListUserLoanApplicationsViewModel.class);
        PagedLoanRestViewModel viewModel = provider.get(PagedLoanRestViewModel.class);

        attemptConnection(viewModel);
    }

    private void attemptConnection(PagedLoanRestViewModel viewModel) {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();


        viewModel.getPagedListLiveData().observe(this, new Observer<PagedList<LoanRest>>() {
            @Override
            public void onChanged(PagedList<LoanRest> loanRests) {
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

    private void successConnection(PagedList<LoanRest> loanRests, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        mAdapter.submitList(loanRests);
        mAdapter.notifyDataSetChanged();

        mAdapter.setClickListener(new ListLoanApplicationsAdapter.ClickListener() {
            @Override
            public void selectLoan(int position, LoanRest loanRest) {
                Toast.makeText(ListLoanApplicationsActivity.this, "" + loanRest.getLoanId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void attemptConnection(ListUserLoanApplicationsViewModel viewModel) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
//        progressDialog.setMessage("Authenticating please wait...");
//        progressDialog.show();
//
//        viewModel.getListLiveData().observe(this, new Observer<List<LoanRest>>() {
//            @Override
//            public void onChanged(List<LoanRest> loanRests) {
//                successConnection(loanRests, progressDialog);
//            }
//        });
//
//        viewModel.getNetworkError().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String errorMessage) {
//                errorConnection(errorMessage, progressDialog);
//            }
//        });
//
//    }

//    private void successConnection(List<LoanRest> loanRests, ProgressDialog progressDialog) {
//        progressDialog.dismiss();
//        mAdapter.submitList(loanRests);
//
//        mAdapter.setClickListener(new ListLoanApplicationsAdapter.ClickListener() {
//            @Override
//            public void selectLoan(int position, LoanRest loanRest) {
//                Toast.makeText(ListLoanApplicationsActivity.this, "" + loanRest.getLoanId(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        getViewModelStore().clear();
//    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    private void searchLoan() {
        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        PagedLoanRestViewModel viewModel = provider.get(PagedLoanRestViewModel.class);

        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = (mDatePicker.getDay() + "-" + mDatePicker.getMonth() + "-" + mDatePicker.getYear() + " " + "00" + ":" + "00" + ":" + "00");
        try {
            Date date = dateFormat.parse(strDate);
            mCal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewModel.setQueryMutableLiveData(mCal.getTimeInMillis());
        Log.d(TAG, "searchLoan: time in milliseconds " + mCal.getTimeInMillis());

        viewModel.getListLiveData().observe(this, new Observer<List<LoanRest>>() {
            @Override
            public void onChanged(List<LoanRest> loanRests) {
                mAdapter.submitList(loanRests);
            }
        });

//        viewModel.getPagedListByLoanLiveData().observe(this, new Observer<PagedList<LoanRest>>() {
//            @Override
//            public void onChanged(PagedList<LoanRest> loanRests) {
//                if (loanRests == null) {
//                    Toast.makeText(ListLoanApplicationsActivity.this, "You don't a loan on this date", Toast.LENGTH_SHORT).show();
//                } else {
//                    mAdapter.submitList(loanRests);
//                    mAdapter.notifyDataSetChanged();
//                }
//                getViewModelStore().clear();
//            }
//        });

//        viewModel.getNetworkByLoanError().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String errorMessage) {
//                Toast.makeText(ListLoanApplicationsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchLoan();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mBinding.searchLoan.getText().toString().trim().equals("")){
                listUserLoans();
            }
        }
    };

    private void initSearch() {
        mBinding.searchLoan.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                searchLoan();
                hideKeyboard(this);
                return true;
            } else {
                return false;
            }
        });

        mBinding.searchLoan.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchLoan();
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
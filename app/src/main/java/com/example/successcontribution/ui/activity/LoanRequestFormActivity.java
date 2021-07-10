package com.example.successcontribution.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityLoanRequestFormBinding;
import com.example.successcontribution.model.request.GuarantorLoanRequestModel;
import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.shared.DatePicker;
import com.example.successcontribution.ui.viewmodel.LoanRequestViewModel;
import com.example.successcontribution.ui.viewmodel.UpdateUserLoanApplicationByGuarantorViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_PARCELABLE_EXTRA_KEY;
import static com.example.successcontribution.shared.Constant.EXCO_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.FIRST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_PARCELABLE_EXTRA_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTOR_ONE_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.GUARANTOR_TWO_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.HAS_SUBMITTED_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.LOAN_CHECKER_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.LOAN_ID_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.LOAN_ID_SENT_BY_ADMIN_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.LOAN_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.NAME_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.PRESIDENT_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_ONE_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_ONE_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_ONE_STRING_EXTRA;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_TWO_KEY;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_TWO_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.LAST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.SELECT_GUARANTOR_TWO_STRING_EXTRA;
import static com.example.successcontribution.shared.Constant.STATUS_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.USER_ID_SENT_BY_ADMIN_STRING_EXTRA_ONE;
import static com.example.successcontribution.shared.Constant.USER_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE;

public class LoanRequestFormActivity extends AppCompatActivity {

    ActivityLoanRequestFormBinding mBinding;
    private String mAmount;
    private String mGuarantorOne;
    private String mGuarantorTwo;
    private String mReason;
    private long mDateApplied;
    private String mDateAppliedString;
    private String mGuarantorOneConfirmation;
    private String mGuarantorTwoConfirmation;
    private String mOfficialOne;
    private String mOfficialTwo;
    private String mOfficialThree;
    private String mPresident;
    private long mDateStatus;
    private String mName;
    private DatePicker mDatePicker;
    private Calendar mCal1;
    private Calendar mCal2;
    private Calendar mCal3;
    private long mRepayment;
    private String mDateRepayment;
    private String mStatus;
    private String mDateStatusString;
    private String mStatusUpdate;
    private SharedPreferences mPreferences;
    private String mSelectedGuarantorOne;
    private String mSelectedGuarantorTwo;
    private String mFullName;
    private String mFormGuarantorOneFullName;
    private String mFormGuarantorTwoFullName;
    private String mUserId;
    private String mLoanId;
    private boolean hasSubmitted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoanRequestFormBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        if (savedInstanceState == null)
            hasSubmitted = false;
        else
            hasSubmitted = savedInstanceState.getBoolean(HAS_SUBMITTED_SAVED_INSTANCE_STATE);

        hideOpeningKeyBoard(mBinding.amount);
        mPreferences = getApplicationContext().getSharedPreferences(MY_PREF, 0);
        mCal1 = Calendar.getInstance();
        mCal2 = Calendar.getInstance();
        mCal3 = Calendar.getInstance();
        setDateApplied();
        setDateRepayment();
        setDateStatus();

        clickListUsers();
    }


    private void clickListUsers() {
        mBinding.guarantorOneListUsers.setOnClickListener(v -> {
            hideKeyboard(this);
            Intent intent = new Intent(this, ListUsersActivity.class);
            intent.putExtra(SELECT_GUARANTOR_ONE_KEY, true);
            startActivityForResult(intent, SELECT_GUARANTOR_ONE_REQUEST_CODE);
        });

        mBinding.guarantorTwoListUsers.setOnClickListener(v -> {
            hideKeyboard(this);
            Intent intent = new Intent(this, ListUsersActivity.class);
            intent.putExtra(SELECT_GUARANTOR_TWO_KEY, true);
            startActivityForResult(intent, SELECT_GUARANTOR_TWO_REQUEST_CODE);
        });
    }

    private void setDateApplied() {
        mDatePicker = new DatePicker(this, mCal1, mBinding.dateApplied);
        mDatePicker.setDateText();
    }

    private void setDateRepayment() {
        mDatePicker = new DatePicker(this, mCal2, mBinding.repayment);
        mDatePicker.setDateText();
    }

    private void setDateStatus() {
        mDatePicker = new DatePicker(this, mCal3, mBinding.dateStatus);
        mDatePicker.setDateText();
    }

    private void requestLoan() {
        String name = mPreferences.getString(FIRST_NAME_KEY, "") + " " + mPreferences.getString(LAST_NAME_KEY, "");
        mBinding.name.setText(name);

        mBinding.userSubmit.setOnClickListener(v -> {
            if (!mBinding.name.getText().toString().trim().isEmpty()) {
                mName = mBinding.name.getText().toString().trim();
            }

            if (mBinding.amount.getText().toString().trim().isEmpty()) {
                mBinding.amountRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.amountRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.amountRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.amountRequiredText.setTextColor(getResources().getColor(R.color.black));
                mAmount = mBinding.amount.getText().toString().trim();
            }

            if (mBinding.guarantorOne.getText().toString().trim().isEmpty()) {
                mBinding.guarantorOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.guarantorOneRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.guarantorOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorOneRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorOne = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.guarantorTwo.getText().toString().trim().isEmpty()) {
                mBinding.guarantorTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.guarantorTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.guarantorTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorTwo = mBinding.guarantorTwo.getText().toString().trim();
            }

            if (mBinding.reason.getText().toString().trim().isEmpty()) {
                mBinding.reasonRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.reasonRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.reasonRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.reasonRequiredText.setTextColor(getResources().getColor(R.color.black));
                mReason = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.dateApplied.getText().toString().trim().isEmpty()) {
                mBinding.dateAppliedRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.dateAppliedRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.dateAppliedRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.dateAppliedRequiredText.setTextColor(getResources().getColor(R.color.black));

                DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
                mDateAppliedString = mBinding.dateApplied.getText().toString();
                String strDate = (mDatePicker.getDay() + "-" + mDatePicker.getMonth() + "-" + mDatePicker.getYear());
                try {
                    Date date = dateFormat.parse(strDate);
                    mCal1.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDateApplied = mCal1.getTimeInMillis();
            }

            if (mBinding.repayment.getText().toString().trim().isEmpty()) {
                mBinding.repaymentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.repaymentRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.repaymentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.repaymentRequiredText.setTextColor(getResources().getColor(R.color.black));

                DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
                mDateRepayment = mBinding.repayment.getText().toString();
                String strDate = (mDatePicker.getDay() + "-" + mDatePicker.getMonth() + "-" + mDatePicker.getYear());
                try {
                    Date date = dateFormat.parse(strDate);
                    mCal2.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mRepayment = mCal2.getTimeInMillis();
            }

            clickSubmitLoanRequest();

        });
    }

    private void clickSubmitLoanRequest() {

        if (!mName.isEmpty() && mAmount != null && mGuarantorOne != null && mGuarantorTwo != null
                && mReason != null && mDateAppliedString != null && mDateRepayment != null) {
            submitLoanRequest(mName, mAmount, mGuarantorOne, mGuarantorTwo, mReason, mDateApplied, mRepayment);
        } else {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitLoanRequest(String name, String amount, String guarantorOne, String guarantorTwo,
                                   String reason, long dateApplied, long dateRepayment) {

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        LoanRequestViewModel viewModel = provider.get(LoanRequestViewModel.class);

        LoanRequestModel loanRequestModel = new LoanRequestModel();
        loanRequestModel.setAmount(amount);
        loanRequestModel.setName(name);
        loanRequestModel.setGuarantorOne(guarantorOne);
        loanRequestModel.setGuarantorTwo(guarantorTwo);
        loanRequestModel.setReason(reason);
        loanRequestModel.setRequestDate(dateApplied);
        loanRequestModel.setRepaymentDate(dateRepayment);

        attemptPostLoanRequest(viewModel, loanRequestModel);
    }

    private void attemptPostLoanRequest(LoanRequestViewModel viewModel, LoanRequestModel loanRequestModel) {
        hideKeyboard(this);
        viewModel.setLoanRequestModelData(loanRequestModel);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        viewModel.getLoanRestLiveData().observe(this, new Observer<LoanRest>() {
            @Override
            public void onChanged(LoanRest loanRest) {
                successConnection(loanRest, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnection(errorMessage, progressDialog);
            }
        });
    }

    private void successConnection(LoanRest loanRest, ProgressDialog progressDialog) {
        mBinding.statusHeader.setVisibility(View.VISIBLE);
        mBinding.loanIdHeader.setVisibility(View.VISIBLE);
        mBinding.amount.setEnabled(false);
        mBinding.guarantorOne.setEnabled(false);
        mBinding.guarantorOneListUsers.setEnabled(false);
        mBinding.guarantorTwo.setEnabled(false);
        mBinding.guarantorTwoListUsers.setEnabled(false);
        mBinding.reason.setEnabled(false);
        mBinding.dateApplied.setEnabled(false);
        mBinding.repayment.setEnabled(false);
        mBinding.userSubmit.setEnabled(false);
        mBinding.officialSection.setVisibility(View.VISIBLE);

        mBinding.loanId.setText(loanRest.getLoanId());
        mBinding.copyLoanId.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Loan Id", "" + loanRest.getLoanId());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, clip.getDescription().getLabel() + " copied", Toast.LENGTH_SHORT).show();
        });
        mBinding.status.setText(loanRest.getStatus());

        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Loan request sent successfully!", Toast.LENGTH_LONG).show();
        hasSubmitted = true;
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

    private void guaranteeLoan() {
        mBinding.guarantorSubmit.setOnClickListener(v -> {

            if (mFullName.equals(mFormGuarantorOneFullName) || mFullName.equals(mFormGuarantorTwoFullName)) {
                mGuarantorOneConfirmation = mBinding.guarantorOneConfirmation.getText().toString().trim();
                mGuarantorTwoConfirmation = mBinding.guarantorTwoConfirmation.getText().toString().trim();

                if (mFullName.equals(mFormGuarantorOneFullName)) {
                    if (mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty()) {
                        Toast.makeText(this, "Please, filling the required field", Toast.LENGTH_SHORT).show();
                        mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                        mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                        mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));

                        attemptToUpdateGuarantor(mGuarantorOneConfirmation, mGuarantorTwoConfirmation);
                    }
                } else {
                    if (mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty()) {
                        Toast.makeText(this, "Please, filling the required field", Toast.LENGTH_SHORT).show();
                        mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                        mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                        mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));

                        attemptToUpdateGuarantor(mGuarantorOneConfirmation, mGuarantorTwoConfirmation);
                    }
                }
            }
        });
    }

    private void attemptToUpdateGuarantor(String guarantorOneConfirmation, String guarantorTwoConfirmation) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        GuarantorLoanRequestModel requestModel = new GuarantorLoanRequestModel();
        requestModel.setGuarantorOneConfirmation(guarantorOneConfirmation);
        requestModel.setGuarantorTwoConfirmation(guarantorTwoConfirmation);

        ViewModelProvider provider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));

        UpdateUserLoanApplicationByGuarantorViewModel viewModel = provider.get(UpdateUserLoanApplicationByGuarantorViewModel.class);
        viewModel.setUpdateLoanByGuarantorCoupleData(mUserId, mLoanId, requestModel);

        viewModel.getLoanRestLiveData().observe(this, new Observer<LoanRest>() {
            @Override
            public void onChanged(LoanRest loanRest) {
                successConnectionGuarantor(loanRest, progressDialog);
            }
        });

        viewModel.getNetworkError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                errorConnectionGuarantor(errorMessage, progressDialog);
            }
        });
    }

    private void successConnectionGuarantor(LoanRest loanRest, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        mBinding.guarantorOneConfirmationHeader.setVisibility(View.VISIBLE);
        mBinding.guarantorOneConfirmation.setEnabled(false);
        mBinding.guarantorTwoConfirmationHeader.setVisibility(View.VISIBLE);
        mBinding.guarantorTwoConfirmation.setEnabled(false);
        mBinding.guarantorSubmit.setEnabled(false);
        mBinding.status.setVisibility(View.VISIBLE);
        Toast.makeText(this, "You have successfully guaranteed " + loanRest.getName(), Toast.LENGTH_SHORT).show();
        hasSubmitted = true;
    }

    private void errorConnectionGuarantor(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        String specificMessage = errorMessage.substring(errorMessage.indexOf("message") + 10, errorMessage.length() - 2);

        if (!errorMessage.contains(specificMessage)) {
            return;
        } else {
            errorMessage = specificMessage;
        }

        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }


    private void approveLoan() {
        mBinding.officialSubmit.setOnClickListener(v -> {
            mStatus = mBinding.status.getText().toString();

            if (mBinding.officialOne.getText().toString().trim().isEmpty()) {
                mBinding.officialOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.officialOneRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.officialOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.officialOneRequiredText.setTextColor(getResources().getColor(R.color.black));
                mOfficialOne = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.officialTwo.getText().toString().trim().isEmpty()) {
                mBinding.officialTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.officialTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.officialTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.officialTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
                mOfficialTwo = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.officialThree.getText().toString().trim().isEmpty()) {
                mBinding.officialThreeRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.officialThreeRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.officialThreeRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.officialThreeRequiredText.setTextColor(getResources().getColor(R.color.black));
                mOfficialThree = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.president.getText().toString().trim().isEmpty()) {
                mBinding.presidentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.presidentRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.presidentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.presidentRequiredText.setTextColor(getResources().getColor(R.color.black));
                mPresident = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.dateStatus.getText().toString().trim().isEmpty()) {
                mBinding.dateStatusRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.dateStatusRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.dateStatusRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.dateStatusRequiredText.setTextColor(getResources().getColor(R.color.black));
                mPresident = mBinding.guarantorOne.getText().toString().trim();

                DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
                mDateStatusString = mBinding.dateApplied.getText().toString();
                String strDate = (mDatePicker.getDay() + "-" + mDatePicker.getMonth() + "-" + mDatePicker.getYear());
                try {
                    Date date = dateFormat.parse(strDate);
                    mCal3.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDateStatus = mCal3.getTimeInMillis();
            }

            if (mBinding.statusUpdate.getText().toString().trim().isEmpty()) {
                mBinding.statusUpdateRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.statusUpdateRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.statusUpdateRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.statusUpdateRequiredText.setTextColor(getResources().getColor(R.color.black));
                mStatusUpdate = mBinding.guarantorOne.getText().toString().trim();
            }

            clickOfficialSubmit();

        });
    }

    private void approveLoanByPresident() {
        mBinding.officialSubmit.setOnClickListener(v -> {

        });
    }

    private void clickOfficialSubmit() {

        if (mOfficialOne.isEmpty() && mOfficialTwo.isEmpty() && mOfficialThree.isEmpty() && mPresident.isEmpty()
                && mDateStatusString.isEmpty() && !mStatusUpdate.isEmpty()) {
            Boolean disableEdit = mBinding.disableEdit.isChecked();
            submitOfficialSection(disableEdit);
        } else {
            Toast.makeText(this, "Required fields must be filled", Toast.LENGTH_SHORT).show();
        }

    }

    private void submitOfficialSection(Boolean disableEdit) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_GUARANTOR_ONE_REQUEST_CODE && resultCode == RESULT_OK) {
            mSelectedGuarantorOne = data.getStringExtra(SELECT_GUARANTOR_ONE_STRING_EXTRA);

            if (mSelectedGuarantorOne.equals(mSelectedGuarantorTwo)) {
                mBinding.guarantorOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                Toast.makeText(this, "You cannot use the same guarantor", Toast.LENGTH_SHORT).show();
                mBinding.guarantorOneRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.guarantorOne.setHint("Select from list");
                mBinding.guarantorOne.setText("");
            } else if (mBinding.name.getText().equals(mSelectedGuarantorOne)) {
                Toast.makeText(this, "You cannot serve as your own guarantor", Toast.LENGTH_SHORT).show();
                mBinding.guarantorOne.setHint("Select from list");
                mBinding.guarantorOne.setText("");
            } else {
                mBinding.guarantorOne.setText(mSelectedGuarantorOne);
                mBinding.guarantorOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorOneRequiredText.setTextColor(getResources().getColor(R.color.black));
            }

        } else if (requestCode == SELECT_GUARANTOR_TWO_REQUEST_CODE && resultCode == RESULT_OK) {
            mSelectedGuarantorTwo = data.getStringExtra(SELECT_GUARANTOR_TWO_STRING_EXTRA);

            if (mSelectedGuarantorTwo.equals(mSelectedGuarantorOne)) {
                mBinding.guarantorTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.guarantorTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
                Toast.makeText(this, "You cannot use the same guarantor", Toast.LENGTH_SHORT).show();
                mBinding.guarantorTwo.setHint("Select from list");
                mBinding.guarantorTwo.setText("");
            } else if (mBinding.name.getText().equals(mSelectedGuarantorTwo)) {
                Toast.makeText(this, "You cannot serve as your own guarantor", Toast.LENGTH_SHORT).show();
                mBinding.guarantorTwo.setHint("Select from list");
                mBinding.guarantorTwo.setText("");
            } else {
                mBinding.guarantorTwo.setText(mSelectedGuarantorTwo);
                mBinding.guarantorTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(HAS_SUBMITTED_SAVED_INSTANCE_STATE, hasSubmitted);
        outState.putString(NAME_SAVED_INSTANCE_STATE, mBinding.name.getText().toString());
        outState.putString(GUARANTOR_ONE_SAVED_INSTANCE_STATE, mBinding.guarantorOne.getText().toString());
        outState.putString(GUARANTOR_TWO_SAVED_INSTANCE_STATE, mBinding.guarantorTwo.getText().toString());
        outState.putString(STATUS_SAVED_INSTANCE_STATE, mBinding.status.getText().toString());
        outState.putString(LOAN_ID_SAVED_INSTANCE_STATE, mBinding.loanId.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mBinding.name.setText(NAME_SAVED_INSTANCE_STATE);
        mBinding.guarantorOne.setText(GUARANTOR_ONE_SAVED_INSTANCE_STATE);
        mBinding.guarantorTwo.setText(GUARANTOR_TWO_SAVED_INSTANCE_STATE);
        mBinding.status.setText(STATUS_SAVED_INSTANCE_STATE);
        mBinding.loanId.setText(LOAN_ID_SAVED_INSTANCE_STATE);
        mBinding.status.setVisibility(View.VISIBLE);
        mBinding.loanIdHeader.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.statusHeader.setVisibility(View.GONE);
        mBinding.loanIdHeader.setVisibility(View.GONE);
        mBinding.amount.setEnabled(false);
        mBinding.reason.setEnabled(false);
        mBinding.guarantorOneListUsers.setEnabled(false);
        mBinding.guarantorTwoListUsers.setEnabled(false);
        mBinding.dateApplied.setEnabled(false);
        mBinding.repayment.setEnabled(false);
        mBinding.userSubmit.setEnabled(false);

        mBinding.guarantorOneConfirmation.setEnabled(false);
        mBinding.guarantorTwoConfirmation.setEnabled(false);
        mBinding.guarantorSubmit.setEnabled(false);

        mBinding.officialOne.setEnabled(false);
        mBinding.officialTwo.setEnabled(false);
        mBinding.officialThree.setEnabled(false);
        mBinding.president.setEnabled(false);
        mBinding.dateStatus.setEnabled(false);
        mBinding.statusUpdate.setEnabled(false);
        mBinding.disableEdit.setEnabled(false);
        mBinding.officialSubmit.setEnabled(false);

        mBinding.officialSection.setVisibility(View.GONE);

        if (hasSubmitted) {
            mBinding.status.setVisibility(View.VISIBLE);
            mBinding.loanIdHeader.setVisibility(View.VISIBLE);

            if (mBinding.name.getText().toString().
                    equals(mPreferences.getString(FIRST_NAME_KEY, "") +
                            " " + mPreferences.getString(LAST_NAME_KEY, ""))) {
                mBinding.officialSection.setVisibility(View.VISIBLE);

                mBinding.copyLoanId.setOnClickListener(v -> {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Loan Id", "" + mBinding.loanId.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, clip.getDescription().getLabel() + " copied", Toast.LENGTH_SHORT).show();
                });

            }
        }

        incomingUser();

    }

    private void incomingUser() {
        Intent intent = getIntent();

        if (!hasSubmitted) {
            if (intent.hasExtra(LOGIN_ROLE_USER_KEY)) {
                if (intent.hasExtra(GUARANTEE_LOAN_KEY)) {
                    setValues();
                    mFullName = mPreferences.getString(FIRST_NAME_KEY, "") + " " + mPreferences.getString(LAST_NAME_KEY, "");
                    mFormGuarantorOneFullName = mBinding.guarantorOne.getText().toString().trim();
                    mFormGuarantorTwoFullName = mBinding.guarantorTwo.getText().toString().trim();

                    if (mFullName.equals(mFormGuarantorOneFullName) || mFullName.equals(mFormGuarantorTwoFullName)) {
                        if (mFullName.equals(mFormGuarantorOneFullName) && mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty()) {
                            mBinding.guarantorOneConfirmation.setEnabled(true);
                            mBinding.guarantorTwoConfirmationHeader.setVisibility(View.GONE);
                            mBinding.guarantorSubmit.setEnabled(true);
                        } else if (mFullName.equals(mFormGuarantorTwoFullName) && mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty()) {
                            mBinding.guarantorTwoConfirmation.setEnabled(true);
                            mBinding.guarantorOneConfirmationHeader.setVisibility(View.GONE);
                            mBinding.guarantorSubmit.setEnabled(true);
                        }
                        mBinding.statusHeader.setVisibility(View.VISIBLE);
                        mBinding.loanIdHeader.setVisibility(View.VISIBLE);
                        guaranteeLoan();
                    } else {
                        Toast.makeText(this, "You are not entitled to guarantee this loan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mBinding.amount.setEnabled(true);
                    mBinding.guarantorOne.setEnabled(true);
                    mBinding.guarantorTwo.setEnabled(true);
                    mBinding.guarantorOneListUsers.setEnabled(true);
                    mBinding.guarantorTwoListUsers.setEnabled(true);
                    mBinding.reason.setEnabled(true);
                    mBinding.dateApplied.setEnabled(true);
                    mBinding.repayment.setEnabled(true);
                    mBinding.userSubmit.setEnabled(true);
                    requestLoan();
                }
            } else {
                setValues();
                if (intent.hasExtra(APPROVE_LOAN_KEY) && intent.hasExtra(PRESIDENT_LOAN_KEY)) {
                    mBinding.officialOne.setEnabled(true);
                    mBinding.officialTwo.setEnabled(true);
                    mBinding.officialThree.setEnabled(true);
                    mBinding.president.setEnabled(true);
                    mBinding.dateStatus.setEnabled(true);
                    mBinding.statusUpdate.setEnabled(true);
                    mBinding.disableEdit.setEnabled(true);
                    mBinding.officialSubmit.setEnabled(true);
                    mBinding.statusHeader.setVisibility(View.VISIBLE);
                    mBinding.loanIdHeader.setVisibility(View.VISIBLE);
                    mBinding.officialSection.setVisibility(View.VISIBLE);
                    approveLoanByPresident();
                } else if (intent.hasExtra(APPROVE_LOAN_KEY) && intent.hasExtra(LOAN_CHECKER_ROLE_USER_KEY)) {
                    mBinding.officialOne.setEnabled(true);
                    mBinding.officialTwo.setEnabled(true);
                    mBinding.officialThree.setEnabled(true);
                    mBinding.officialSubmit.setEnabled(true);
                    mBinding.statusHeader.setVisibility(View.VISIBLE);
                    mBinding.loanIdHeader.setVisibility(View.VISIBLE);
                    mBinding.officialSection.setVisibility(View.VISIBLE);
                    approveLoan();
                } else if (intent.hasExtra(APPROVE_LOAN_KEY) && intent.hasExtra(EXCO_ROLE_USER_KEY)) {
                    mBinding.statusHeader.setVisibility(View.VISIBLE);
                    mBinding.loanIdHeader.setVisibility(View.VISIBLE);
                    mBinding.officialSection.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    private void setValues() {
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");

        Intent intent = getIntent();
        if (intent.hasExtra(GUARANTEE_LOAN_PARCELABLE_EXTRA_KEY)) {
            LoanRest loanRest = intent.getParcelableExtra(GUARANTEE_LOAN_PARCELABLE_EXTRA_KEY);

            mBinding.status.setText(loanRest.getStatus());
            mBinding.loanId.setText(loanRest.getLoanId());
            mBinding.name.setText(loanRest.getName());
            mBinding.amount.setText(loanRest.getAmount());
            mBinding.guarantorOne.setText(loanRest.getGuarantorOne());
            mBinding.guarantorTwo.setText(loanRest.getGuarantorTwo());
            mBinding.reason.setText(loanRest.getReason());
            mBinding.dateApplied.setText("" + dateFormat.format(new Date(loanRest.getRequestDate())));
            mBinding.repayment.setText("" + dateFormat.format(new Date(loanRest.getRepaymentDate())));
            mBinding.guarantorOneConfirmation.setText(loanRest.getGuarantorOneConfirmation());
            mBinding.guarantorTwoConfirmation.setText(loanRest.getGuarantorTwoConfirmation());

            mUserId = intent.getStringExtra(USER_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE);
            mLoanId = intent.getStringExtra(LOAN_ID_SENT_BY_GUARANTOR_STRING_EXTRA_ONE);
        } else if (intent.hasExtra(APPROVE_LOAN_PARCELABLE_EXTRA_KEY)) {
            LoanRest loanRest = intent.getParcelableExtra(APPROVE_LOAN_PARCELABLE_EXTRA_KEY);

            mBinding.status.setText(loanRest.getStatus());
            mBinding.loanId.setText(loanRest.getLoanId());
            mBinding.name.setText(loanRest.getName());
            mBinding.amount.setText(loanRest.getAmount());
            mBinding.guarantorOne.setText(loanRest.getGuarantorOne());
            mBinding.guarantorTwo.setText(loanRest.getGuarantorTwo());
            mBinding.reason.setText(loanRest.getReason());
            mBinding.dateApplied.setText("" + dateFormat.format(new Date(loanRest.getRequestDate())));
            mBinding.repayment.setText("" + dateFormat.format(new Date(loanRest.getRepaymentDate())));
            mBinding.guarantorOneConfirmation.setText(loanRest.getGuarantorOneConfirmation());
            mBinding.guarantorTwoConfirmation.setText(loanRest.getGuarantorTwoConfirmation());
            mBinding.officialOne.setText(loanRest.getOfficialOne());
            mBinding.officialTwo.setText(loanRest.getOfficialTwo());
            mBinding.officialThree.setText(loanRest.getOfficialThree());
            mBinding.president.setText(loanRest.getPresident());
            mBinding.dateStatus.setText("" + dateFormat.format(new Date(loanRest.getStatusDate())));
            mBinding.statusUpdate.setText(loanRest.getStatus());
            mBinding.disableEdit.setEnabled(loanRest.getEditable());

            mUserId = intent.getStringExtra(USER_ID_SENT_BY_ADMIN_STRING_EXTRA_ONE);
            mLoanId = intent.getStringExtra(LOAN_ID_SENT_BY_ADMIN_STRING_EXTRA_ONE);
        }
    }

    private void hideOpeningKeyBoard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(v -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
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
package com.example.successcontribution.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityLoanRequestFormBinding;
import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.ui.activity.ListUsersActivity;
import com.example.successcontribution.shared.DatePicker;
import com.example.successcontribution.ui.viewmodel.LoanRequestViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.successcontribution.shared.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.FIRST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.shared.Constant.GUARANTOR_ONE_FROM_INTENT;
import static com.example.successcontribution.shared.Constant.GUARANTOR_ONE_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.GUARANTOR_TWO_FROM_INTENT;
import static com.example.successcontribution.shared.Constant.GUARANTOR_TWO_REQUEST_CODE;
import static com.example.successcontribution.shared.Constant.LAST_NAME_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY;
import static com.example.successcontribution.shared.Constant.LOGIN_ROLE_USER_KEY;
import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.REQUEST_LOAN_KEY;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoanRequestFormBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

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
            Intent intent = new Intent(this, ListUsersActivity.class);
            startActivityForResult(intent, GUARANTOR_ONE_REQUEST_CODE);
        });

        mBinding.guarantorTwoListUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListUsersActivity.class);
            startActivityForResult(intent, GUARANTOR_TWO_REQUEST_CODE);
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
                mGuarantorTwo = mBinding.guarantorOne.getText().toString().trim();
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

                // DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
                //Date date = new Date (milliseconds);
                //String strFormatDate = dateFormat.format(date);

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
        mBinding.status.setText(loanRest.getStatus());

        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Loan request sent successfully!", Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    private void errorConnection(String errorMessage, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_LONG).show();
        getViewModelStore().clear();
    }

    private void guaranteeLoan() {

        mBinding.guarantorSubmit.setOnClickListener(v -> {

            if (!mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty() &&
                    !mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty()) {
                mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorOneConfirmation = mBinding.guarantorOne.getText().toString().trim();
                mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorTwoConfirmation = mBinding.guarantorOne.getText().toString().trim();
            }

            if (!mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty() &&
                    mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty()) {
                mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorOneConfirmation = mBinding.guarantorOne.getText().toString().trim();
            }

            if (!mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty() &&
                    mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty()) {
                mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorTwoConfirmation = mBinding.guarantorOne.getText().toString().trim();
            }

            if (mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty() &&
                    mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty()) {
                mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
                mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
                mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
            } else {
                mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorOneConfirmation = mBinding.guarantorOne.getText().toString().trim();
                mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
                mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
                mGuarantorTwoConfirmation = mBinding.guarantorOne.getText().toString().trim();
            }

            clickSubmitGuarantorSection();
        });
    }

    private void clickSubmitGuarantorSection() {
        if (!mGuarantorOneConfirmation.isEmpty() && !mGuarantorTwoConfirmation.isEmpty()) {
            submitGuarantorSection();
        }
        if (!mGuarantorOneConfirmation.isEmpty() && mGuarantorTwoConfirmation.isEmpty()) {
            mGuarantorTwoConfirmation = " ";
            submitGuarantorSection();
        } else if (mGuarantorOneConfirmation.isEmpty() && !mGuarantorTwoConfirmation.isEmpty()) {
            mGuarantorOneConfirmation = " ";
            submitGuarantorSection();
        } else {
            Toast.makeText(this, "At least one guarantor must fill", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitGuarantorSection() {

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

        if (requestCode == GUARANTOR_ONE_REQUEST_CODE && resultCode == RESULT_OK) {
            String guarantorOne = data.getStringExtra(GUARANTOR_ONE_FROM_INTENT);
            mBinding.guarantorOne.setText(guarantorOne);
        } else if (requestCode == GUARANTOR_TWO_REQUEST_CODE && resultCode == RESULT_OK) {
            String guarantorTwo = data.getStringExtra(GUARANTOR_TWO_FROM_INTENT);
            mBinding.guarantorTwo.setText(guarantorTwo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.statusHeader.setVisibility(View.GONE);
        mBinding.loanIdHeader.setVisibility(View.GONE);
        mBinding.amount.setEnabled(false);
        mBinding.guarantorOne.setEnabled(false);
        mBinding.guarantorTwo.setEnabled(false);
        mBinding.reason.setEnabled(false);
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

        incomingUser();
    }

    private void incomingUser() {
        Intent intent = getIntent();

        if (intent.hasExtra(LOGIN_ROLE_USER_KEY)) {
            if (intent.hasExtra(GUARANTEE_LOAN_KEY)) {
                mBinding.guarantorOneConfirmation.setEnabled(true);
                mBinding.guarantorTwoConfirmation.setEnabled(true);
                mBinding.guarantorSubmit.setEnabled(true);
                mBinding.statusHeader.setVisibility(View.VISIBLE);
                mBinding.loanIdHeader.setVisibility(View.VISIBLE);
                guaranteeLoan();

            } else {
                mBinding.amount.setEnabled(true);
                mBinding.guarantorOne.setEnabled(true);
                mBinding.guarantorTwo.setEnabled(true);
                mBinding.reason.setEnabled(true);
                mBinding.dateApplied.setEnabled(true);
                mBinding.repayment.setEnabled(true);
                mBinding.userSubmit.setEnabled(true);
                requestLoan();
            }
        } else {
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
            approveLoan();
        }

    }
}
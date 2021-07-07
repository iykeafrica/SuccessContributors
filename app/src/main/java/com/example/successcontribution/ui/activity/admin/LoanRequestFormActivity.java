package com.example.successcontribution.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivityLoanRequestFormBinding;
import com.example.successcontribution.ui.activity.ListUsersActivity;
import com.example.successcontribution.utils.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.successcontribution.utils.Constant.APPROVE_LOAN_KEY;
import static com.example.successcontribution.utils.Constant.GUARANTEE_LOAN_KEY;
import static com.example.successcontribution.utils.Constant.GUARANTOR_ONE_FROM_INTENT;
import static com.example.successcontribution.utils.Constant.GUARANTOR_ONE_REQUEST_CODE;
import static com.example.successcontribution.utils.Constant.GUARANTOR_TWO_FROM_INTENT;
import static com.example.successcontribution.utils.Constant.GUARANTOR_TWO_REQUEST_CODE;
import static com.example.successcontribution.utils.Constant.REQUEST_LOAN_KEY;

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
    private Calendar mCal;
    private long mRepayment;
    private String mDateRepayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityLoanRequestFormBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mCal = Calendar.getInstance();
        setDateApplied();
        setDateRepayment();
        setDateStatus();

        Intent intent = getIntent();

        if (intent.hasExtra(REQUEST_LOAN_KEY)) {
            requestLoan();
        } else if (intent.hasExtra(GUARANTEE_LOAN_KEY)) {
            guaranteeLoan();
        } else if (intent.hasExtra(APPROVE_LOAN_KEY)) {
            approveLoan();
        }

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
        mDatePicker = new DatePicker(this, mCal, mBinding.dateApplied);
        mDatePicker.setDateText();
    }

    private void setDateRepayment() {
        mDatePicker = new DatePicker(this, mCal, mBinding.repayment);
        mDatePicker.setDateText();
    }

    private void setDateStatus() {
        mDatePicker = new DatePicker(this, mCal, mBinding.dateStatus);
        mDatePicker.setDateText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.status.setVisibility(View.GONE);
        mBinding.loanIdHeader.setVisibility(View.GONE);
    }

    private void requestLoan() {
        mBinding.guarantorSection.setEnabled(false);
        mBinding.officialSection.setEnabled(false);

            mName = mBinding.name.getText().toString().trim();

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
                String strDate = dateFormat.format(mDateAppliedString);
                try {
                    Date date = dateFormat.parse(strDate);
                    mCal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDateApplied = mCal.getTimeInMillis();

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
                String strDate = dateFormat.format(mDateRepayment);
                try {
                    Date date = dateFormat.parse(strDate);
                    mCal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mRepayment = mCal.getTimeInMillis();
            }

            clickSubmitLoanRequest();
    }

    private void clickSubmitLoanRequest() {

        mBinding.userSubmit.setOnClickListener(v -> {

            if (!mName.isEmpty() && !mAmount.isEmpty() && !mGuarantorOne.isEmpty() && !mGuarantorTwo.isEmpty()
                    && !mReason.isEmpty() && !mDateAppliedString.isEmpty() && !mDateRepayment.isEmpty()) {
                submitLoanRequest();
            } else {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void submitLoanRequest() {
        //To be implemented
    }

    private void guaranteeLoan() {
        mBinding.userSection.setEnabled(false);
        mBinding.officialSection.setEnabled(false);

        //get values from server before enabling them
        mBinding.status.setVisibility(View.VISIBLE);
        mBinding.loanIdHeader.setVisibility(View.VISIBLE);

        if (mBinding.guarantorOneConfirmation.getText().toString().trim().isEmpty()) {
            mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
            mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBinding.guarantorOneConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
            mBinding.guarantorOneConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
            mGuarantorOneConfirmation = mBinding.guarantorOne.getText().toString().trim();
        }

        if (mBinding.guarantorTwoConfirmation.getText().toString().trim().isEmpty()) {
            mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
            mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBinding.guarantorTwoConfirmationRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
            mBinding.guarantorTwoConfirmationRequiredText.setTextColor(getResources().getColor(R.color.black));
            mGuarantorTwoConfirmation = mBinding.guarantorOne.getText().toString().trim();
        }

        clickSubmitGuarantorSection();

    }

    private void clickSubmitGuarantorSection() {
        mBinding.guarantorSubmit.setOnClickListener(v -> {
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
        });
    }

    private void submitGuarantorSection() {

    }


    private void approveLoan() {
        mBinding.userSection.setEnabled(false);
        mBinding.guarantorSection.setEnabled(false);

        //get values from server before enabling them
        mBinding.status.setVisibility(View.VISIBLE);
        mBinding.loanIdHeader.setVisibility(View.VISIBLE);

        mOfficialOne = mBinding.guarantorOne.getText().toString().trim();
        mOfficialTwo = mBinding.guarantorOne.getText().toString().trim();
        mOfficialThree = mBinding.guarantorOne.getText().toString().trim();
        mPresident = mBinding.guarantorOne.getText().toString().trim();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);
        mBinding.dateStatus.setText(strDate);
        mDateStatus = System.currentTimeMillis();

//
//        if (mBinding.officialOne.getText().toString().trim().isEmpty()) {
//            mBinding.officialOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
//            mBinding.officialOneRequiredText.setTextColor(getResources().getColor(R.color.white));
//        } else {
//            mBinding.officialOneRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
//            mBinding.officialOneRequiredText.setTextColor(getResources().getColor(R.color.black));
//            mOfficialOne = mBinding.guarantorOne.getText().toString().trim();
//        }
//
//        if (mBinding.officialTwo.getText().toString().trim().isEmpty()) {
//            mBinding.officialTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
//            mBinding.officialTwoRequiredText.setTextColor(getResources().getColor(R.color.white));
//        } else {
//            mBinding.officialTwoRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
//            mBinding.officialTwoRequiredText.setTextColor(getResources().getColor(R.color.black));
//            mOfficialTwo = mBinding.guarantorOne.getText().toString().trim();
//        }
//
//        if (mBinding.officialThree.getText().toString().trim().isEmpty()) {
//            mBinding.officialThreeRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
//            mBinding.officialThreeRequiredText.setTextColor(getResources().getColor(R.color.white));
//        } else {
//            mBinding.officialThreeRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
//            mBinding.officialThreeRequiredText.setTextColor(getResources().getColor(R.color.black));
//            mOfficialThree = mBinding.guarantorOne.getText().toString().trim();
//        }
//
//        if (mBinding.president.getText().toString().trim().isEmpty()) {
//            mBinding.presidentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required_red));
//            mBinding.presidentRequiredText.setTextColor(getResources().getColor(R.color.white));
//        } else {
//            mBinding.presidentRequired.setBackground(ContextCompat.getDrawable(this, R.drawable.required));
//            mBinding.presidentRequiredText.setTextColor(getResources().getColor(R.color.black));
//            mPresident = mBinding.guarantorOne.getText().toString().trim();
//        }

        clickOfficialSubmit();
    }

    private void clickOfficialSubmit() {
        mBinding.officialSubmit.setOnClickListener(v -> {

            if (!mOfficialOne.isEmpty() && !mOfficialTwo.isEmpty() && !mOfficialThree.isEmpty() && !mPresident.isEmpty()) {
                submitOfficialSection();
            }

            if (!mOfficialOne.isEmpty() && mOfficialTwo.isEmpty() && mOfficialThree.isEmpty() && mPresident.isEmpty()) {
                mOfficialTwo = " ";
                mOfficialThree = " ";
                mPresident = " ";
                submitOfficialSection();
            }

            if (mOfficialOne.isEmpty() && !mOfficialTwo.isEmpty() && mOfficialThree.isEmpty() && mPresident.isEmpty()) {
                mOfficialOne = " ";
                mOfficialThree = " ";
                mPresident = " ";
                submitOfficialSection();
            }

            if (mOfficialOne.isEmpty() && mOfficialTwo.isEmpty() && !mOfficialThree.isEmpty() && mPresident.isEmpty()) {
                mOfficialOne = " ";
                mOfficialTwo = " ";
                mPresident = " ";
                submitOfficialSection();
            }


        });
    }

    private void submitOfficialSection() {

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
}
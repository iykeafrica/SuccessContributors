package com.example.successcontribution.ui.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import static com.example.successcontribution.shared.Constant.GUARANTOR_ONE_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.GUARANTOR_TWO_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.HAS_SUBMITTED_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.LOAN_ID_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.NAME_SAVED_INSTANCE_STATE;
import static com.example.successcontribution.shared.Constant.STATUS_SAVED_INSTANCE_STATE;

public class LoanRequestHasSubmittedViewModel extends ViewModel {


    public boolean mIsNewlyCreated;
    public String mName;
    public String mGuarantorOne;
    public String mGuarantorTwo;
    public String mOfficialOne;
    public String mOfficialTwo;
    public String mOfficialThree;
    public String mPresident;
    public String mAmount;
    public String mReason;
    public long mDateApplied;
    public String mDateAppliedString;
    public String mGuarantorOneConfirmation;
    public String mGuarantorTwoConfirmation;
    public long mDateStatus;
    public long mRepayment;
    public String mDateRepayment;
    public String mStatus;
    public String mDateStatusString;
    public String mStatusUpdate;
    public String mSelectedGuarantorOne;
    public String mSelectedGuarantorTwo;
    public String mFullName;
    public String mFormGuarantorOneFullName;
    public String mFormGuarantorTwoFullName;
    public String mUserId;
    public String mLoanId;
    public boolean hasSubmitted;


    public void saveState(Bundle outState) {
        outState.putBoolean(HAS_SUBMITTED_SAVED_INSTANCE_STATE, hasSubmitted);
        outState.putString(NAME_SAVED_INSTANCE_STATE, mName);
        outState.putString(GUARANTOR_ONE_SAVED_INSTANCE_STATE, mGuarantorOne);
        outState.putString(GUARANTOR_TWO_SAVED_INSTANCE_STATE, mGuarantorTwo);
        outState.putString(STATUS_SAVED_INSTANCE_STATE, mStatus);
        outState.putString(LOAN_ID_SAVED_INSTANCE_STATE, mLoanId);
    }

    public void restoreState(Bundle savedInstanceState) {
        hasSubmitted = savedInstanceState.getBoolean(HAS_SUBMITTED_SAVED_INSTANCE_STATE);
        mName = savedInstanceState.getString(NAME_SAVED_INSTANCE_STATE);
        mGuarantorOne = savedInstanceState.getString(GUARANTOR_ONE_SAVED_INSTANCE_STATE);
        mGuarantorTwo = savedInstanceState.getString(GUARANTOR_TWO_SAVED_INSTANCE_STATE);
        mStatus = savedInstanceState.getString(STATUS_SAVED_INSTANCE_STATE);
        mLoanId = savedInstanceState.getString(LOAN_ID_SAVED_INSTANCE_STATE);
    }
}

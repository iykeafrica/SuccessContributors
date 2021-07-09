package com.example.successcontribution.shared;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePicker {
    private static final String TAG = DatePicker.class.getSimpleName();
    EditText mEditTextDate;
    Context mContext;
    private final Calendar mCal;
    private String mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener mDate;

    public DatePicker(Context context, Calendar calendar, EditText editTextDate) {
        mContext = context;
        mCal = calendar;
        mEditTextDate = editTextDate;
    }

    private void dataPickerDialog(){
        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

                mCal.set(Calendar.YEAR, year);
                mCal.set(Calendar.MONTH, month);
                mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateLabel();

                setYear(String.valueOf(year));
                setMonth(String.valueOf(month + 1));
                setDay(String.valueOf(dayOfMonth));
            }
        };
    }

    public void setDateText(){
        mEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPickerDialog();
                new DatePickerDialog(mContext, mDate, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH)).show();

                Log.d(TAG, "onClick: " + getDay() + " " + getMonth() + " " + getYear());
                hideKeyboard((Activity) mContext);
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "dd-M-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        mEditTextDate.setText(sdf.format(mCal.getTime()));
        Log.d(TAG, "updateDateLabel: " + mCal.getTime());
    }

    private void setYear(String year) {
        mYear = year;
    }

    private void setMonth(String month) {
        mMonth = month;
    }

    private void setDay(String day) {
        mDay = day;
    }

    public String getYear() {
        return mYear;
    }

    public String getMonth() {
        return mMonth;
    }

    public String getDay() {
        return mDay;
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

package com.example.successcontribution.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.successcontribution.R;
import com.example.successcontribution.databinding.ActivitySearchUserLoanBinding;

public class SearchUserLoanActivity extends AppCompatActivity {

    private ActivitySearchUserLoanBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySearchUserLoanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        approveLoan();
    }

    private void approveLoan() {

        mBinding.btnSearch.setOnClickListener(v -> {
            if (!mBinding.etUserId.getText().toString().isEmpty() && !mBinding.etLoanId.getText().toString().trim().isEmpty()){
                search();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search() {
        successful();

    }

    private void successful() {
        Intent intent = new Intent(this, ApproveLoanActivity.class);
        startActivity(intent);
    }
}
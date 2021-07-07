package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDepositedFundRequestModel {
    @SerializedName("depositedFund")
    @Expose
    private String depositedFund;

    public String getDepositedFund() {
        return depositedFund;
    }

    public void setDepositedFund(String depositedFund) {
        this.depositedFund = depositedFund;
    }
}

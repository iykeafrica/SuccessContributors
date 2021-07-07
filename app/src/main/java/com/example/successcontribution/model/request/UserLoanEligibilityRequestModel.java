package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoanEligibilityRequestModel {

    @SerializedName("loanEligibility")
    @Expose
    private Boolean loanEligibility;

    public Boolean getLoanEligibility() {
        return loanEligibility;
    }

    public void setLoanEligibility(Boolean loanEligibility) {
        this.loanEligibility = loanEligibility;
    }
}

package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuarantorLoanRequestModel {

    @SerializedName("guarantorOneConfirmation")
    @Expose
    private String guarantorOneConfirmation;

    @SerializedName("guarantorTwoConfirmation")
    @Expose
    private String guarantorTwoConfirmation;

    public String getGuarantorOneConfirmation() {
        return guarantorOneConfirmation;
    }

    public void setGuarantorOneConfirmation(String guarantorOneConfirmation) {
        this.guarantorOneConfirmation = guarantorOneConfirmation;
    }

    public String getGuarantorTwoConfirmation() {
        return guarantorTwoConfirmation;
    }

    public void setGuarantorTwoConfirmation(String guarantorTwoConfirmation) {
        this.guarantorTwoConfirmation = guarantorTwoConfirmation;
    }
}

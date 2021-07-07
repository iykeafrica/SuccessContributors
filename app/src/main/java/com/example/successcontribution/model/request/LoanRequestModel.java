package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanRequestModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("guarantorOne")
    @Expose
    private String guarantorOne;

    @SerializedName("guarantorTwo")
    @Expose
    private String guarantorTwo;

    @SerializedName("requestDate")
    @Expose
    private long requestDate;

    @SerializedName("repaymentDate")
    @Expose
    private long repaymentDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGuarantorOne() {
        return guarantorOne;
    }

    public void setGuarantorOne(String guarantorOne) {
        this.guarantorOne = guarantorOne;
    }

    public String getGuarantorTwo() {
        return guarantorTwo;
    }

    public void setGuarantorTwo(String guarantorTwo) {
        this.guarantorTwo = guarantorTwo;
    }

    public long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(long requestDate) {
        this.requestDate = requestDate;
    }

    public long getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(long repaymentDate) {
        this.repaymentDate = repaymentDate;
    }
}

package com.example.successcontribution.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanRest {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("loanId")
    @Expose
    private String loanId;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("requestDate")
    @Expose
    private long requestDate;

    @SerializedName("repaymentDate")
    @Expose
    private long repaymentDate;

    @SerializedName("guarantorOne")
    @Expose
    private String guarantorOne;

    @SerializedName("guarantorTwo")
    @Expose
    private String guarantorTwo;

    @SerializedName("guarantorOneConfirmation")
    @Expose
    private String guarantorOneConfirmation;

    @SerializedName("guarantorTwoConfirmation")
    @Expose
    private String guarantorTwoConfirmation;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("officialOne")
    @Expose
    private String officialOne;

    @SerializedName("officialTwo")
    @Expose
    private String officialTwo;

    @SerializedName("officialThree")
    @Expose
    private String officialThree;

    @SerializedName("president")
    @Expose
    private String president;

    @SerializedName("statusDate")
    @Expose
    private long statusDate;

    @SerializedName("editable")
    @Expose
    private Boolean isEditable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfficialOne() {
        return officialOne;
    }

    public void setOfficialOne(String officialOne) {
        this.officialOne = officialOne;
    }

    public String getOfficialTwo() {
        return officialTwo;
    }

    public void setOfficialTwo(String officialTwo) {
        this.officialTwo = officialTwo;
    }

    public String getOfficialThree() {
        return officialThree;
    }

    public void setOfficialThree(String officialThree) {
        this.officialThree = officialThree;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public long getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(long statusDate) {
        this.statusDate = statusDate;
    }

    public Boolean getEditable() {
        return isEditable;
    }

    public void setEditable(Boolean editable) {
        isEditable = editable;
    }
}

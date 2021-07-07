package com.example.successcontribution.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRest {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("depositedFund")
    @Expose
    private String depositedFund;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("sapNo")
    @Expose
    private String sapNo;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;

    @SerializedName("whatsappNo")
    @Expose
    private String whatsappNo;

    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;

    @SerializedName("loanEligibility")
    @Expose
    private Boolean loanEligibility;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("loans")
    @Expose
    private List<LoanRest> loans;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepositedFund() {
        return depositedFund;
    }

    public void setDepositedFund(String depositedFund) {
        this.depositedFund = depositedFund;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Boolean getLoanEligibility() {
        return loanEligibility;
    }

    public void setLoanEligibility(Boolean loanEligibility) {
        this.loanEligibility = loanEligibility;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<LoanRest> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanRest> loans) {
        this.loans = loans;
    }
}

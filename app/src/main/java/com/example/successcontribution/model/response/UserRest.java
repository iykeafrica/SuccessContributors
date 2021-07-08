package com.example.successcontribution.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

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

    @SerializedName("department")
    @Expose
    private String department;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRest userRest = (UserRest) o;
        return Objects.equals(userId, userRest.userId) &&
                Objects.equals(depositedFund, userRest.depositedFund) &&
                Objects.equals(firstName, userRest.firstName) &&
                Objects.equals(lastName, userRest.lastName) &&
                Objects.equals(email, userRest.email) &&
                Objects.equals(sapNo, userRest.sapNo) &&
                Objects.equals(address, userRest.address) &&
                Objects.equals(phoneNo, userRest.phoneNo) &&
                Objects.equals(whatsappNo, userRest.whatsappNo) &&
                Objects.equals(fcmToken, userRest.fcmToken) &&
                Objects.equals(loanEligibility, userRest.loanEligibility) &&
                Objects.equals(role, userRest.role) &&
                Objects.equals(loans, userRest.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, depositedFund, firstName, lastName, email, sapNo, address, phoneNo, whatsappNo, fcmToken, loanEligibility, role, loans);
    }
}

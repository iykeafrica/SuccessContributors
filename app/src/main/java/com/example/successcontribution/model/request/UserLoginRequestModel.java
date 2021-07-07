package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginRequestModel {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;

    @SerializedName("sapNo")
    @Expose
    private String sapNo;

    @SerializedName("password")
    @Expose
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

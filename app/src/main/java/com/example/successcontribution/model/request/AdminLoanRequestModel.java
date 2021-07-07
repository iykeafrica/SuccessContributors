package com.example.successcontribution.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminLoanRequestModel {

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

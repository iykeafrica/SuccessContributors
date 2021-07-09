package com.example.successcontribution.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LoanRest implements Parcelable {

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

    protected LoanRest(Parcel in) {
        name = in.readString();
        loanId = in.readString();
        amount = in.readString();
        reason = in.readString();
        requestDate = in.readLong();
        repaymentDate = in.readLong();
        guarantorOne = in.readString();
        guarantorTwo = in.readString();
        guarantorOneConfirmation = in.readString();
        guarantorTwoConfirmation = in.readString();
        status = in.readString();
        officialOne = in.readString();
        officialTwo = in.readString();
        officialThree = in.readString();
        president = in.readString();
        statusDate = in.readLong();
        byte tmpIsEditable = in.readByte();
        isEditable = tmpIsEditable == 0 ? null : tmpIsEditable == 1;
    }

    public static final Creator<LoanRest> CREATOR = new Creator<LoanRest>() {
        @Override
        public LoanRest createFromParcel(Parcel in) {
            return new LoanRest(in);
        }

        @Override
        public LoanRest[] newArray(int size) {
            return new LoanRest[size];
        }
    };

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRest loanRest = (LoanRest) o;
        return requestDate == loanRest.requestDate &&
                repaymentDate == loanRest.repaymentDate &&
                statusDate == loanRest.statusDate &&
                Objects.equals(name, loanRest.name) &&
                Objects.equals(loanId, loanRest.loanId) &&
                Objects.equals(amount, loanRest.amount) &&
                Objects.equals(reason, loanRest.reason) &&
                Objects.equals(guarantorOne, loanRest.guarantorOne) &&
                Objects.equals(guarantorTwo, loanRest.guarantorTwo) &&
                Objects.equals(guarantorOneConfirmation, loanRest.guarantorOneConfirmation) &&
                Objects.equals(guarantorTwoConfirmation, loanRest.guarantorTwoConfirmation) &&
                Objects.equals(status, loanRest.status) &&
                Objects.equals(officialOne, loanRest.officialOne) &&
                Objects.equals(officialTwo, loanRest.officialTwo) &&
                Objects.equals(officialThree, loanRest.officialThree) &&
                Objects.equals(president, loanRest.president) &&
                Objects.equals(isEditable, loanRest.isEditable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, loanId, amount, reason, requestDate, repaymentDate, guarantorOne, guarantorTwo, guarantorOneConfirmation, guarantorTwoConfirmation, status, officialOne, officialTwo, officialThree, president, statusDate, isEditable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(loanId);
        dest.writeString(amount);
        dest.writeString(reason);
        dest.writeLong(requestDate);
        dest.writeLong(repaymentDate);
        dest.writeString(guarantorOne);
        dest.writeString(guarantorTwo);
        dest.writeString(guarantorOneConfirmation);
        dest.writeString(guarantorTwoConfirmation);
        dest.writeString(status);
        dest.writeString(officialOne);
        dest.writeString(officialTwo);
        dest.writeString(officialThree);
        dest.writeString(president);
        dest.writeLong(statusDate);
        dest.writeByte((byte) (isEditable == null ? 0 : isEditable ? 1 : 2));
    }
}

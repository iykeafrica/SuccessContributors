package com.example.successcontribution.retrofit.api;

import com.example.successcontribution.model.request.AdminLoanRequestModel;
import com.example.successcontribution.model.request.GuarantorLoanRequestModel;
import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.request.PasswordResetModel;
import com.example.successcontribution.model.request.PasswordResetRequestModel;
import com.example.successcontribution.model.request.UserDepositedFundRequestModel;
import com.example.successcontribution.model.request.UserDetailsRequestModel;
import com.example.successcontribution.model.request.UserLoanEligibilityRequestModel;
import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.OperationStatusModel;
import com.example.successcontribution.model.response.UserRest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {
    //baseUrl -> https://success-contributions.herokuapp.com
    //loginUrl -> https://success-contributions.herokuapp.com/success-contributions/users/login
    //loanRequest -> https://success-contributions.herokuapp.com/success-contributions/users/userId/loan-applications
    //listUsers -> https://success-contributions.herokuapp.com/success-contributions/users
    //listUsers -> https://success-contributions.herokuapp.com/success-contributions/users/{userId}
    //listUserLoanApplications -> https://success-contributions.herokuapp.com/success-contributions/users/{userId}/loan-applications
    //listUserLoanApplications -> https://success-contributions.herokuapp.com/success-contributions/users/register
    //selectSpecificLoan -> https://success-contributions.herokuapp.com/success-contributions/users/{userId}/loan-applications/{loanId}
    //updateLoanByGuarantor -> https://success-contributions.herokuapp.com/success-contributions/{userId}/loan-applications/{loanId}/update-loan-application-guarantor
    //passwordResetRequest -> https://success-contributions.herokuapp.com/success-contributions/success-contributions/users/password-reset-request
    //updateUserLoanEligibility -> https://success-contributions.herokuapp.com/success-contributions/users/{userId}/update-loan-eligibility
    //updateUserSavings -> https://success-contributions.herokuapp.com/success-contributions/users/{userId}/update-deposited-fund



    @POST("/success-contributions/users/register")
    Call<UserRest> createUser(@Body UserDetailsRequestModel detailsRequestModel);

    @POST("/success-contributions/users/login")
    Call<ResponseBody> login(@Body UserLoginRequestModel loginRequestModel);

    @POST("/success-contributions/users/password-reset-request")
    Call<OperationStatusModel> passwordResetRequestRequest(@Body PasswordResetRequestModel passwordResetRequestModel);

    @POST("/success-contributions/users/{userId}/loan-applications")
    Call<LoanRest> requestLoan(@Path("userId") String userId, @Body LoanRequestModel loanRequestModel);

    @GET("/success-contributions/users")
    Call<List<UserRest>> getUsers(@Query("page") int page, @Query("limit") int limit);

    @GET("/success-contributions/users/{userId}")
    Call<UserRest> getUser(@Path("userId") String userId);

    @GET("/success-contributions/users/{userId}/loan-applications")
    Call<List<LoanRest>> getUserLoanApplications(@Path("userId") String userId);

    @GET("/success-contributions/users/{userId}/loan-applications/{loanId}")
    Call<LoanRest> getUserLoanApplication(@Path("userId") String userId, @Path("loanId") String loanId);

    @PUT("/success-contributions/users/{userId}/loan-applications/{loanId}/update-loan-application-guarantor")
    Call<LoanRest> updateUserLoanApplicationByGuarantor(@Path("userId") String userId, @Path("loanId") String loanId, @Body GuarantorLoanRequestModel guarantorLoanRequestModel);

    @PUT("/success-contributions/users/{userId}/loan-applications/{loanId}/update-loan-application-admin")
    Call<LoanRest> updateUserLoanApplicationByAdmin(@Path("userId") String userId, @Path("loanId") String loanId, @Body AdminLoanRequestModel adminLoanRequestModel);

    @PUT("/success-contributions/users/{userId}/update-loan-eligibility")
    Call<UserRest> updateUserLoanEligibility(@Path("userId") String userId, @Body UserLoanEligibilityRequestModel requestModel);

    @PUT("/success-contributions/users/{userId}/update-deposited-fund")
    Call<UserRest> updateUserSavings(@Path("userId") String userId, @Body UserDepositedFundRequestModel requestModel);
}

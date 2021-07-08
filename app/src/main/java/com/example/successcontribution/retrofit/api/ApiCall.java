package com.example.successcontribution.retrofit.api;

import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.request.UserDetailsRequestModel;
import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.UserRest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCall {
    //baseUrl -> https://success-contribution.herokuapp.com
    //loginUrl -> https://success-contribution.herokuapp.com/success-contribution/users/login
    //loanRequest -> https://success-contribution.herokuapp.com/success-contribution/users/userId/loan-applications
    //listUsers -> https://success-contribution.herokuapp.com/success-contribution/users
    //listUserLoanApplications -> https://success-contribution.herokuapp.com/success-contribution/users/{userId}/loan-applications
    //listUserLoanApplications -> https://success-contribution.herokuapp.com/success-contribution/users/register

    @POST("/success-contributions/users/login")
    Call<ResponseBody> login(@Body UserLoginRequestModel loginRequestModel);

    @POST("/success-contributions/users/{userId}/loan-applications")
    Call<LoanRest> requestLoan(@Path("userId") String userId, @Body LoanRequestModel loanRequestModel);

    @GET("/success-contributions/users")
    Call<List<UserRest>> getUsers();

    @GET("/success-contributions/users/{userId}/loan-applications")
    Call<List<LoanRest>> getUserLoanApplications(@Path("userId") String userId);

    @POST("/success-contributions/users/register")
    Call<UserRest> createUser(@Body UserDetailsRequestModel detailsRequestModel);

}

package com.example.successcontribution.retrofit.api;

import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.request.UserLoginRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCall {
    //baseUrl -> https://success-contribution.herokuapp.com
    //loginUrl -> https://success-contribution.herokuapp.com/success-contribution/users/login
    //loanRequest - > https://success-contribution.herokuapp.com/success-contribution/users/userId/loan-applications

    @POST("/success-contribution/users/login")
    Call<ResponseBody> login(@Body UserLoginRequestModel loginRequestModel);

    @POST("/success-contribution/users/{userId}/loan-applications")
    Call<LoanRest> requestLoan(@Path("userId") String userId, @Body LoanRequestModel loanRequestModel);


}

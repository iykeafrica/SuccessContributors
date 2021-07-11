package com.example.successcontribution.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.successcontribution.model.request.AdminLoanRequestModel;
import com.example.successcontribution.model.request.GuarantorLoanRequestModel;
import com.example.successcontribution.model.request.LoanRequestModel;
import com.example.successcontribution.model.response.LoanRest;
import com.example.successcontribution.model.response.UserRest;
import com.example.successcontribution.repository.response.ListLoanRestResponse;
import com.example.successcontribution.repository.response.LoanRestResponse;
import com.example.successcontribution.repository.response.ListUserRestResponse;
import com.example.successcontribution.repository.response.UserRestResponse;
import com.example.successcontribution.retrofit.client.AuthServiceClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.successcontribution.shared.Constant.MY_PREF;
import static com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY;

public class AuthRepository {

    private static final String TAG = AuthRepository.class.getSimpleName();
    private final AuthServiceClient mClient;
    private final SharedPreferences mSharedPreferences;

    public AuthRepository(Application application) {
        mClient = new AuthServiceClient(application);
        mSharedPreferences = application.getSharedPreferences(MY_PREF, 0);
    }

    public LoanRestResponse requestLoan(LoanRequestModel loanRequestModel) {
        MutableLiveData<LoanRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        String userId = mSharedPreferences.getString(USER_ID_DEFAULT_KEY, "");

        mClient.getApi().requestLoan(userId, loanRequestModel).enqueue(new Callback<LoanRest>() {
            @Override
            public void onResponse(Call<LoanRest> call, Response<LoanRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });


        return new LoanRestResponse(data, networkError);
    }

    public ListUserRestResponse getUsers(){
        MutableLiveData<List<UserRest>> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().getUsers().enqueue(new Callback<List<UserRest>>() {
            @Override
            public void onResponse(Call<List<UserRest>> call, Response<List<UserRest>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserRest>> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new ListUserRestResponse(data, networkError);
    }

    public ListLoanRestResponse getUserLoanApplications(){
        MutableLiveData<List<LoanRest>> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        String userId = mSharedPreferences.getString(USER_ID_DEFAULT_KEY, "");

        mClient.getApi().getUserLoanApplications(userId).enqueue(new Callback<List<LoanRest>>() {
            @Override
            public void onResponse(Call<List<LoanRest>> call, Response<List<LoanRest>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoanRest>> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new ListLoanRestResponse(data, networkError);
    }

    public LoanRestResponse getLoanApplication(String userId, String loanId) {
        MutableLiveData<LoanRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().getUserLoanApplication(userId, loanId).enqueue(new Callback<LoanRest>() {
            @Override
            public void onResponse(Call<LoanRest> call, Response<LoanRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new LoanRestResponse(data, networkError);
    }

    public LoanRestResponse updateUserLoanApplicationByGuarantor(String userId, String loanId, GuarantorLoanRequestModel guarantorLoanRequestModel) {
        MutableLiveData<LoanRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().updateUserLoanApplicationByGuarantor(userId, loanId, guarantorLoanRequestModel).enqueue(new Callback<LoanRest>() {
            @Override
            public void onResponse(Call<LoanRest> call, Response<LoanRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new LoanRestResponse(data, networkError);
    }

    public UserRestResponse getUser() {
        MutableLiveData<UserRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        String userId = mSharedPreferences.getString(USER_ID_DEFAULT_KEY, "");

        mClient.getApi().getUser(userId).enqueue(new Callback<UserRest>() {
            @Override
            public void onResponse(Call<UserRest> call, Response<UserRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });
        return new UserRestResponse(data, networkError);
    }

    public LoanRestResponse updateUserLoanApplicationByAdmin(String userId, String loanId, AdminLoanRequestModel adminLoanRequestModel) {
        MutableLiveData<LoanRest> data = new MutableLiveData<>();
        MutableLiveData<String> networkError = new MutableLiveData<>();

        mClient.getApi().updateUserLoanApplicationByAdmin(userId, loanId, adminLoanRequestModel).enqueue(new Callback<LoanRest>() {
            @Override
            public void onResponse(Call<LoanRest> call, Response<LoanRest> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String e = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + e);
                            networkError.setValue(e);
                        } catch (IOException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        networkError.setValue("Unknown error, please try again");
                        Log.d(TAG, "onResponse: Unknown error, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanRest> call, Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    networkError.setValue(t.getMessage());
                } else {
                    networkError.setValue("Unknown Error from server!");
                    Log.d(TAG, "onFailure: " + "Unknown Error from server!");
                }
            }
        });

        return new LoanRestResponse(data, networkError);
    }
}

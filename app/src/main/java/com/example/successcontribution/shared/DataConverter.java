package com.example.successcontribution.shared;

import androidx.room.TypeConverter;

import com.example.successcontribution.model.response.LoanRest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {
    @TypeConverter
    public static List<LoanRest> fromString(String value) { //While reading data back from Room Database,
        Type listType = new TypeToken<List<LoanRest>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromLoanList(List<LoanRest> loans) { //returns String representation of List<LoanRest> loans in UserRest to be stored in DB
        Gson gson = new Gson();
        String json = gson.toJson(loans);
        return json;
    }
}

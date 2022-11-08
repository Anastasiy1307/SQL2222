package com.example.sql;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitAPIDelete {
    @DELETE("motoes/")
    Call<DataModal> deleteData(@Query("Id") int id);
}

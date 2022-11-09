package com.example.sql;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPIUpdate {
    @PUT("motoes/")
    Call<DataModal> updateData(@Query("id") int id, @Body DataModal dataModal);

}

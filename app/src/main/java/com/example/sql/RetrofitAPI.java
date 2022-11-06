package com.example.sql;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("motoes")
    Call<DataModal> createPost(@Body DataModal dataModal);


}

package com.example.attendancegsr.Api;

import com.example.attendancegsr.Models.DefaultResponse;
import com.example.attendancegsr.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("wf/signup")
    Call<DefaultResponse> signup(
            @Field("email") String email,
            @Field("password") String password,
            @Field("company") String company
    );
    @FormUrlEncoded
    @POST("wf/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
}

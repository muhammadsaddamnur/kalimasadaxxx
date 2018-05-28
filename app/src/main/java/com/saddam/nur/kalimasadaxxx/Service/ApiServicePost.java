package com.saddam.nur.kalimasadaxxx.Service;

import com.saddam.nur.kalimasadaxxx.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServicePost {
    @FormUrlEncoded
    @POST("pembeli/daftar")
    Call<ResponseModel> daftarpembeli(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp);

}

package com.saddam.nur.kalimasadaxxx.Service;

import com.saddam.nur.kalimasadaxxx.Model.LoginModel.ResponseModelLogin;
import com.saddam.nur.kalimasadaxxx.Model.ProfilModel.ResponseModelProfil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiServiceGet {
    @GET("pembeli/login")
    Call<ResponseModelLogin> getPembeli(@Header("Authorization") String authHeader);

    @GET("pembeli/profil")
    Call<ResponseModelProfil> getProfilPembeli(@Header("Authorization") String authHeader);
}

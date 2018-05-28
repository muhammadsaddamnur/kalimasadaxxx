package com.saddam.nur.kalimasadaxxx.Model.ProfilModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModelProfil {
    @SerializedName("profil")
    @Expose
    private ProfilValue profil;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public ProfilValue getProfil() {
        return profil;
    }

    public void setProfil(ProfilValue profil) {
        this.profil = profil;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
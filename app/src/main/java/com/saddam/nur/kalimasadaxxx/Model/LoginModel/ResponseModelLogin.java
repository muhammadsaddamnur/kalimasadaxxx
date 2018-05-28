package com.saddam.nur.kalimasadaxxx.Model.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModelLogin {

    @SerializedName("login")
    @Expose
    private LoginValue login;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public LoginValue getLogin() {
        return login;
    }

    public void setLogin(LoginValue login) {
        this.login = login;
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


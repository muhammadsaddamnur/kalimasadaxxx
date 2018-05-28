package com.saddam.nur.kalimasadaxxx.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.saddam.nur.kalimasadaxxx.Model.LoginModel.LoginValue;
import com.saddam.nur.kalimasadaxxx.Model.LoginModel.ResponseModelLogin;
import com.saddam.nur.kalimasadaxxx.R;
import com.saddam.nur.kalimasadaxxx.Service.ApiClient;
import com.saddam.nur.kalimasadaxxx.Service.ApiServiceGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnlogin;
    TextView btndaftar;
    EditText txtemail, txtpassword;
    String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btndaftar = (TextView) findViewById(R.id.btndaftar);


        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                txtemail = (EditText) findViewById(R.id.txtemail);
                txtpassword = (EditText) findViewById(R.id.txtpassword);

                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();
                String user64 = email + ":" + password;
                String authHeader = "Basic " + Base64.encodeToString(user64.getBytes(), Base64.NO_WRAP);

                loginpembeli(authHeader);
                break;
            case R.id.btndaftar:
                Intent daftar = new Intent(this, Daftar.class);
                startActivity(daftar);
                break;
        }
    }

    private void loginpembeli(final String authHeader) {

        ApiServiceGet apiService = ApiClient.getClient().create(ApiServiceGet.class);
        Call<ResponseModelLogin> call = apiService.getPembeli(authHeader); //mengirim semua data ke server untuk di query
        call.enqueue(new Callback<ResponseModelLogin>() {
            @Override
            public void onResponse(Call<ResponseModelLogin> call, Response<ResponseModelLogin> response) {

                ResponseModelLogin responseModelLogin = response.body();

                //check the status code
                if (responseModelLogin.getSuccess() == 200) {
                    LoginValue loginValues = responseModelLogin.getLogin();

                    SharedPreferences prefs = getSharedPreferences("kalimasada", MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = prefs.edit();

                    String token = loginValues.getToken();

                    mEditor.putString("token", "Bearer " + token);
                    mEditor.commit();

                    //Toast.makeText(login.this, user, Toast.LENGTH_SHORT).show();
                    Intent main = new Intent(Login.this, MainActivity.class);
                    startActivity(main);
                    finish();
                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelLogin> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.saddam.nur.kalimasadaxxx.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.saddam.nur.kalimasadaxxx.Model.ResponseModel;
import com.saddam.nur.kalimasadaxxx.R;
import com.saddam.nur.kalimasadaxxx.Service.ApiClient;
import com.saddam.nur.kalimasadaxxx.Service.ApiServicePost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity implements View.OnClickListener {
    Button btndaftar ;
    TextView btnlogin;
    EditText txtnama, txtemail, txtpassword, txtalamat, txtnohp;
    String nama, email, password, alamat, no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        btndaftar = (Button) findViewById(R.id.btndaftar);
        btnlogin = (TextView) findViewById(R.id.btnlogin);

        txtnama = (EditText) findViewById(R.id.txtnama);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        txtalamat = (EditText) findViewById(R.id.txtalamat);
        txtnohp = (EditText) findViewById(R.id.txtnohp);

        btndaftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                Intent daftar = new Intent(this, Login.class);
                startActivity(daftar);
                break;
            case R.id.btndaftar:
                nama = txtnama.getText().toString();
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();
                alamat = txtalamat.getText().toString();
                no_hp = txtnohp.getText().toString();

                daftarpembeli(nama, email, password, alamat, no_hp);
                break;
        }
    }

    private void daftarpembeli(String nama, String email, String password, String alamat, String no_hp) {
        ApiServicePost apiService = ApiClient.getClient().create(ApiServicePost.class);
        Call<ResponseModel> call = apiService.daftarpembeli(nama, email, password, alamat, no_hp);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                ResponseModel ResponseModel = response.body();

                if (ResponseModel.getSuccess() == 200) {
                    Toast.makeText(Daftar.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(Daftar.this, Login.class);
                    startActivity(login);
                    finish();
                } else {
                    Toast.makeText(Daftar.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Daftar.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

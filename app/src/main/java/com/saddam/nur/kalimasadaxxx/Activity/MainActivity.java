package com.saddam.nur.kalimasadaxxx.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.saddam.nur.kalimasadaxxx.Model.LoginModel.LoginValue;
import com.saddam.nur.kalimasadaxxx.Model.LoginModel.ResponseModelLogin;
import com.saddam.nur.kalimasadaxxx.Model.ProfilModel.ProfilValue;
import com.saddam.nur.kalimasadaxxx.Model.ProfilModel.ResponseModelProfil;
import com.saddam.nur.kalimasadaxxx.R;
import com.saddam.nur.kalimasadaxxx.Service.ApiClient;
import com.saddam.nur.kalimasadaxxx.Service.ApiServiceGet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton btnpesan ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnpesan = (FloatingActionButton) findViewById(R.id.btnpesan);

        btnpesan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnpesan:
                SharedPreferences prefs = getSharedPreferences("kalimasada", Context.MODE_PRIVATE);
                String token = prefs.getString("token", null);
                profil(token);
                /*Intent pesan = new Intent(this, Pemesanan.class);
                startActivity(pesan);*/
                break;

        }
    }

    private void profil(final String authHeader) {

        ApiServiceGet apiService = ApiClient.getClient().create(ApiServiceGet.class);
        Call<ResponseModelProfil> call = apiService.getProfilPembeli(authHeader); //mengirim semua data ke server untuk di query
        call.enqueue(new Callback<ResponseModelProfil>() {
            @Override
            public void onResponse(Call<ResponseModelProfil> call, Response<ResponseModelProfil> response) {

                ResponseModelProfil responseModelProfil = response.body();

                //check the status code
                if (responseModelProfil.getSuccess() == 200) {
                    ProfilValue profilValue = responseModelProfil.getProfil();

                    SharedPreferences prefs = getSharedPreferences("kalimasada", MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = prefs.edit();

                    String token = profilValue.getIdPembeli();

                    Intent pesan = new Intent(MainActivity.this, Pemesanan.class);
                    pesan.putExtra("id_pembeli", token);
                    startActivity(pesan);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelProfil> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

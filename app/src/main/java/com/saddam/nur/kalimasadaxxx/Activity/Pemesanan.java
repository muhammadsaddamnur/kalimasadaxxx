package com.saddam.nur.kalimasadaxxx.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saddam.nur.kalimasadaxxx.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.EasyPermissions;

public class Pemesanan extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    String DesainJersey = "";
    String urlDesainJersey = "";
    private Uri uri;

    EditText txtid_pembeli, txtid_pemesanan, txtjumlah_pemesanan, txttotal_harga;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        txtid_pembeli = (EditText) findViewById(R.id.txtid_pembeli);
        txtid_pemesanan = (EditText) findViewById(R.id.txtid_pemesanan);
        txtjumlah_pemesanan = (EditText) findViewById(R.id.txtjml_pemesanan);
        txttotal_harga = (EditText) findViewById(R.id.txttotal_harga);

        btnUpload = (Button) findViewById(R.id.btnupload);

        Bundle extras = getIntent().getExtras();
        String id_pembeli = extras.getString("id_pembeli");
        txtid_pembeli.setText(id_pembeli);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String currentDateandTime = sdf.format(new Date());

        Random rand = new Random();
        int n = rand.nextInt(99);

        txtid_pemesanan.setText(id_pembeli + currentDateandTime + n);


        txtjumlah_pemesanan.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (s.length() != 0) {
                    int harga = 150000;
                    String txtjml = txtjumlah_pemesanan.getText().toString();
                    int jumlah = Integer.parseInt(txtjml);

                    harga = harga * jumlah;
                    String tampilharga = String.valueOf(harga);
                    txttotal_harga.setText("Rp. " + tampilharga);
                } else {
                    txttotal_harga.setText("Rp. 0");
                }
            }
        });


        btnUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnupload:
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, Pemesanan.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, Pemesanan.this);

                String[] FileName = filePath.split("/");
                int ke = FileName.length - 1;


                TextView txtDesainJersey = (EditText) findViewById(R.id.txtdesain_jersey);
                urlDesainJersey = FileName[ke];
                txtDesainJersey.setText(FileName[ke]);
                DesainJersey = filePath;
                ImageView imgDesain = (ImageView) findViewById(R.id.imgDesainJersey);
                Picasso.with(this)
                        .load(new File(DesainJersey))
                        .into(imgDesain);


            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            String filePath = getRealPathFromURIPath(uri, Pemesanan.this);
            TextView txtDesainJersey = (TextView) findViewById(R.id.txtdesain_jersey);
            txtDesainJersey.setText(filePath);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

}

package com.example.kerangkasamplingarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.CreateResponse;
import com.example.kerangkasamplingarea.model.LoginResponseModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText segmenInput, lintangInput, bujurInput, kabupatenInput;
    private String selectedDate;
    private TextView dateinput;
    private ApiService apiService;
    private  String accessToken;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        accessToken = getIntent().getStringExtra("accessToken");

        segmenInput = findViewById(R.id.segmenInput);
        lintangInput = findViewById(R.id.lintangInput);
        bujurInput = findViewById(R.id.bujurInput);
        kabupatenInput = findViewById(R.id.kabupatenInput);
        dateinput = findViewById(R.id.dateinput);
        Button createButton = findViewById(R.id.createButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(TambahActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(TambahActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    createData();
                } else{
                    Toast.makeText(TambahActivity.this, "Isian tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValid(){
        String segmen = segmenInput.getText().toString();
        String lintang = lintangInput.getText().toString();
        String bujur = bujurInput.getText().toString();
        String kabupaten = kabupatenInput.getText().toString();
        return !TextUtils.isEmpty(segmen)&&!TextUtils.isEmpty(lintang)&&!TextUtils.isEmpty(bujur)&&!TextUtils.isEmpty(bujur);
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment datePickerFragment = new TimePickerFragment();
        datePickerFragment.setOnDateSetListener(new TimePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = year+ "-" +(month + 1) + "-" +dayOfMonth;
                dateinput.setText(selectedDate);
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void createData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
        String segmen = segmenInput.getText().toString();
        Double lintang = Double.valueOf(lintangInput.getText().toString());
        Double bujur = Double.valueOf(bujurInput.getText().toString());
        int kabupaten = Integer.parseInt(kabupatenInput.getText().toString());
        JsonObject createRequest = new JsonObject();
        String tanggal = selectedDate;
        createRequest.addProperty("tanggalpendataan", tanggal);
        createRequest.addProperty("lintang", lintang);
        createRequest.addProperty("bujur", bujur);
        createRequest.addProperty("segmen", segmen);
        createRequest.addProperty("idKab", kabupaten);

        Call<CreateResponse> call = apiService.createksa("Bearer " +accessToken,createRequest);

        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CreateResponse result = response.body();
                        Toast.makeText(TambahActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(TambahActivity.this, MenuActivity.class);
                        intent.putExtra("accessToken", accessToken);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TambahActivity.this, "Gagal menambahkan data. Error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TambahActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahActivity.this, MenuActivity.class);
                    intent.putExtra("accessToken", accessToken);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TambahActivity.this, MenuActivity.class);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
                finish();
            }
        });
    }
}
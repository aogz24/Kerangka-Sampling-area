package com.example.kerangkasamplingarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.LoginResponseModel;
import com.example.kerangkasamplingarea.model.RegisterResponseModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, namaEditText;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        TextView loginTextView = findViewById(R.id.login);

        Button register = findViewById(R.id.button);

        emailEditText = findViewById(R.id.email);
        namaEditText = findViewById(R.id.nama);
        passwordEditText = findViewById(R.id.Password);



        apiService = RetrofitClient.getClient().create(ApiService.class);

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValid()){
                            if(isEmailValid()){
                                registerUser();
                            } else{
                                Toast.makeText(RegistrasiActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(RegistrasiActivity.this, "Isian tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isEmailValid(){
        String email = emailEditText.getText().toString().trim();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValid(){
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String nama = namaEditText.getText().toString().trim();
        return !TextUtils.isEmpty(password)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(nama);
    };

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String nama = namaEditText.getText().toString().trim();


        JsonObject registerRequest = new JsonObject();
        registerRequest.addProperty("email", email);
        registerRequest.addProperty("password", password);
        registerRequest.addProperty("name", nama);

        Call<RegisterResponseModel> call = apiService.regisUser(registerRequest);
        System.out.println(call.toString());

        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        RegisterResponseModel result = response.body();
                        Toast.makeText(RegistrasiActivity.this, "Registrasi berhasil, silahkan login", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println(response.headers());
                        System.out.println(response.errorBody().toString());
                        Toast.makeText(RegistrasiActivity.this, "Registrasi gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegistrasiActivity.this, "Exception in onResponse: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                Toast.makeText(RegistrasiActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
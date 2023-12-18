package com.example.kerangkasamplingarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.LoginResponseModel;
import com.google.gson.JsonObject;

import java.io.IOException;

import okio.Buffer;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ApiService apiService;
    ImageView showhide;
    private boolean pass =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView loginTextView = findViewById(R.id.register);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.loginbutton);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail()&&isValid()) {
                    loginUser();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showhide = findViewById(R.id.show);

        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass){
                    showhide.setImageResource(R.drawable.show);
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else{
                    showhide.setImageResource(R.drawable.hidden);
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                pass =!pass;
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
    }

    private boolean isValidEmail() {
        String email = emailEditText.getText().toString().trim();
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValid(){
        String password = passwordEditText.getText().toString().trim();
        return !TextUtils.isEmpty(password);
    }
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Create a JSON object with email and password
        JsonObject loginRequest = new JsonObject();
        loginRequest.addProperty("email", email);
        loginRequest.addProperty("password", password);

        Call<LoginResponseModel> call = apiService.loginUser(loginRequest);
        System.out.println(call.toString());

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        LoginResponseModel result = response.body();
                        System.out.println(result.getAccessToken());
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("accessToken", result.getAccessToken());
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println(response.headers());
                        System.out.println(response.errorBody().toString());
                        Toast.makeText(LoginActivity.this, "Login gagal, coba periksa email atau password.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Exception in onResponse: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
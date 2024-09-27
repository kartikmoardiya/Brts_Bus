package com.example.city_bus.User_Login_Signout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.city_bus.MainActivity;
import com.example.city_bus.Models.Token;
import com.example.city_bus.R;

import com.example.city_bus.API.Instancee;
import com.example.city_bus.Models.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText loginEdtEmail, loginEdtPass;
    Button loginBtn;
    TextView loginTxtSignUp, loginTxtFrgtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginEdtEmail = findViewById(R.id.loginEdtEmail);
        loginEdtPass = findViewById(R.id.loginEdtPass);
        loginBtn = findViewById(R.id.loginBtn);
        loginTxtSignUp = findViewById(R.id.loginTxtSignup);
        loginTxtFrgtPass = findViewById(R.id.loginTxtFrgtPass);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = loginEdtEmail.getText().toString();
                pass = loginEdtPass.getText().toString();

                if (email == null || pass == null) {
                    Toast.makeText(Login.this, "Enter Email and pass", Toast.LENGTH_SHORT).show();

                } else {
                    clickOnLogin(email, pass);
                }
            }
        });

        loginTxtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpPage();
            }
        });

        loginTxtFrgtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPassChange();
            }
        });

    }

    private void goToPassChange() {
        Intent i = new Intent(Login.this, ForgatePassword.class);
        startActivity(i);
    }

    private void goToSignUpPage() {
        Intent i = new Intent(Login.this, SignUp.class);
        startActivity(i);
    }

    // Click on login Button
    public void clickOnLogin(String email, String pass) {

        LoginRequest user = new LoginRequest(email, pass);

        Call<Token> call = Instancee.getService().userLogin(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Khutu chhe hoo bhai taru", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("nthi Done","A");

                Toast.makeText(Login.this, "So sad chhe hoo bhai taru", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Sign Up Page
}
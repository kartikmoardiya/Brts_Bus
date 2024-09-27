package com.example.city_bus.User_Login_Signout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.city_bus.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText signUpEdtName, signUpEdtMobnum, signUpEdtEmail, signUpEdtPass;
    Button signUpBtnDone;
    String name, mobNum, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpEdtName = findViewById(R.id.signUpEdtName);
        signUpEdtMobnum = findViewById(R.id.signUpEdtMobNum);
        signUpEdtEmail = findViewById(R.id.signUpEdtEmail);
        signUpEdtPass = findViewById(R.id.signUpEdtPassword);
        signUpBtnDone = findViewById(R.id.signUpBtnDone);



        signUpBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = signUpEdtName.getText().toString();
                mobNum = signUpEdtMobnum.getText().toString();
                email = signUpEdtEmail.getText().toString();
                pass = signUpEdtPass.getText().toString();
                storeData(name, mobNum,email,pass);
            }
        });
    }

    private void storeData(String name, String mobNum, String email, String pass) {
        if(name == null || mobNum == null || email == null || pass == null)
        {
            Toast.makeText(this, "Badhu Nakh n Bhopa", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(name,email,mobNum,pass);
        Call<Token> call = Instancee.getService().userSingUp(user);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.isSuccessful() && response.body() != null)
                        {
                            Intent i = new Intent(SignUp.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "Bindas Thama Aavu Mail Id Already Kok Pahe Chhe Biju Nakh", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {

                    }
                });
    }
}
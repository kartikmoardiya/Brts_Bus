package com.example.city_bus.User_Login_Signout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.city_bus.R;


import com.example.city_bus.API.Instancee;
import com.example.city_bus.Models.ForgatePass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgatePassword extends AppCompatActivity {

    EditText frgtPassEdtEmail,frgtPassEdtCurr,frgtPassEdtNew;
    Button frgtPassBtnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgate_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frgtPassEdtEmail = findViewById(R.id.frgtPassEdtEmail);
        frgtPassEdtCurr = findViewById(R.id.frgtPassEdtCurr);
        frgtPassEdtNew = findViewById(R.id.frgtPassEdtNew);
        frgtPassBtnDone = findViewById(R.id.frgtPassBtnDone);


        // Click on done
        frgtPassBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email , currPass, newPass;
                email = frgtPassEdtEmail.getText().toString();
                currPass = frgtPassEdtCurr.getText().toString();
                newPass = frgtPassEdtNew.getText().toString();

                retrofitApi(email,currPass,newPass);
            }
        });
    }

    private void retrofitApi(String email, String currPass, String newPass) {
        ForgatePass pass = new ForgatePass(email,newPass,currPass);

        Call<String> call = Instancee.getService().userPut(pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.d("Done","A");
                }else{
                    Log.d("Done nthi","A"+response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
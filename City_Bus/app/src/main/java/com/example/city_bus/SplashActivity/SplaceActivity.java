package com.example.city_bus.SplashActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.city_bus.MainActivity;
import com.example.city_bus.Models.LoginRequest;
import com.example.city_bus.R;

import java.util.ArrayList;

import com.example.city_bus.API.Instancee;
import com.example.city_bus.Models.AllRoutes;
import com.example.city_bus.User_Login_Signout.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplaceActivity extends AppCompatActivity {

    ArrayList<String> allRoutes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Call<AllRoutes> call = Instancee.getService().getAllRoutes();

        call.enqueue(new Callback<AllRoutes>() {
            @Override
            public void onResponse(Call<AllRoutes> call, Response<AllRoutes> response) {
                if (response.isSuccessful()) {
                    allRoutes = (ArrayList<String>) response.body().getAllStations();
                    Log.d("Ready", "A");
                } else {
                    Log.d("Ready nthi", "A");
                }
            }

            @Override
            public void onFailure(Call<AllRoutes> call, Throwable t) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Ready nthi", "A" + allRoutes.size());
                Bundle dataBundle = new Bundle();
                dataBundle.putStringArrayList("allRoutes", allRoutes);

                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag",false);

                Intent i;
                if(check)
                {
                    i = new Intent(SplaceActivity.this,MainActivity.class);
                }else{
                    i = new Intent(SplaceActivity.this, Login.class);
                }
                startActivity(i);
                finish();
            }
        }, 4000);


    }
}
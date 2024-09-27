package com.example.city_bus;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import com.example.city_bus.Fragments.Home;
import com.example.city_bus.Fragments.Notes;
import com.example.city_bus.Fragments.Routes;
import com.example.city_bus.Fragments.Settings;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
//    ArrayList<String> allRoutes = new ArrayList<>();

    BottomNavigationView bnView;
    Home home = new Home();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        bnView = findViewById(R.id.bnView);
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.btnRount) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, home)
                            .commit();
                } else if (id == R.id.btnNotes) {
                    loadFrag(new Notes());
                } else if (id == R.id.btnMap) {
                    loadFrag(new Routes());
                } else {
                    loadFrag(new Settings());
                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.btnRount);
    }

    public void loadFrag(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Bundle bundle = getIntent().getExtras();
//        allRoutes = bundle.getStringArrayList("allRoutes");
//
//        Log.d("Jova de","A"+allRoutes.size());
//
//        Bundle b = new Bundle();
//        b.putStringArrayList("allRoutes",allRoutes);
//        home.setArguments(b);
//    }

}
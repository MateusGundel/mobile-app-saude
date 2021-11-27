package com.elefante.app_saude;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.measurement.BloodPressure;
import com.elefante.app_saude.measurement.Diabetes;
import com.elefante.app_saude.measurement.HeartBeat;
import com.elefante.app_saude.measurement.Height;
import com.elefante.app_saude.measurement.Vaccine;
import com.elefante.app_saude.measurement.Weigth;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
//        TextView email_menu = findViewById(R.id.email_menu);
//        TextView access_code_menu = findViewById(R.id.access_code_menu);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
//        email_menu.setText(prefs.getString("email", ""));
//        access_code_menu.setText(prefs.getString("access_token", ""));
        LinearLayout menu_vaccine = findViewById(R.id.menu_vaccine);
        menu_vaccine.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, Vaccine.class);
            startActivity(appInfo);
        });
        LinearLayout menu_heatbeat = findViewById(R.id.menu_heatbeat);
        menu_heatbeat.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, HeartBeat.class);
            startActivity(appInfo);
        });
        LinearLayout menu_height = findViewById(R.id.menu_height);
        menu_height.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, Height.class);
            startActivity(appInfo);
        });
        LinearLayout menu_weight = findViewById(R.id.menu_weight);
        menu_weight.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, Weigth.class);
            startActivity(appInfo);
        });
        LinearLayout menu_diabetes = findViewById(R.id.menu_diabetes);
        menu_diabetes.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, Diabetes.class);
            startActivity(appInfo);
        });
        LinearLayout menu_blood_pressure = findViewById(R.id.menu_blood_pressure);
        menu_blood_pressure.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, BloodPressure.class);
            startActivity(appInfo);
        });
    }
}
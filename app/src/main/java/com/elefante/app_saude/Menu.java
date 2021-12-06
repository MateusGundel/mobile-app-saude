package com.elefante.app_saude;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.measurement.BloodPressure;
import com.elefante.app_saude.measurement.Diabetes;
import com.elefante.app_saude.measurement.HeartBeat;
import com.elefante.app_saude.measurement.Height;
import com.elefante.app_saude.measurement.Imc;
import com.elefante.app_saude.measurement.Vaccine;
import com.elefante.app_saude.measurement.Weight;
import com.elefante.app_saude.user.UserInfo;

public class Menu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.perfil_button) {
            Intent appInfo = new Intent(Menu.this, UserInfo.class);
            startActivity(appInfo);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
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
            Intent appInfo = new Intent(Menu.this, Weight.class);
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
        LinearLayout menu_imc = findViewById(R.id.menu_imc);
        menu_imc.setOnClickListener(v -> {
            Intent appInfo = new Intent(Menu.this, Imc.class);
            startActivity(appInfo);
        });
    }
}
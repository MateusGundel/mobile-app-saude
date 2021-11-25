package com.elefante.app_saude;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        TextView email_menu = findViewById(R.id.email_menu);
        TextView access_code_menu = findViewById(R.id.access_code_menu);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        email_menu.setText(prefs.getString("email", ""));
        access_code_menu.setText(prefs.getString("access_token", ""));


    }
}
package com.elefante.app_saude.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button exit_button = findViewById(R.id.user_exit_button);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        exit_button.setOnClickListener(v -> {
            editor.remove("email");
            editor.remove("access_token");
            editor.apply();
        });
    }
}
package com.elefante.app_saude.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;
import com.elefante.app_saude.login.Login;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        Button exit_button = findViewById(R.id.user_exit_button);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        exit_button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
            builder.setMessage("Você tem certeza que deseja sair ?")
                    .setTitle("Confirmação de saída");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    editor.remove("email");
                    editor.remove("access_token");
                    editor.apply();
                    Intent login = new Intent(UserInfo.this, Login.class);
                    startActivity(login);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent login = new Intent(UserInfo.this, Menu.class);
                    startActivity(login);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
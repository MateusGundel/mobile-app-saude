package com.elefante.app_saude.measurement;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

public class WeightShow extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String access_code = prefs.getString("access_token", "");

        Intent myIntent = getIntent();
        TextView text_name = findViewById(R.id.weight_name);
        TextView text_date = findViewById(R.id.weight_date);
        TextView text_id = findViewById(R.id.weight_id);
        text_id.setText(myIntent.getStringExtra("id"));
        text_name.setText(myIntent.getStringExtra("valor"));
        text_date.setText(myIntent.getStringExtra("date"));
        Button btn_delete = findViewById(R.id.weight_delete);
        btn_delete.setOnClickListener(v -> {

            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete("https://app-saude-unisc.herokuapp.com/api/v1/weight/" + myIntent.getStringExtra("id"));
            httpDelete.setHeader("Authorization", "Bearer " + access_code);
            Thread thread = new Thread(() -> {
                try {
                    HttpResponse response = httpclient.execute(httpDelete);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        Intent appInfo = new Intent(WeightShow.this, Weigth.class);
                        startActivity(appInfo);
                    } else {
                        runOnUiThread(() -> {
                            final Toast toast = Toast.makeText(WeightShow.this,
                                    "Erro ao tentar excluir a vacina",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        });
    }
}
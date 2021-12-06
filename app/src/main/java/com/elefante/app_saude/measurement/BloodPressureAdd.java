package com.elefante.app_saude.measurement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class BloodPressureAdd extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.blood_pressure_add);

            Button button_add = findViewById(R.id.button_add_blood_pressure);
            button_add.setOnClickListener(v -> {
                try {
                    postData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });

        }
    // TODO: 06/12/2021  ajustar post dos dados para dois parametros

        public void postData() throws UnsupportedEncodingException {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(
                    "http://app-saude-unisc.herokuapp.com/api/v1/blood_pressure/");
            SharedPreferences prefs = this.getSharedPreferences(
                    "com.elefante.app_saude", Context.MODE_PRIVATE);
            String access_code = prefs.getString("access_token", "");

            EditText editText = findViewById(R.id.text_add_blood_pressure);
            try {
                JSONObject json = new JSONObject();
                json.put("name", editText.getText().toString());
                StringEntity params = new StringEntity(json.toString());
                httppost.setEntity(params);
                httppost.addHeader("content-type", "application/json");
                httppost.addHeader("Authorization", "Bearer " + access_code);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread(() -> {
                try {
                    HttpResponse response = httpclient.execute(httppost);

                    if (response.getStatusLine().getStatusCode() == 200) {
                        Intent appInfo = new Intent(com.elefante.app_saude.measurement.BloodPressureAdd.this, Vaccine.class);
                        startActivity(appInfo);
                    } else {
                        runOnUiThread(() -> {
                            final Toast toast = Toast.makeText(com.elefante.app_saude.measurement.BloodPressureAdd.this,
                                    "Erro ao salvar",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

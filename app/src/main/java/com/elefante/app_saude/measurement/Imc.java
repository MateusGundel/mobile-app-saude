package com.elefante.app_saude.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Imc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imc);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String access_code = prefs.getString("access_token", "");
        getData(access_code);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getData(String access_code) {
        Thread thread = new Thread(() -> {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://app-saude-unisc.herokuapp.com/api/v1/height?limit=1");
                httpGet.setHeader("Authorization", "Bearer " + access_code);
                Double altura = null;
                Double peso = null;
                HttpResponse response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            altura = jsonObject.getDouble("value");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                HttpClient httpclient2 = new DefaultHttpClient();
                HttpGet httpGet2 = new HttpGet("https://app-saude-unisc.herokuapp.com/api/v1/weight?limit=1");
                httpGet2.setHeader("Authorization", "Bearer " + access_code);
                HttpResponse response2 = httpclient2.execute(httpGet2);
                if (response2.getStatusLine().getStatusCode() == 200) {
                    try {
                        String jsonString = EntityUtils.toString(response2.getEntity());
                        JSONArray jsonArray = new JSONArray(jsonString);
                        System.out.println("???????");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            System.out.println(jsonObject);
                            System.out.println(jsonObject.getDouble("value"));
                            System.out.println(jsonObject.getString("value"));
                            peso = jsonObject.getDouble("value");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(peso);
                System.out.println(altura);
                if (peso != null && altura != null) {
                    Double imc = peso / (altura * altura);
                    System.out.println(imc);
                    TextView imc_text_view = findViewById(R.id.imc_text_view);
                    imc_text_view.setText("Seu imc é de: " + String.format("%.2f", imc));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }
}
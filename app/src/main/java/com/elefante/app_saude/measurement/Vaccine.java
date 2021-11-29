package com.elefante.app_saude.measurement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vaccine extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccine);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String access_code = prefs.getString("access_token", "");
        getData(access_code);
        FloatingActionButton floatingActionButton = findViewById(R.id.vaccine_add_button);
        floatingActionButton.setOnClickListener(v -> {
            Intent appInfo = new Intent(Vaccine.this, VaccineAdd.class);
            startActivity(appInfo);
        });
    }

    public void getData(String access_code) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://app-saude-unisc.herokuapp.com/api/v1/vaccine");
        httpGet.setHeader("Authorization", "Bearer " + access_code);
        Thread thread = new Thread(() -> {
            try {
                HttpResponse response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    List<VaccineItem> list_item = new ArrayList<>();
                    try {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            VaccineItem vaccine_item = new VaccineItem();
                            vaccine_item.name = jsonObject.getString("name");
                            vaccine_item.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonObject.getString("date"));
                            list_item.add(vaccine_item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        ArrayAdapter<VaccineItem> adapter = new ArrayAdapter<>(Vaccine.this, android.R.layout.simple_list_item_1, list_item);
                        ListView vaccine_list = findViewById(R.id.list_view);
                        vaccine_list.setAdapter(adapter);
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}


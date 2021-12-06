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
import java.util.List;

public class HeartBeat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartbeat);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String access_code = prefs.getString("access_token", "");
        getData(access_code);
        FloatingActionButton floatingActionButton = findViewById(R.id.heart_beat_add_button);
        floatingActionButton.setOnClickListener(v -> {
            Intent appInfo = new Intent(HeartBeat.this, HeartBeatAdd.class);
            startActivity(appInfo);
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void getData(String access_code) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://app-saude-unisc.herokuapp.com/api/v1/heartbeat");
        httpGet.setHeader("Authorization", "Bearer " + access_code);
        Thread thread = new Thread(() -> {
            try {
                HttpResponse response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    List<HeartBeatItem> list_item = new ArrayList<>();
                    try {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            HeartBeatItem heartBeatItem = new HeartBeatItem();
                            heartBeatItem.valor = jsonObject.getString("value");
                            heartBeatItem.id = jsonObject.getInt("id");
                            heartBeatItem.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonObject.getString("date"));
                            list_item.add(heartBeatItem);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        ArrayAdapter<HeartBeatItem> adapter = new ArrayAdapter<>(HeartBeat.this, android.R.layout.simple_list_item_1, list_item);
                        ListView heartbeat_list = findViewById(R.id.heart_beat_listView);
                        heartbeat_list.setOnItemClickListener((parent, view, position, id) -> {
                            Intent appInfo = new Intent(HeartBeat.this, HeartBeatShow.class);
                            HeartBeatItem item = adapter.getItem(position);
                            appInfo.putExtra("valor", item.valor);
                            appInfo.putExtra("id", item.id.toString());
                            appInfo.putExtra("date", new SimpleDateFormat("dd/MM/yyyy hh:mm").format(item.date));
                            startActivity(appInfo);
                        });
                        heartbeat_list.setAdapter(adapter);
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}

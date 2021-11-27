package com.elefante.app_saude.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.Menu;
import com.elefante.app_saude.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_code);
        Button button = findViewById(R.id.button_login_code);
        button.setOnClickListener(v -> {
            try {
                postData();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    public void postData() throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "https://app-saude-unisc.herokuapp.com/api/v1/login/access-token-code");
        Intent myIntent = getIntent();
        EditText login_code = findViewById(R.id.login_code);
        try {
            JSONObject json = new JSONObject();
            json.put("email", myIntent.getStringExtra("email"));
            json.put("code", login_code.getText().toString());
            StringEntity params = new StringEntity(json.toString());
            httppost.addHeader("content-type", "application/json");
            httppost.setEntity(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            try {
                HttpResponse response = httpclient.execute(httppost);
                String jsonString = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonString);
                if (!jsonObject.isNull("access_token")
                        && response.getStatusLine().getStatusCode() == 200) {
                    String accessToken = (String) jsonObject.get("access_token");
                    if (!accessToken.isEmpty()) {
                        SharedPreferences prefs = this.getSharedPreferences(
                                "com.elefante.app_saude", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("email", myIntent.getStringExtra("email"));
                        editor.putString("access_token", accessToken);
                        editor.apply();
                        Intent appInfo = new Intent(LoginCode.this, Menu.class);
                        startActivity(appInfo);
                    }
                } else {
                    runOnUiThread(() -> {
                        final Toast toast = Toast.makeText(LoginCode.this,
                                "Código errado ou tempo esgotado",
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
package com.elefante.app_saude.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elefante.app_saude.Menu;
import com.elefante.app_saude.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String access_code = prefs.getString("access_token", "");
        if (!email.equals("") && !access_code.equals("")) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("https://app-saude-unisc.herokuapp.com/api/v1/user/me");
            httpGet.setHeader("Authorization", "Bearer " + access_code);
            Thread thread = new Thread(() -> {
                try {
                    HttpResponse response = httpclient.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        Intent appInfo = new Intent(Login.this, Menu.class);
                        startActivity(appInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        findViewById(R.id.login_layout).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(v -> {
            try {
                postData();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        TextView register_here = findViewById(R.id.register_here);
        register_here.setOnClickListener(v -> {
            Intent appInfo = new Intent(Login.this, Register.class);
            startActivity(appInfo);
        });
        TextView recover_here = findViewById(R.id.recover_here);
        recover_here.setOnClickListener(v -> {
            Intent appInfo = new Intent(Login.this, Recover.class);
            startActivity(appInfo);
        });
    }

    public void postData() throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "https://app-saude-unisc.herokuapp.com/api/v1/login/access-code");
        List<NameValuePair> params = new ArrayList<>(2);
        EditText email_login = findViewById(R.id.email_login);
        EditText password_login = findViewById(R.id.password_login);
        String email = email_login.getText().toString();
        params.add(new BasicNameValuePair("username", email));
        params.add(new BasicNameValuePair("password", password_login.getText().toString()));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        Thread thread = new Thread(() -> {
            try {
                HttpResponse response = httpclient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    Intent appInfo = new Intent(Login.this, LoginCode.class);
                    appInfo.putExtra("email", email);
                    startActivity(appInfo);
                } else {
                    runOnUiThread(() -> {
                        final Toast toast = Toast.makeText(Login.this,
                                "Erro ao realizar login, talvez os teus dados estejam " +
                                        "errados :(\nTenta de novo aí tiu",
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
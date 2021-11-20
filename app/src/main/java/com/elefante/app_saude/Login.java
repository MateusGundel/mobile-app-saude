package com.elefante.app_saude;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
    }

    public void postData() throws UnsupportedEncodingException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "https://app-saude-unisc.herokuapp.com/api/v1/login/access-token");
        List<NameValuePair> params = new ArrayList<>(2);
        EditText email_login = findViewById(R.id.email_login);
        EditText password_login = findViewById(R.id.password_login);
        params.add(new BasicNameValuePair("username", email_login.getText().toString()));
        params.add(new BasicNameValuePair("password", password_login.getText().toString()));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        Thread thread = new Thread(() -> {
            try {
                HttpResponse response = httpclient.execute(httppost);
                String jsonString = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonString);
                if (!jsonObject.isNull("access_token")
                        && response.getStatusLine().getStatusCode() == 200) {
                    String accessToken = (String) jsonObject.get("access_token");
                    if (!accessToken.isEmpty()) {
                        System.out.println(response.getStatusLine().getStatusCode());
                        System.out.println(accessToken);
                        Intent appInfo = new Intent(Login.this, Menu.class);
                        startActivity(appInfo);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(Login.this,
                                    "Erro ao realizar login, talvez os teus dados estejam " +
                                            "errados :(\nTenta de novo aí tiu",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        // Execute HTTP Post Request


    }
}
package com.elefante.app_saude.measurement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
        EditText date_add_blood_pressure = findViewById(R.id.date_add_blood_pressure);
        date_add_blood_pressure.setInputType(InputType.TYPE_NULL);
        date_add_blood_pressure.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    date_add_blood_pressure.setText(simpleDateFormat.format(calendar.getTime()));

                };
                new TimePickerDialog(BloodPressureAdd.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            };
            new DatePickerDialog(BloodPressureAdd.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    public void postData() throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "http://app-saude-unisc.herokuapp.com/api/v1/blood_pressure/");
        SharedPreferences prefs = this.getSharedPreferences(
                "com.elefante.app_saude", Context.MODE_PRIVATE);
        String access_code = prefs.getString("access_token", "");

        EditText text_add_blood_pressure = findViewById(R.id.text_add_blood_pressure);
        EditText text_add_blood_pressure2 = findViewById(R.id.text_add_blood_pressure2);
        EditText date_add_blood_pressure = findViewById(R.id.date_add_blood_pressure);
        try {
            JSONObject json = new JSONObject();
            json.put("first_value", text_add_blood_pressure.getText().toString());
            json.put("second_value", text_add_blood_pressure2.getText().toString());
            json.put("date", date_add_blood_pressure.getText().toString());
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
                    Intent appInfo = new Intent(BloodPressureAdd.this, BloodPressure.class);
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

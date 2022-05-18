package com.ednaelnunes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnnotify;
    private EditText editeMinutes;
    private TimePicker timePicker;

    private int hour;
    private int minute;
    private int interval;

    private boolean activated = false;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnnotify = findViewById(R.id.btn_notify);
        editeMinutes = findViewById(R.id.edit_txt_number_interval);
        timePicker = findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);
        preferences = getSharedPreferences("db", Context.MODE_PRIVATE);

        activated =  preferences.getBoolean("activated", false);

        if (activated) {
            btnnotify.setText(R.string.pause);
            int color = ContextCompat.getColor(this, android.R.color.black);
            btnnotify.setBackgroundColor(color);
            activated = true;

           int interval =  preferences.getInt("interval", 0);
           int hour = preferences.getInt("hour", timePicker.getCurrentHour());
           int minute = preferences.getInt("minute", timePicker.getCurrentMinute());

           editeMinutes.setText(String.valueOf(interval));
           timePicker.setCurrentHour(hour);
           editeMinutes.setText(String.valueOf(minute));

        }

    }

    public void notifyClick(View view) {
        String sInterval = editeMinutes.getText().toString();

        if (sInterval.isEmpty()) {
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
            return;
        }

        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        interval = Integer.parseInt(sInterval);

        if (!activated) {
            btnnotify.setText(R.string.pause);
            int color = ContextCompat.getColor(this, android.R.color.black);
            btnnotify.setBackgroundColor(color);
            activated = true;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("activated", true);
            editor.putInt("Interval", interval);
            editor.putInt("hour", hour);
            editor.putInt("minute", minute);
            editor.apply();

        } else {
            btnnotify.setText(R.string.notify);
            int color = ContextCompat.getColor(this, R.color.colorAccent);
            btnnotify.setBackgroundColor(color);
            activated = false;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("activated", false);
            editor.remove("Interval");
            editor.remove("hour");
            editor.remove("minute");
            editor.apply();
        }

        Log.d("Teste", " hora " + hour + " minuto: " + minute + " intervalo: " + interval);

    }

}
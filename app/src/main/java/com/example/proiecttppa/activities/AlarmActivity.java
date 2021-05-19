package com.example.proiecttppa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proiecttppa.globals.CustomAlarm;
import com.example.proiecttppa.R;

public class AlarmActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String hour = intent.getStringExtra("hour");
        String minute = intent.getStringExtra("minute");
        TextView alarmName = findViewById(R.id.alarmName);
        TextView alarmValue = findViewById(R.id.alarmValue);
        alarmName.setText(name);
        if (Integer.parseInt(minute) < 10) {
            minute = "0" + minute;
        }
        alarmValue.setText(hour + ":" + minute);
    }

    public void stopAlarm(View view) {
        CustomAlarm customAlarm = CustomAlarm.getInstance();
        customAlarm.ringtone.stop();
        this.finish();
    }


}

package com.example.proiecttppa.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.proiecttppa.AlarmReceiver;
import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalData.getInstance().setContext(this);

        //setAlarm();

        final PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //System.out.println(counter);
                if (powerManager.isScreenOn()) {
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }, 0, 1000);

    }

    public void getRecordPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1234);
        } else {
            initializePlayerAndStartRecording();
        }
    }

    public void initializePlayerAndStartRecording() {
        Intent myIntent = new Intent(MainActivity.this, StartRecordingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializePlayerAndStartRecording();

                } else {
                    System.out.println("Permission denied");
                }
                return;
            }
        }
    }

    public void switchToScheduler(View view) {
        Intent myIntent = new Intent(MainActivity.this, AlarmSchedulerActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void startSleepTracking(View view) {
        getRecordPermission();
    }

    public void switchToHistory(View view) {
        Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
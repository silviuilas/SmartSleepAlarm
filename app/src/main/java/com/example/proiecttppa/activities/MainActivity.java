package com.example.proiecttppa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import com.example.proiecttppa.AlarmReceiver;
import com.example.proiecttppa.R;
import com.example.proiecttppa.SoundMeter;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecordPermission();

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

        //startService(new Intent(this, MicrophoneMonitorService.class));
    }

    public void setAlarm() {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        10 * 1000, alarmIntent);
    }

    public void setAlarm2() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 38);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
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
        Thread recordInBackGround = new Thread(new Runnable() {
            @Override
            public void run() {
                final SoundMeter soundMeter = new SoundMeter();
                soundMeter.start();
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        //System.out.println(soundMeter.getAmplitude());
                    }
                }, 0, 100);//put here time 1000 milliseconds=1 second
            }
        });
        recordInBackGround.start();
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
        System.out.println("Salut");
        Intent myIntent = new Intent(MainActivity.this, AlarmSchedulerActivity.class);
        myIntent.putExtra("key", "sal"); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }
}
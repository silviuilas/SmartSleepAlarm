package com.example.proiecttppa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.proiecttppa.activities.AlarmActivity;


public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "alarm_test_check";
    private Ringtone r;

    @Override
    public void onReceive(Context context, Intent intent3) {
        //Toast.makeText(context, "AlarmReceiver called", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: called ");

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        CustomAlarm customAlarm = CustomAlarm.getInstance();
        customAlarm.ringtone = RingtoneManager.getRingtone(context, notification);
        customAlarm.ringtone.play();

        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }
}

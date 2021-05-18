package com.example.proiecttppa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.proiecttppa.activities.AlarmActivity;
import com.example.proiecttppa.models.Alarm;


public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "alarm_test_check";
    private Ringtone r;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "AlarmReceiver called", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: called ");
        String name = intent.getStringExtra("name");
        String hour = intent.getStringExtra("hour");
        String minute = intent.getStringExtra("minute");
        System.out.println("Alarm got " + name + " " + hour + " " + minute);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        CustomAlarm customAlarm = CustomAlarm.getInstance();
        customAlarm.ringtone = RingtoneManager.getRingtone(context, notification);
        customAlarm.ringtone.play();
        disableOldAlarm(name, hour, minute);
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("name", name);
        intent2.putExtra("hour", hour);
        intent2.putExtra("minute", minute);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

    private void disableOldAlarm(String name, String hour, String minute) {
        AlarmAdapter alarmAdapter = AlarmAdapter.getInstance(null);
        int count = alarmAdapter.getCount();
        for (int i = 0; i <= count; i++) {
            Alarm alarm = alarmAdapter.getItem(i);
            if (alarm.name.equals(name)) {
                if (alarm.hour == Integer.parseInt(hour) && alarm.minute == Integer.parseInt(minute)) {
                    alarmAdapter.disableAlarm(i);
                    alarm.isActive = false;
                    alarmAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}

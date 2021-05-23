package com.example.proiecttppa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.proiecttppa.activities.AlarmActivity;
import com.example.proiecttppa.adapters.AlarmAdapter;
import com.example.proiecttppa.globals.CustomAlarm;
import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.helpers.ShouldYouWakeUp;
import com.example.proiecttppa.models.Alarm;
import com.example.proiecttppa.models.Report;

import java.util.Calendar;
import java.util.Random;


public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "alarm_test_check";
    private Ringtone r;
    private AlarmAdapter alarmAdapter = AlarmAdapter.getInstance();
    private AlarmManager alarmManager;
    private Context context;
    int howManyToMinutesDelay = 1;
    int maxSnooze = 30;


    @Override
    public void onReceive(Context context1, Intent intent) {
        context = context1;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG, "onReceive: called ");
        String name = intent.getStringExtra("name");
        String hour = intent.getStringExtra("hour");
        String minute = intent.getStringExtra("minute");
        Log.d(TAG, "Alarm got " + name + " " + hour + " " + minute);

        int index = findAlarmIndex(name, hour, minute);
        Alarm alarm = alarmAdapter.getItem(index);
        if (!alarm.isSmartAlarm) {
            startAlarm(index, alarm);
            return;
        }
        if (alarm.isSmartAlarm && monitoringIsOn() && shouldDelayAlarm(alarm)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, howManyToMinutesDelay);
            delayAlarm(index, alarm, cal);
            return;
        }
    }

    public void delayAlarm(int index, Alarm alarm, Calendar cal) {
        Log.d(TAG, "Delayed alarm for time" + cal.toString());
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("hour", String.valueOf(alarm.hour));
        intent.putExtra("minute", String.valueOf(alarm.minute));
        intent.putExtra("name", String.valueOf(alarm.name));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, new Random().nextInt(), intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmAdapter.getPendingIntents().set(index, pendingIntent);
    }

    public boolean shouldDelayAlarm(Alarm alarm) {
        Calendar calendarAlarm = transformAlarmToCalendar(alarm);
        calendarAlarm.add(Calendar.MINUTE, maxSnooze);
        // if the snooze time has passed then the alarm should not be delayed
        if (calendarAlarm.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
            return false;
        }
        Report report = GlobalData.getInstance().getRecordInfo().getReport();
        ShouldYouWakeUp shouldYouWakeUp = new ShouldYouWakeUp(report);
        return !shouldYouWakeUp.shouldIWakeUp();
    }

    public boolean monitoringIsOn() {
        return GlobalData.getInstance().getRecordInfo().getReport().getEndTime() == null;
    }

    public void startAlarm(int index, Alarm alarm) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        CustomAlarm customAlarm = CustomAlarm.getInstance();
        customAlarm.ringtone = RingtoneManager.getRingtone(context, notification);
        customAlarm.ringtone.play();
        disableOldAlarm(index);
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("name", alarm.name);
        intent2.putExtra("hour", String.valueOf(alarm.hour));
        intent2.putExtra("minute", String.valueOf(alarm.minute));
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

    private int findAlarmIndex(String name, String hour, String minute) {
        int count = alarmAdapter.getCount();
        for (int i = 0; i <= count; i++) {
            Alarm alarm = alarmAdapter.getItem(i);
            if (alarm.name.equals(name)) {
                if (alarm.hour == Integer.parseInt(hour) && alarm.minute == Integer.parseInt(minute)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void disableOldAlarm(int i) {
        Alarm alarm = alarmAdapter.getItem(i);
        alarmAdapter.disableAlarm(i);
        alarm.isActive = false;
        alarmAdapter.notifyDataSetChanged();
        return;
    }

    public Calendar transformAlarmToCalendar(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, alarm.hour);
        calendar.set(Calendar.MINUTE, alarm.minute);
        return calendar;
    }
}

package com.example.proiecttppa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private static AlarmAdapter mInstance;
    private static ArrayList<Alarm> alarms;
    private static ArrayList<PendingIntent> pendingIntents;
    private static ArrayList<Calendar> calendars;
    private static AlarmManager alarmManager;


    protected AlarmAdapter(Context context1) {
        super(context1, 0, alarms);
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    }


    public static synchronized AlarmAdapter getInstance(Context context1) {
        if (null == mInstance) {
            alarms = new ArrayList<>();
            pendingIntents = new ArrayList<>();
            calendars = new ArrayList<>();
            mInstance = new AlarmAdapter(context1);
        }
        return mInstance;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Alarm alarm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        TextView firstRow = convertView.findViewById(R.id.firstLine);
        TextView secondRow = convertView.findViewById(R.id.secondLine);
        Switch onOfSwitch = convertView.findViewById(R.id.switchId);

        onOfSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(position).isActive = isChecked;
                if(isChecked){
                    enableAlarm(position);
                }else{
                    disableAlarm(position);
                }
            }
        });
        //Handle buttons and add onClickListeners
        ImageButton callbtn = convertView.findViewById(R.id.rowButton);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println(position);
                remove(getItem(position));
            }
        });


        firstRow.setText(alarm.name);
        secondRow.setText(alarm.hour + ":" + alarm.minute);
        onOfSwitch.setChecked(alarm.isActive);
        return convertView;
    }

    @Override
    public void add(@Nullable Alarm object) {
        super.add(object);
        createAlarm(object);
    }

    @Override
    public void remove(@Nullable Alarm object) {
        removeAlarm(object);
        super.remove(object);
    }

    private void createAlarm(@Nullable Alarm object) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, object.hour);
        cal.set(Calendar.MINUTE, object.minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        pendingIntents.add(pendingIntent);
        calendars.add(cal);
        if(object.isActive)
            enableAlarm(getPosition(object));
    }

    private void removeAlarm(@Nullable Alarm object) {
        int position = getPosition(object);
        disableAlarm(position);
        pendingIntents.remove(position);
        calendars.remove(position);
    }


    private void disableAlarm(int position) {
        alarmManager.cancel(pendingIntents.get(position));
    }

    private void enableAlarm(int position) {
        Calendar cal = calendars.get(position);
        PendingIntent pendingIntent = pendingIntents.get(position);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
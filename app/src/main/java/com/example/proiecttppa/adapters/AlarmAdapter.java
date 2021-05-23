package com.example.proiecttppa.adapters;

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

import com.example.proiecttppa.AlarmReceiver;
import com.example.proiecttppa.R;
import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.helpers.TinyDB;
import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private static AlarmAdapter mInstance;
    private TinyDB tinydb;
    private ArrayList<PendingIntent> pendingIntents;
    private ArrayList<Calendar> calendars;
    private AlarmManager alarmManager;
    private boolean isLoading;


    protected AlarmAdapter() {
        super(GlobalData.getInstance().getContext(), 0, new ArrayList<Alarm>());
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        tinydb = new TinyDB(getContext());
        pendingIntents = new ArrayList<>();
        calendars = new ArrayList<>();
        loadFromMemory();
    }


    public static synchronized AlarmAdapter getInstance() {
        if (null == mInstance) {

            mInstance = new AlarmAdapter();
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
                if (isChecked) {
                    enableAlarm(position);
                } else {
                    disableAlarm(position);
                }
                saveToMemory();
            }
        });

        Switch smartAlarmSwitch = convertView.findViewById(R.id.smartAlarmId);
        smartAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(position).isSmartAlarm = isChecked;
                saveToMemory();
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
        smartAlarmSwitch.setChecked(alarm.isSmartAlarm);
        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        saveToMemory();
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
        saveToMemory();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        saveToMemory();
    }

    public void saveToMemory() {
        if(!isLoading) {
            int count = this.getCount();
            ArrayList<Object> alarmArrayList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                alarmArrayList.add(this.getItem(i));
            }
            tinydb.putListObject("Alarms", alarmArrayList);
        }
    }

    private ArrayList<Alarm> loadFromMemory() {
        isLoading = true;
        ArrayList<Object> objects = tinydb.getListObject("Alarms", Alarm.class);
        ArrayList<Alarm> alarms = new ArrayList<>();
        for (Object obj :
                objects) {
            this.add((Alarm) obj);
        }
        isLoading = false;
        return alarms;
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
        System.out.println("Is it today? : " + checkIfTheAlarmItsToday(object));
        Calendar cal = Calendar.getInstance();
//        if (!checkIfTheAlarmItsToday(object))
//            cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, object.hour);
        cal.set(Calendar.MINUTE, object.minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("hour", String.valueOf(object.hour));
        intent.putExtra("minute", String.valueOf(object.minute));
        intent.putExtra("name", String.valueOf(object.name));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), new Random().nextInt(), intent, 0);
        pendingIntents.add(pendingIntent);
        calendars.add(cal);
        if (object.isActive)
            enableAlarm(getPosition(object));
    }

    boolean checkIfTheAlarmItsToday(Alarm alarm) {
        Calendar rightNow = Calendar.getInstance();
        int current_hour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        int current_minute = rightNow.get(Calendar.MINUTE);
        if (current_hour < alarm.hour)
            return true;
        if (current_hour == alarm.hour)
            if (current_minute < alarm.minute)
                return true;
        return false;
    }

    private void removeAlarm(@Nullable Alarm object) {
        int position = getPosition(object);
        disableAlarm(position);
        pendingIntents.remove(position);
        calendars.remove(position);
    }

    private void disableAlarm(Alarm alarm) {
        disableAlarm(getPosition(alarm));
    }

    public void disableAlarm(int position) {
        alarmManager.cancel(pendingIntents.get(position));
    }

    private void enableAlarm(Alarm alarm) {
        disableAlarm(getPosition(alarm));
    }

    public void enableAlarm(int position) {
        Calendar cal = calendars.get(position);
        cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        if (!checkIfTheAlarmItsToday(getItem(position)))
            cal.add(Calendar.DAY_OF_YEAR, 1);
        PendingIntent pendingIntent = pendingIntents.get(position);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    public ArrayList<PendingIntent> getPendingIntents() {
        return pendingIntents;
    }
}
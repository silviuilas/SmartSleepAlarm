package com.example.proiecttppa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.proiecttppa.R;
import com.example.proiecttppa.helpers.TimePickerFragment;
import com.example.proiecttppa.interfaces.CustomDialogInterface;

public class AlarmCreateActivity extends FragmentActivity {
    int hour = -1;
    int minute = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
    }


    public void showTimePickerDialog(View view) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setCustomDialogInterface(new CustomDialogInterface() {
            @Override
            public void okButtonClicked(int _hour, int _minute) {
                hour = _hour;
                minute = _minute;
                TextView selectedTime = findViewById(R.id.selected_time);
                String _minute_changed;
                if (minute < 10)
                    _minute_changed = "0" + String.valueOf(minute);
                else
                    _minute_changed = "" + minute;

                selectedTime.setText("   " + hour + ":" + _minute_changed);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void saveAlarm(View view) {
        Intent returnIntent = new Intent();
        EditText selectedName = findViewById(R.id.alarm_name);
        String selectedNameText = selectedName.getText().toString();
        if (selectedNameText.equals(""))
            selectedNameText = "Default name";
        if (hour == -1 || minute == -1) {
            return;
        }
        returnIntent.putExtra("hour", String.valueOf(hour));
        returnIntent.putExtra("minute", String.valueOf(minute));
        returnIntent.putExtra("name", selectedNameText);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}

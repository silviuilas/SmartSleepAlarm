package com.example.proiecttppa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AlarmActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void stopAlarm(View view) {
        CustomAlarm customAlarm = CustomAlarm.getInstance();
        customAlarm.ringtone.stop();
        this.finish();
    }


}

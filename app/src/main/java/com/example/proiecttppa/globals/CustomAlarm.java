package com.example.proiecttppa.globals;

import android.media.Ringtone;

public class CustomAlarm {
    public Ringtone ringtone= null;
    private static CustomAlarm mInstance;

    protected CustomAlarm(){}

    public static synchronized CustomAlarm getInstance() {
        if(null == mInstance){
            mInstance = new CustomAlarm();
        }
        return mInstance;
    }
}
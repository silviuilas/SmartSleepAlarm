package com.example.proiecttppa;

import android.media.Ringtone;

public class CustomAlarm {
    public Ringtone ringtone= null;
    private static CustomAlarm mInstance;

    public int someValueIWantToKeep;

    protected CustomAlarm(){}

    public static synchronized CustomAlarm getInstance() {
        if(null == mInstance){
            mInstance = new CustomAlarm();
        }
        return mInstance;
    }
}
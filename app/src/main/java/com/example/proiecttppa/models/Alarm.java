package com.example.proiecttppa.models;

public class Alarm extends Object {
    public String name;
    public int hour;
    public int minute;
    public boolean isActive;
    public boolean isSmartAlarm;

    public Alarm(String name, int hour, int minute, boolean isActive, boolean isSmartAlarm) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
        this.isSmartAlarm = isSmartAlarm;
    }


    @Override
    public String toString() {
        return "Alarm{" +
                "name='" + name + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", isActive=" + isActive +
                ", isSmartAlarm=" + isSmartAlarm +
                '}';
    }
}

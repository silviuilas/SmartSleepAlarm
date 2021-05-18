package com.example.proiecttppa.models;

public class Alarm {
    public String name;
    public int hour;
    public int minute;
    public boolean isActive;

    public Alarm(String name, int hour, int minute, boolean isActive) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "name='" + name + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

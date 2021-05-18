package com.example.proiecttppa.models;

import java.util.ArrayList;
import java.util.Calendar;

public class Report {
    private ArrayList<Double> SoundILevelsInfo = new ArrayList<>();
    private Calendar startTime;
    private Calendar endTime;

    public Report() {
    }

    public Report(Calendar startTime, Calendar endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ArrayList<Double> getSoundILevelsInfo() {
        return SoundILevelsInfo;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}

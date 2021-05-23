package com.example.proiecttppa.models;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;

public class Report extends Object{
    private ArrayList<Double> SoundILevelsInfo = new ArrayList<>();
    private ArrayList<Pair<Calendar, Double>> MovementLevelsInfo = new ArrayList<>();
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

    public ArrayList<Pair<Calendar, Double>> getMovementLevelsInfo() {
        return MovementLevelsInfo;
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

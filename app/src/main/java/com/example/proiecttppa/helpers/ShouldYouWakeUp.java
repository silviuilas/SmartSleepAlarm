package com.example.proiecttppa.helpers;

import android.util.Pair;

import com.example.proiecttppa.models.Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ShouldYouWakeUp {
    Report report;
    int average_time_in_minutes = 2;
    double sensibility = 0.5;

    public ShouldYouWakeUp(Report report) {
        this.report = report;
    }

    public boolean shouldIWakeUp() {
        ArrayList<Double> soundSorted = sortSoundLevels();
        ArrayList<Double> movementSorted = sortMovementLevels();
        double avg_sound = getAverageSound();
        double median_sound = soundSorted.get((int) (soundSorted.size() * sensibility));
        if (avg_sound > median_sound)
            return true;
        double avg_motion = getAverageMotion();
        double median_motion = movementSorted.get((int) (soundSorted.size() * sensibility));
        if (avg_motion > median_motion) {
            return true;
        }
        return false;
    }

    public ArrayList<Double> sortSoundLevels() {
        ArrayList<Double> arrayList = new ArrayList<>(report.getSoundILevelsInfo());
        Collections.sort(arrayList);
        return arrayList;
    }

    public ArrayList<Double> sortMovementLevels() {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (Pair<Calendar, Double> elem :
                report.getMovementLevelsInfo()) {
            arrayList.add(elem.second);
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public Double getAverageSound() {
        ArrayList<Double> arrayList = new ArrayList<>(report.getSoundILevelsInfo());
        long startTime = report.getStartTime().getTimeInMillis();
        long endTime = Calendar.getInstance().getTimeInMillis();
        long diff = endTime - startTime;
        int size = arrayList.size();
        double step = diff / (size - 1);
        long average_time_in_mili = average_time_in_minutes * 60 * 1000;
        average_time_in_mili = Math.min(average_time_in_mili, endTime - startTime);
        long x = endTime - average_time_in_mili;
        double soundSum = 0;
        int i_avg_start = (int) (((endTime - startTime) - average_time_in_mili) / step);
        for (int i_avg = i_avg_start; i_avg < size; i_avg++) {
            x += step;
            if (x > endTime - (1000 * average_time_in_mili)) {
                soundSum += arrayList.get(i_avg);
            }
        }
        return soundSum / (size - i_avg_start);
    }

    public Double getAverageMotion() {
        ArrayList<Double> arrayList = new ArrayList<>();
        long startTime = report.getStartTime().getTimeInMillis();
        long endTime = Calendar.getInstance().getTimeInMillis();
        long average_time_in_mili = average_time_in_minutes * 60 * 1000;
        average_time_in_mili = Math.min(average_time_in_mili, endTime - startTime);
        long x = endTime - average_time_in_mili;
        double avgMotionSum = 0;
        int counter = 0;
        for (Pair<Calendar, Double> elem :
                report.getMovementLevelsInfo()) {
            if (elem.first.getTimeInMillis() > x) {
                avgMotionSum += elem.second;
                counter++;
            }
        }
        return avgMotionSum / counter;
    }

}

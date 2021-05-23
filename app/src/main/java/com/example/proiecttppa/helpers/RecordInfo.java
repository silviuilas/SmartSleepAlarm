package com.example.proiecttppa.helpers;

import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.adapters.SleepRecordsAdapter;
import com.example.proiecttppa.models.Report;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class RecordInfo {
    private Timer timer = new Timer();
    final SoundMeter soundMeter = new SoundMeter();
    private Report report;
    private MovementDetector movementDetector;

    public RecordInfo(){
    }


    public void startRecording() {
        report = new Report();
        movementDetector = new MovementDetector(report);
        movementDetector.start();
        timer = new Timer();
        report.setStartTime(Calendar.getInstance());
        soundMeter.start();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                report.getSoundILevelsInfo().add(soundMeter.getAmplitude());
                System.out.println(soundMeter.getAmplitude());
            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second
    }

    public void stopRecording() {
        movementDetector.stop();

        soundMeter.stop();
        timer.cancel();
        report.setEndTime(Calendar.getInstance());
        SleepRecordsAdapter.getInstance(GlobalData.getInstance().getContext()).add(report);
    }

    public Report getReport() {
        return report;
    }
}

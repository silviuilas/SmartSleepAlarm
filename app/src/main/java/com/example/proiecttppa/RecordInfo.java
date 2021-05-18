package com.example.proiecttppa;

import com.example.proiecttppa.models.Report;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class RecordInfo {
    private static RecordInfo mInstance = new RecordInfo();
    private static Timer timer = new Timer();
    final static SoundMeter soundMeter = new SoundMeter();
    private static Report report;


    protected RecordInfo() {
    }

    public static synchronized RecordInfo getInstance() {
        if (null == mInstance) {
            mInstance = new RecordInfo();
        }
        return mInstance;
    }

    public static void startRecording() {
        report = new Report();
        timer = new Timer();
        report.setStartTime(Calendar.getInstance());
        soundMeter.start();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                report.getSoundILevelsInfo().add(soundMeter.getAmplitude());
                System.out.println(soundMeter.getAmplitude());
            }
        }, 0, 100);//put here time 1000 milliseconds=1 second
    }

    public static void stopRecording() {
        soundMeter.stop();
        timer.cancel();
        report.setEndTime(Calendar.getInstance());
    }

    public static Report getReport() {
        return report;
    }
}

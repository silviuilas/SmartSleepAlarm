package com.example.proiecttppa.globals;

import com.example.proiecttppa.SoundMeter;
import com.example.proiecttppa.models.Report;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class RecordInfo {
    private static RecordInfo mInstance = new RecordInfo();
    private Timer timer = new Timer();
    final SoundMeter soundMeter = new SoundMeter();
    private Report report;
    private MovementDetector movementDetector = MovementDetector.getInstance();

    protected RecordInfo() {
    }

    public static synchronized RecordInfo getInstance() {
        if (null == mInstance) {
            mInstance = new RecordInfo();
        }
        return mInstance;
    }

    public void startRecording() {
        movementDetector.start();
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

    public void stopRecording() {
        movementDetector.stop();
        soundMeter.stop();
        timer.cancel();
        report.setEndTime(Calendar.getInstance());
    }

    public Report getReport() {
        return report;
    }
}

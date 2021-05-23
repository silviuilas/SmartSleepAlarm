package com.example.proiecttppa.helpers;


// Credits to https://stackoverflow.com/questions/14574879/how-to-detect-movement-of-an-android-device

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.Pair;

import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.models.Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class MovementDetector implements SensorEventListener {

    protected final String TAG = getClass().getSimpleName();

    private SensorManager sensorMan;
    private Sensor accelerometer;
    private Report report;


    public MovementDetector(Report report1) {
        init();
        report = report1;
    }


    //////////////////////
    private HashSet<Listener> mListeners = new HashSet<>();

    private void init() {
        sensorMan = (SensorManager) GlobalData.getInstance().getContext().getSystemService(Context.SENSOR_SERVICE);
        sensorMan.unregisterListener(this);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    public void start() {
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorMan.unregisterListener(this);
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
     */
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private final int SAMPLE_SIZE = 1; // change this sample size as you want, higher is more precise but slow measure.
    private final double THRESHOLD = 0.2; // change this threshold as you want, higher is more spike movement


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            double x = mGravity[0];
            double y = mGravity[1];
            double z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = (float) (mAccel * 0.9f + delta);

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                Log.d(TAG, "The value is " + String.valueOf(hitResult));
  ;
                report.getMovementLevelsInfo().add(new Pair(Calendar.getInstance(), hitResult));
                if (hitResult > THRESHOLD) {
                    //Log.d(TAG, "Walking");
                } else {
                    //Log.d(TAG, "Stop Walking");
                }
                for (Listener listener : mListeners) {
                    listener.onMotionDetected(event, hitResult);
                }

                hitCount = 0;
                hitSum = 0;
                hitResult = 0;

            }
        }

    }

    /* (non-Javadoc)
     * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public interface Listener {
        void onMotionDetected(SensorEvent event, double acceleration);
    }
}

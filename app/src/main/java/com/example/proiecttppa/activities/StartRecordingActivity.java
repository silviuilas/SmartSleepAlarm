package com.example.proiecttppa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.proiecttppa.R;
import com.example.proiecttppa.RecordInfo;
import com.example.proiecttppa.SleepRecordsAdapter;

public class StartRecordingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_recording);
    }

    public void stopRecording(View view) {
        RecordInfo.stopRecording();
        generateRecord();
        finish();
    }

    private void generateRecord() {
        SleepRecordsAdapter.getInstance(this).add(RecordInfo.getReport());
    }
}

package com.example.proiecttppa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.proiecttppa.R;
import com.example.proiecttppa.globals.GlobalData;
import com.example.proiecttppa.helpers.RecordInfo;

public class StartRecordingActivity extends Activity {
    RecordInfo recordInfo = GlobalData.getInstance().getRecordInfo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_recording);
        recordInfo.startRecording();
    }

    public void stopRecording(View view) {
        recordInfo.stopRecording();
        finish();
    }
}

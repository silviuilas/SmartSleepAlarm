package com.example.proiecttppa.globals;

import android.content.Context;

import com.example.proiecttppa.helpers.RecordInfo;

public class GlobalData {
    private static GlobalData mInstance = new GlobalData();
    public Context context;
    public RecordInfo recordInfo = new RecordInfo();

    protected GlobalData() {
    }

    public static synchronized GlobalData getInstance() {
        if (null == mInstance) {
            mInstance = new GlobalData();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setRecordInfo(RecordInfo recordInfo) {
        this.recordInfo = recordInfo;
    }

    public RecordInfo getRecordInfo() {
        return recordInfo;
    }
}

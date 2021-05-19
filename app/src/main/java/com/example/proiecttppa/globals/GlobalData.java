package com.example.proiecttppa.globals;

import android.content.Context;

public class GlobalData {
    private static GlobalData mInstance = new GlobalData();
    public Context context;

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
}

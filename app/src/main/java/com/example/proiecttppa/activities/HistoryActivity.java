package com.example.proiecttppa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.proiecttppa.R;
import com.example.proiecttppa.adapters.SleepRecordsAdapter;

public class HistoryActivity extends Activity {
    ListView listView;
    SleepRecordsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        adapter = SleepRecordsAdapter.getInstance(this);
//        if (adapter.getCount() == 0)
//            for (int i = 0; i < 10; i++) {
//                adapter.add(new Report(Calendar.getInstance(), Calendar.getInstance()));
//            }

        listView = findViewById(R.id.history_list_view);
        listView.setAdapter(adapter);
    }
}

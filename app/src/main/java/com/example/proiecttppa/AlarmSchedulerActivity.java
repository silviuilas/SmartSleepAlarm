package com.example.proiecttppa;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;

public class AlarmSchedulerActivity extends Activity implements AdapterView.OnItemClickListener {
    ArrayList<Alarm> alarmArrayList = new ArrayList<>();
    ListView listView;
    AlarmAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scheduler);
        for (int i = 0; i < 10; i++) {
            alarmArrayList.add(new Alarm("Trezirea iubiri", "12:24", true));
        }

        listView = findViewById(R.id.list_view);
        adapter = new AlarmAdapter(this, alarmArrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
//        Intent intent = new Intent();
//        intent.setClass(this, ListItemDetail.class);
//        intent.putExtra("position", position);
//        // Or / And
//        intent.putExtra("id", id);
//        startActivity(intent);
    }
}

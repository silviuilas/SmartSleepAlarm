package com.example.proiecttppa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.proiecttppa.AlarmAdapter;
import com.example.proiecttppa.R;
import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;

public class AlarmSchedulerActivity extends Activity implements AdapterView.OnItemClickListener {
    ArrayList<Alarm> alarmArrayList = new ArrayList<>();
    ListView listView;
    AlarmAdapter adapter;

    int LAUNCH_CREATE_ACTIVITY = 123456;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_scheduler);
        adapter = AlarmAdapter.getInstance(this);
//        if (adapter.getCount() == 0)
//            for (int i = 0; i < 10; i++) {
//                adapter.add(new Alarm("Trezirea lepra", 12, 24, true));
//            }

        listView = findViewById(R.id.list_view);
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

    public void createAlarm(View view) {
        Intent myIntent = new Intent(AlarmSchedulerActivity.this, AlarmCreateActivity.class);
        AlarmSchedulerActivity.this.startActivityForResult(myIntent, LAUNCH_CREATE_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_CREATE_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String hour = data.getStringExtra("hour");
                String minute = data.getStringExtra("minute");
                String name = data.getStringExtra("name");
                adapter.add(new Alarm(name, Integer.parseInt(hour), Integer.parseInt(minute), true));
                System.out.println("Scheduler received " + hour + ":" + minute + " with name " + name);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onA
}

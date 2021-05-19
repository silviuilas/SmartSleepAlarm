package com.example.proiecttppa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.proiecttppa.R;
import com.example.proiecttppa.adapters.SleepRecordsAdapter;
import com.example.proiecttppa.models.Report;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecordPageActivity extends Activity {
    LineGraphSeries<DataPoint> soundSeries;
    LineGraphSeries<DataPoint> movementSeries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        TextView reportDate = findViewById(R.id.report_date);
        TextView reportFrom = findViewById(R.id.report_from);
        TextView reportTo = findViewById(R.id.report_to);
        reportDate.setText(date);
        reportFrom.setText(from);
        reportTo.setText(to);

        int position = intent.getIntExtra("position", -1);

        if (position == -1) {
            System.out.println("Something is wrong");
        } else {
            GraphView soundGraphView = findViewById(R.id.sound_graph);
            soundGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        Format formatter = new SimpleDateFormat("HH:mm");
                        return formatter.format(value);
                    }
                    return super.formatLabel(value, isValueX);
                }
            });
            soundSeries = new LineGraphSeries<>();

            SleepRecordsAdapter sleepRecordsAdapter = SleepRecordsAdapter.getInstance(this);
            Report report = sleepRecordsAdapter.getItem(position);
            ArrayList<Double> arrayList = report.getSoundILevelsInfo();
            long startTime = report.getStartTime().getTimeInMillis();
            long endTime = report.getEndTime().getTimeInMillis();
            long diff = endTime - startTime;
            int size = arrayList.size();
            long x = startTime;
            for (int i = 0; i < size; i++) {
                x += diff / size;
                double y = arrayList.get(i);
                soundSeries.appendData(new DataPoint(x, y), true, size);
            }
            soundGraphView.addSeries(soundSeries);

            GraphView movementGraphView = findViewById(R.id.movement_graph);

            movementGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        Format formatter = new SimpleDateFormat("HH:mm");
                        return formatter.format(value);
                    }
                    return super.formatLabel(value, isValueX);
                }
            });
            movementSeries = new LineGraphSeries<>();
            ArrayList<Pair<Calendar, Double>> movementArrayList = report.getMovementLevelsInfo();

            size = movementArrayList.size();
            for (int i = 0; i < size; i++) {
                x = movementArrayList.get(i).first.getTimeInMillis();
                double y = movementArrayList.get(i).second;
                movementSeries.appendData(new DataPoint(x, y), true, size);
            }
            movementGraphView.addSeries(movementSeries);
        }

    }
}

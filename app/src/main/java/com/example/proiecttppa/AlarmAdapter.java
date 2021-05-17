package com.example.proiecttppa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    public AlarmAdapter(Context context1, ArrayList<Alarm> sensors) {
        super(context1, 0, sensors);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Alarm alarm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        TextView firstRow = (TextView) convertView.findViewById(R.id.firstLine);
        TextView secondRow = (TextView) convertView.findViewById(R.id.secondLine);
        Switch onOfSwitch = convertView.findViewById(R.id.switchId);

        firstRow.setText(alarm.name);
        secondRow.setText(alarm.values);
        onOfSwitch.setChecked(alarm.isActive);
        return convertView;
    }



}
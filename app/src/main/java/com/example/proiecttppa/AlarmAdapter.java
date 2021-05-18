package com.example.proiecttppa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiecttppa.models.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<Alarm> {


    public AlarmAdapter(Context context1, ArrayList<Alarm> alarms) {
        super(context1, 0, alarms);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Alarm alarm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        TextView firstRow = convertView.findViewById(R.id.firstLine);
        TextView secondRow = convertView.findViewById(R.id.secondLine);
        Switch onOfSwitch = convertView.findViewById(R.id.switchId);

        //Handle buttons and add onClickListeners
        ImageButton callbtn= convertView.findViewById(R.id.rowButton);

        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                System.out.println(position);
                remove(getItem(position));
            }
        });


        firstRow.setText(alarm.name);
        secondRow.setText(alarm.values);
        onOfSwitch.setChecked(alarm.isActive);
        return convertView;
    }



}
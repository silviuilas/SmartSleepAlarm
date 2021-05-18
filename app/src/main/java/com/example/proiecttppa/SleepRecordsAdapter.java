package com.example.proiecttppa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiecttppa.models.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SleepRecordsAdapter extends ArrayAdapter<Report> {
    private static SleepRecordsAdapter mInstance;


    protected SleepRecordsAdapter(Context context) {
        super(context, 0, new ArrayList<Report>());
    }

    public static synchronized SleepRecordsAdapter getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SleepRecordsAdapter(context);
        }
        return mInstance;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Report report = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.report_row_layout, parent, false);
        }
        TextView reportDate = convertView.findViewById(R.id.report_date);
        TextView reportFrom = convertView.findViewById(R.id.report_from);
        TextView reportTo = convertView.findViewById(R.id.report_to);
        //Handle buttons and add onClickListeners
        ImageButton callbtn = convertView.findViewById(R.id.rowButton);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println(position);
                remove(getItem(position));
            }
        });


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String stringDate = dateFormat.format(report.getStartTime().getTime());
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("K:mm a");
        String stringFrom = hourMinuteFormat.format(report.getStartTime().getTime());
        String stringTo = hourMinuteFormat.format(report.getEndTime().getTime());
        reportDate.setText(stringDate);
        reportFrom.setText(stringFrom);
        reportTo.setText(stringTo);
        return convertView;
    }

}

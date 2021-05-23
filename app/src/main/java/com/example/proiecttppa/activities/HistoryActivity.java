package com.example.proiecttppa.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amar.library.ui.StickyScrollView;
import com.example.proiecttppa.R;
import com.example.proiecttppa.adapters.SleepRecordsAdapter;

public class HistoryActivity extends Activity {
    private static final int HIDE_THRESHOLD = 0;
    ListView listView;
    SleepRecordsAdapter adapter;
    LinearLayout titleLayout;
    TextView titleText;
    private int scrolledDistance = 0;
    private int lastScrolledValue = 0;
    private boolean controlsVisible = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        adapter = SleepRecordsAdapter.getInstance(this);

        titleLayout = findViewById(R.id.titleLayout);
        titleText = findViewById(R.id.titleText);
        final StickyScrollView stickyScrollview = findViewById(R.id.stickScrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            stickyScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    scrolledDistance = stickyScrollview.getScrollY();
                    int diff = lastScrolledValue - stickyScrollview.getScrollY();
                    System.out.println(scrolledDistance + " " + diff);
                    if (hideLayout(diff))
                        lastScrolledValue = stickyScrollview.getScrollY();
                }
            });
        }

        listView = findViewById(R.id.history_list_view);
        listView.setAdapter(adapter);
    }

    private boolean hideLayout(int scrollValue) {
        ViewGroup.LayoutParams layoutParams = titleLayout.getLayoutParams();
        layoutParams.height = layoutParams.height + scrollValue;
        if (titleText.getHeight() >= layoutParams.height) {
            layoutParams.height = titleLayout.getHeight();
            titleLayout.setLayoutParams(layoutParams);
            return false;
        }
        titleLayout.setLayoutParams(layoutParams);
        return true;
    }

    private void hideViews() {

        //titleLayout.animate().translationY(-titleLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        //titleLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

}

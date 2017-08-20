package com.wuli.flabbergast;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final SimpleDateFormat MINUTES_SECONDS = new SimpleDateFormat("m:ss");

    private List<TextView> textViewsInGrid;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewsInGrid = new ArrayList<>();


        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(0, 0, 10));
            tableRow.setGravity(Gravity.CENTER);
            for (int j = 0; j < 4; j++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
                textView.setBackgroundResource(R.drawable.dice_background);
                tableRow.addView(textView);
                textViewsInGrid.add(textView);
            }
        }
        refreshGrid();
    }

    public void refreshGrid(View view) {
        refreshGrid();

    }

    private void refreshGrid() {
        List<String> grid = GridGenerator.generateGrid();
        for (int i = 0; i < 16; i++) {
            textViewsInGrid.get(i).setText(grid.get(i));

        }

        final TextView textView = (TextView) findViewById(R.id.countdown_timer);
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(1000 * 5, 100) {

            public void onTick(long millisUntilFinished) {
                // Add 999ms to make countdown timer smoother from the start, also avoids timer
                // going to 0:00 before "Pens down"
                textView.setText(MINUTES_SECONDS.format(new Date(millisUntilFinished + 999)));

            }

            public void onFinish() {
                textView.setText("Pens down!");
            }
        };
        timer.start();

    }
}

package com.wuli.flabbergast;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final SimpleDateFormat MINUTES_SECONDS = new SimpleDateFormat("m:ss");

    private List<TextView> textViewsInGrid;
    private ArrayList<String> gridLetters;
    private CountDownTimer timer;
    private long millisLeft;
    boolean warningBeepSent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewsInGrid = new ArrayList<>();


        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 10));
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

        if (savedInstanceState != null) {
            gridLetters = savedInstanceState.getStringArrayList("grid");
            millisLeft = savedInstanceState.getLong("timer");
            warningBeepSent = savedInstanceState.getBoolean("warningBeepSent");
            fillInGridAndTimer();
        } else {
            createNewGrid();
        }
    }

    public void refreshGrid(View view) {
        createNewGrid();
    }

    private void createNewGrid() {
        gridLetters = GridGenerator.generateGrid();
        millisLeft = 1000 * 60 * 3;
        warningBeepSent = false;

        fillInGridAndTimer();
    }

    private void fillInGridAndTimer() {
        for (int i = 0; i < 16; i++) {
            textViewsInGrid.get(i).setText(gridLetters.get(i));
        }

        final TextView textView = (TextView) findViewById(R.id.countdown_timer);
        if (timer != null) {
            timer.cancel();
        }
        if (millisLeft == 0) {

            textView.setText("Pens down!");

        } else {
            timer = new CountDownTimer(millisLeft, 100) {


                public void onTick(long millisUntilFinished) {
                    millisLeft = millisUntilFinished;
                    // Add 999ms to make countdown timer smoother from the start, also avoids timer
                    // going to 0:00 before "Pens down"
                    textView.setText(MINUTES_SECONDS.format(new Date(millisUntilFinished + 999)));

                    if (millisUntilFinished < 1000 * 30 && !warningBeepSent) {
                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                        warningBeepSent = true;
                    }

                }

                public void onFinish() {
                    millisLeft = 0;
                    textView.setText("Pens down!");

                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);

                }
            };
            timer.start();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("grid", gridLetters);
        outState.putLong("timer", millisLeft);
        outState.putBoolean("warningBeepSent", warningBeepSent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }
    }
}

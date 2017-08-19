package com.wuli.flabbergast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void refreshGrid(View view) {
        List<String> grid = GridGenerator.generateGrid();
        TextView gridAsText = (TextView) findViewById(R.id.gridAsText);
        String displayString = "";
        for (String letter : grid) {
            displayString = displayString + letter;
        }

        gridAsText.setText(displayString);
    }
}

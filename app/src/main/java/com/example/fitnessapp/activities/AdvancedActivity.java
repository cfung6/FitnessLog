package com.example.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessapp.R;

public class AdvancedActivity extends AppCompatActivity {

    private double startingBenchWeight;
    private double startingOverheadWeight;
    private double startingSquatWeight;
    private double startingDeadliftWeight;
    private double startingBarbellRowWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        Intent intent = getIntent();
        startingBenchWeight = intent.getDoubleExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getDoubleExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getDoubleExtra ("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getDoubleExtra ("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getDoubleExtra ("BARBELL_ROW_WEIGHT", -1);

        TextView tv = findViewById(R.id.textView1);

        tv.setText(startingBenchWeight+"\n"+startingOverheadWeight+"\n"+startingSquatWeight+"\n"+startingDeadliftWeight+"\n"+startingBarbellRowWeight);
    }
}

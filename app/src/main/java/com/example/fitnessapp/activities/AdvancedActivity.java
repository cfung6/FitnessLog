package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;

public class AdvancedActivity extends AppCompatActivity {

    private int startingBenchWeight;
    private int startingOverheadWeight;
    private int startingSquatWeight;
    private int startingDeadliftWeight;
    private int startingBarbellRowWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        Intent intent = getIntent();
        startingBenchWeight = intent.getIntExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getIntExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getIntExtra("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getIntExtra("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getIntExtra("BARBELL_ROW_WEIGHT", -1);

    }
}

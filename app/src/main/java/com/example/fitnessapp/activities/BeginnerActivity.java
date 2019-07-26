package com.example.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Routine;
import com.example.fitnessapp.Workout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeginnerActivity extends AppCompatActivity {

    private double startingBenchWeight;
    private double startingOverheadWeight;
    private double startingSquatWeight;
    private double startingDeadliftWeight;
    private double startingBarbellRowWeight;

    private Routine beginnerRoutine;
    private Workout workoutA;
    private Workout workoutB;
    private Exercise benchPress;
    private Exercise overheadPress;
    private Exercise squat;
    private Exercise deadlift;
    private Exercise barbellRow;

    private FileOutputStream fileOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        Intent intent = getIntent();
        startingBenchWeight = intent.getDoubleExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getDoubleExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getDoubleExtra ("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getDoubleExtra ("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getDoubleExtra ("BARBELL_ROW_WEIGHT", -1);

        benchPress = new Exercise ("benchPress", getApplicationContext());
        overheadPress = new Exercise("overheadPress",getApplicationContext());
        squat = new Exercise ("squat", getApplicationContext());
        deadlift = new Exercise ("deadlift", getApplicationContext());
        barbellRow = new Exercise("barbellRow", getApplicationContext());

        benchPress.addWeightDone(startingBenchWeight, Arrays.asList(5,5,5));
        overheadPress.addWeightDone(startingOverheadWeight, Arrays.asList(5,5,5));
        squat.addWeightDone(startingSquatWeight, Arrays.asList(5,5,5));
        deadlift.addWeightDone(startingDeadliftWeight, Arrays.asList(5,5,5));
        barbellRow.addWeightDone(startingBarbellRowWeight, Arrays.asList(5,5,5));

        //For testing
        TextView tv = findViewById(R.id.textView1);
        benchPress.addWeightDone(500, Arrays.asList(10));
        tv.setText(benchPress.getActualMap().keySet() + "\n" + benchPress.getActualMap().values() + "\n" + benchPress.getGoalMap().keySet() + "\n" + benchPress.getGoalMap().values());
    }
}

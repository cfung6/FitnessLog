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

        benchPress = new Exercise ("benchPress", startingBenchWeight, 5, 1, Arrays.asList(5,5,5));
//        overheadPress = new Exercise("overheadPress",startingOverheadWeight, 5, 1, Arrays.asList(5,5,5));
//        squat = new Exercise ("squat", startingSquatWeight, 5, 1, Arrays.asList(5,5,5));
//        deadlift = new Exercise ("deadlift", startingDeadliftWeight, 5, 1, Arrays.asList(5,5,5));
//        barbellRow = new Exercise("barbellRow", startingBarbellRowWeight, 5, 1, Arrays.asList(5,5,5));

        //For testing
        benchPress.addRepsDone(75 ,5);
        benchPress.addRepsDone(75 ,5);
        benchPress.addRepsDone(70 ,5);

        benchPress.increaseWeight();

        TextView tv = findViewById(R.id.textView1);
        tv.setText("" + benchPress.getPass() + "\n" + benchPress.getGoalWeight());
    }
}

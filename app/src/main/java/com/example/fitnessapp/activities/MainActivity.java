package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.DatabaseHelper;
import com.example.fitnessapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private double[] exerciseCapableWeights;
    private List<String> exerciseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseNames = new ArrayList<>(Arrays.asList(
                "Bench Press",
                "Overhead Press",
                "Squat",
                "Deadlift",
                "Barbell Row"));

        exerciseCapableWeights = new double[exerciseNames.size()];
    }

    public void newProgramClicked(View view) {
        Intent intent = new Intent(this, NewProgramActivity.class);
        startActivity(intent);
    }

    public void continueProgramClicked(View view) {
        Intent intent;
        databaseHelper = new DatabaseHelper(this);
        boolean isEmpty = databaseHelper.isEmpty();

        //If database is empty, defaults to new program
        if (isEmpty) {
            intent = new Intent(this, NewProgramActivity.class);
            startActivity(intent);
        }

        int routineID = databaseHelper.getLatestRoutineID();

        try {
            if (routineID == 1) {
                intent = new Intent(this, BeginnerActivity.class);
                initializeWeight("BeginnerTable");
            } else if (routineID == 2) {
                intent = new Intent(this, IntermediateActivity.class);
                initializeWeight("IntermediateTable");
            } else if (routineID == 3) {
                intent = new Intent(this, AdvancedActivity.class);
                initializeWeight("AdvancedTable");
            } else {
                throw new IllegalArgumentException();
            }

            //Passing the capable weights to Beg/Int/Adv activities
            intent.putExtra("weights", exerciseCapableWeights);
            startActivity(intent);
        } catch (IllegalArgumentException e) {
            Log.d("TAG", "Invalid Routine ID found in SQL data table");
        }
    }

    // EFFECTS: initializes the Next Weight (i.e. goal weight) of all the exercises
    private void initializeWeight(String table) {
        for (int i = 0; i < exerciseNames.size(); i++) {
            exerciseCapableWeights[i] = databaseHelper.getExerciseCapableWeight(table, exerciseNames.get(i));
        }
    }
}

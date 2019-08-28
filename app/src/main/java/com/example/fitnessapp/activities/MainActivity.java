package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.DatabaseHelper;
import com.example.fitnessapp.R;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    double nextBenchWeight;
    double nextOverheadWeight;
    double nextSquatWeight;
    double nextDeadliftWeight;
    double nextBarbellRowWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newProgramClicked(View view) {
        Intent intent = new Intent(this, NewProgramActivity.class);
        startActivity(intent);
    }

    public void continueProgramClicked(View view) {
        Intent intent;
        databaseHelper = new DatabaseHelper(this);
        boolean isEmpty = databaseHelper.isEmpty();

        if (isEmpty) {
            intent = new Intent(this, NewProgramActivity.class);
            startActivity(intent);
        }

        int routineID = databaseHelper.getLatestRoutineID();

        //If database is empty, defaults to new program
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
            intent.putExtra("BENCH_PRESS_WEIGHT", nextBenchWeight);
            intent.putExtra("OVERHEAD_PRESS_WEIGHT", nextOverheadWeight);
            intent.putExtra("SQUAT_WEIGHT", nextSquatWeight);
            intent.putExtra("DEADLIFT_WEIGHT", nextDeadliftWeight);
            intent.putExtra("BARBELL_ROW_WEIGHT", nextBarbellRowWeight);
            startActivity(intent);
        } catch (IllegalArgumentException e) {
            Log.d("TAG", "Invalid Routine ID found in SQL data table");
        }
    }

    // EFFECTS: initializes the Next Weight (i.e. goal weight) of all the exercises
    private void initializeWeight(String table) {
        nextBenchWeight = databaseHelper.getExerciseCapableWeight(table, "Bench Press");
        nextOverheadWeight = databaseHelper.getExerciseCapableWeight(table, "Overhead Press");
        nextSquatWeight = databaseHelper.getExerciseCapableWeight(table, "Squat");
        nextDeadliftWeight = databaseHelper.getExerciseCapableWeight(table, "Deadlift");
        nextBarbellRowWeight = databaseHelper.getExerciseCapableWeight(table, "Barbell Row");
    }
}

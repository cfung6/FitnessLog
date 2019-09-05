package com.example.fitnesslog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.ExerciseNames;
import com.example.fitnesslog.R;

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
    private String[] exerciseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivity(new Intent(this, WorkoutCalendar.class));
                return true;
//            case R.id.graphs:
//                startActivity(new Intent(this, Graphs.class));
//                return true;
            case R.id.stopwatch:
                startActivity(new Intent(this, Stopwatch.class));
                return true;
            case R.id.exercise_tutorials:
                startActivity(new Intent(this, ExerciseTutorials.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                exerciseNames = ExerciseNames.BEGINNER_NAMES;
                initializeWeight("BeginnerTable");
            } else if (routineID == 2) {
                intent = new Intent(this, IntermediateActivity.class);
                exerciseNames = ExerciseNames.INTERMEDIATE_NAMES;
                initializeWeight("IntermediateTable");
            } else if (routineID == 3) {
                intent = new Intent(this, AdvancedActivity.class);
                exerciseNames = ExerciseNames.ADVANCED_NAMES;
                initializeWeight("AdvancedTable");
            } else {
                throw new IllegalArgumentException();
            }

            //Passing the capable weights and names to Beg/Int/Adv activities
            intent.putExtra("WEIGHTS", exerciseCapableWeights);
            intent.putExtra("NAMES", exerciseNames);

            startActivity(intent);
        } catch (IllegalArgumentException e) {
            Log.d("TAG", "Invalid Routine ID found in SQL data table");
        }
    }

    public void aboutClicked(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    // EFFECTS: initializes the capable weight of all the exercises
    private void initializeWeight(String table) {
        exerciseCapableWeights = new double[exerciseNames.length];
        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseCapableWeights[i] = databaseHelper.getExerciseCapableWeight(table, exerciseNames[i]);
        }
    }
}

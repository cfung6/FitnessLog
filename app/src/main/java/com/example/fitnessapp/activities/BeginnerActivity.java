package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Routine;
import com.example.fitnessapp.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

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

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        TextView dateView = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dateView.setText(full);

        Intent intent = getIntent();
        startingBenchWeight = intent.getDoubleExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getDoubleExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getDoubleExtra ("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getDoubleExtra ("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getDoubleExtra ("BARBELL_ROW_WEIGHT", -1);

        initializeExercises();
        initializeWorkouts();
        initializeRoutine();

        TextView exerciseOneView = findViewById(R.id.exercise_one);
        Exercise currentExercise = beginnerRoutine.getCurrentWorkout().getCurrentExercise();
        exerciseOneView.setText(currentExercise.getName());

        TextView exerciseOneWeightView = findViewById(R.id.exercise_one_weight);
        exerciseOneWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseOneSetView = findViewById(R.id.exercise_one_set);
        exerciseOneSetView.setText("Sets:" + currentExercise.getGoalReps().size());

        TextView exerciseOneRepsView = findViewById(R.id.exercise_one_reps);
        exerciseOneRepsView.setText("Reps: " + currentExercise.getGoalReps().get(0));



//        //For testing
//        benchPress.addRepsDone(100 ,5);
//        benchPress.addRepsDone(100 ,5);
//        benchPress.addRepsDone(100 ,5);
//
//        benchPress.increaseWeight();
//
//        TextView tv = findViewById(R.id.textView1);
//        tv.setText("" + benchPress.getPass() + "\n" + benchPress.getGoalWeight());
    }

    private void initializeExercises() {
        benchPress = new Exercise("Bench Press", startingBenchWeight, 5, 1, Arrays.asList(5, 5, 5));
        overheadPress = new Exercise("Overhead Press", startingOverheadWeight, 5, 1, Arrays.asList(5, 5, 5));
        squat = new Exercise("Squat", startingSquatWeight, 5, 1, Arrays.asList(5, 5, 5));
        deadlift = new Exercise("Deadlift", startingDeadliftWeight, 5, 1, Arrays.asList(5));
        barbellRow = new Exercise("Barbell Row", startingBarbellRowWeight, 5, 1, Arrays.asList(5, 5, 5));
    }

    private void initializeWorkouts() {
        workoutA = new Workout("workoutA", new ArrayList<Exercise>(Arrays.asList(squat, benchPress, barbellRow)));
        workoutB = new Workout("workoutB", new ArrayList<Exercise>(Arrays.asList(squat, overheadPress, deadlift)));
    }

    private void initializeRoutine() {
        beginnerRoutine = new Routine(3, "beginner_routine", new ArrayList<Workout>(Arrays.asList(workoutA, workoutB)));
    }
}

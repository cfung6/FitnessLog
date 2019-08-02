package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Routine;
import com.example.fitnessapp.Workout;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

    private Exercise currentExercise;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        setDateText();
        initializeWeights();
        initializeExercises();
        initializeWorkouts();
        initializeRoutine();

        TextView exerciseOneView = findViewById(R.id.exercise_one);
        currentExercise = beginnerRoutine.getCurrentWorkout().getCurrentExercise();
        exerciseOneView.setText(currentExercise.getName());

        TextView exerciseOneWeightView = findViewById(R.id.exercise_one_weight);
        exerciseOneWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseOneSetView = findViewById(R.id.exercise_one_set);
        exerciseOneSetView.setText("Sets:" + currentExercise.getGoalReps().size());

        TextView exerciseOneRepsView = findViewById(R.id.exercise_one_reps);
        exerciseOneRepsView.setText("Reps: " + currentExercise.getGoalReps().get(0));
    }

    public void submitOnClick(View view) {

        EditText weight1ET = findViewById(R.id.weight1);
        EditText weight2ET = findViewById(R.id.weight2);
        EditText weight3ET = findViewById(R.id.weight3);
        EditText reps1ET = findViewById(R.id.reps1);
        EditText reps2ET = findViewById(R.id.reps2);
        EditText reps3ET = findViewById(R.id.reps3);

        String weight1 = weight1ET.getText().toString();
        String weight2 = weight2ET.getText().toString();
        String weight3 = weight3ET.getText().toString();
        String reps1 = reps1ET.getText().toString();
        String reps2 = reps2ET.getText().toString();
        String reps3 = reps3ET.getText().toString();

        if (!weight1.isEmpty() && !weight1.equals(".") && !reps1.isEmpty() && !weight2ET.isShown() && !reps2ET.isShown() && !weight3ET.isShown() && !reps3ET.isShown()) {
            currentExercise.addRepsDone(Double.parseDouble(weight1), Integer.parseInt(reps1));
            weight2ET.setVisibility(View.VISIBLE);
            reps2ET.setVisibility(View.VISIBLE);
        } else if (!weight2.isEmpty() && !weight2.equals(".") && !reps2.isEmpty() && !weight3ET.isShown() && !reps3ET.isShown()) {
            currentExercise.addRepsDone(Double.parseDouble(weight2), Integer.parseInt(reps2));
            weight3ET.setVisibility(View.VISIBLE);
            reps3ET.setVisibility(View.VISIBLE);
        } else if (!weight3.isEmpty() && !weight3.equals(".") && !reps3.isEmpty()) {
            currentExercise.addRepsDone(Double.parseDouble(weight3), Integer.parseInt(reps3));
        } else {
            Snackbar mySnackbar = Snackbar.make(view, "Weights and/or reps are blank", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    private void setDateText() {
        TextView dateView = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dateView.setText(full);
    }

    private void initializeWeights() {
        Intent intent = getIntent();
        startingBenchWeight = intent.getDoubleExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getDoubleExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getDoubleExtra("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getDoubleExtra("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getDoubleExtra("BARBELL_ROW_WEIGHT", -1);
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

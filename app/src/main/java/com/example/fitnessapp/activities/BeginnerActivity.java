package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Workout currentWorkout;
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

        currentExercise = beginnerRoutine.getCurrentWorkout().getCurrentExercise();
        currentWorkout = beginnerRoutine.getCurrentWorkout();

        TextView exerciseOneView = findViewById(R.id.exercise_one);
        exerciseOneView.setText(currentExercise.getName());

        TextView exerciseOneWeightView = findViewById(R.id.exercise_one_weight);
        exerciseOneWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseOneSetView = findViewById(R.id.exercise_one_set);
        exerciseOneSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseOneRepsView = findViewById(R.id.exercise_one_reps);
        exerciseOneRepsView.setText("Reps: " + currentExercise.getGoalReps().get(0));

        currentWorkout.nextExercise();
        currentExercise = currentWorkout.getCurrentExercise();

        TextView exerciseTwoView = findViewById(R.id.exercise_two);
        exerciseTwoView.setText(currentExercise.getName());

        TextView exerciseTwoWeightView = findViewById(R.id.exercise_two_weight);
        exerciseTwoWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseTwoSetView = findViewById(R.id.exercise_two_set);
        exerciseTwoSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseTwoRepsView = findViewById(R.id.exercise_two_reps);
        exerciseTwoRepsView.setText("Reps: " + currentExercise.getGoalReps().get(1));
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
//            case R.id.stopwatch:
//                startActivity(new Intent(this, Stopwatch.class));
//                return true;
//            case R.id.exercise_tutorials:
//                startActivity(new Intent(this, ExerciseTutorials.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        if (AreWeightsAndRepsFilled(weight1, reps1) && AreWeightsAndRepsInvisible(weight2ET, reps2ET)) {
            weight2ET.setVisibility(View.VISIBLE);
            reps2ET.setVisibility(View.VISIBLE);

        } else if (AreWeightsAndRepsFilled(weight2, reps2) && AreWeightsAndRepsInvisible(weight3ET, reps3ET)) {
            weight3ET.setVisibility(View.VISIBLE);
            reps3ET.setVisibility(View.VISIBLE);

        } else if (AreWeightsAndRepsFilled(weight1, reps1) && AreWeightsAndRepsFilled(weight2, reps2) && AreWeightsAndRepsFilled(weight3, reps3)) {
            //After all lines are visible, submit button adds all the inputs at once
            currentExercise.removeRepsDone();
            currentExercise.addRepsDone(Double.parseDouble(weight1), Integer.parseInt(reps1));
            currentExercise.addRepsDone(Double.parseDouble(weight2), Integer.parseInt(reps2));
            currentExercise.addRepsDone(Double.parseDouble(weight3), Integer.parseInt(reps3));
            currentExercise.increaseWeight();
            //Set textview based on pass or fail
            //Textview tv = get by id
//            if (currentExercise.passOrFail()) {
//                tv.setText(nice job bro your next weight is blah blah);
//            } else {
//                tv.setText(sucks to suck stay at current weight);
//            }
            currentWorkout.nextExercise();

        } else {
            Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights and/or reps are blank", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    private void setDateText() {
        TextView dateView = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("EE, MMM d YYYY").format(date);
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
        workoutA = new Workout("workoutA", new ArrayList<>(Arrays.asList(squat, benchPress, barbellRow)));
        workoutB = new Workout("workoutB", new ArrayList<>(Arrays.asList(squat, overheadPress, deadlift)));
    }

    private void initializeRoutine() {
        beginnerRoutine = new Routine(3, "beginner_routine", new ArrayList<>(Arrays.asList(workoutA, workoutB)));
    }

    private boolean AreWeightsAndRepsFilled(String weight, String reps) {
        return !weight.isEmpty() && !weight.equals(".") && !reps.isEmpty();
    }

    private boolean AreWeightsAndRepsInvisible(EditText weight, EditText reps) {
        return !weight.isShown() && !reps.isShown();
    }
}

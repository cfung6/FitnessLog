package com.example.fitnessapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.DatabaseHelper;
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
    private Date date;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        databaseHelper = new DatabaseHelper(this);
        date = new Date();

        setDateText();
        initializeWeights();
        initializeExercises();
        initializeWorkouts();
        initializeRoutine();

        currentWorkout = beginnerRoutine.getCurrentWorkout();
        currentExercise = currentWorkout.getCurrentExercise();

        setFirstExercise();
        nextExercise();
        setSecondExercse();
        nextExercise();
        setThirdExercise();
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
        String tag = view.getTag().toString();
        int index = Integer.parseInt(tag);
        submitOnClick(view, index);
    }

    public void submitOnClick(View view, int index) {

        Exercise exercise = currentWorkout.getExerciseAtIndex(index - 1);
        int numOfSets = exercise.getGoalReps().size();

        databaseHelper.insertBeginnerRoutineData(beginnerRoutine.getWorkouts().indexOf(currentWorkout), exercise.getName());

        EditText[] weightsET = new EditText[numOfSets + 1];
        EditText[] repsET = new EditText[numOfSets + 1];
        String[] weights = new String[numOfSets + 1];
        String[] reps = new String[numOfSets + 1];

        for (int i = 1; i <= numOfSets; i++) {
            int id = getResources().getIdentifier("weight" + i + "ex" + index, "id", getPackageName());
            weightsET[i] = findViewById(id);
        }

        for (int i = 1; i <= numOfSets; i++) {
            int id = getResources().getIdentifier("reps" + i + "ex" + index, "id", getPackageName());
            repsET[i] = findViewById(id);
        }

        for (int i = 1; i <= numOfSets; i++) {
            weights[i] = weightsET[i].getText().toString();
        }

        for (int i = 1; i <= numOfSets; i++) {
            reps[i] = repsET[i].getText().toString();
        }

        if (AreWeightsAndRepsFilled(weights[1], reps[1]) && AreWeightsAndRepsInvisible(weightsET[2], repsET[2])) {
            weightsET[2].setVisibility(View.VISIBLE);
            repsET[2].setVisibility(View.VISIBLE);

        } else if (AreWeightsAndRepsFilled(weights[2], reps[2]) && AreWeightsAndRepsInvisible(weightsET[3], repsET[3])) {
            weightsET[3].setVisibility(View.VISIBLE);
            repsET[3].setVisibility(View.VISIBLE);

        } else if (AreWeightsAndRepsFilled(weights[1], reps[1]) && AreWeightsAndRepsFilled(weights[2], reps[2]) && AreWeightsAndRepsFilled(weights[3], reps[3])) {
            //After all lines are visible, submit button adds all the inputs at once

            exercise.removeRepsDone();
            exercise.addRepsDone(Double.parseDouble(weights[1]), Integer.parseInt(reps[1]));
            if (databaseHelper.isExerciseInBeginnerTable(exercise)) {
                exercise.addRepsDone(Double.parseDouble(weights[1]), Integer.parseInt(reps[1]));
                databaseHelper.updateDataBeginnerTable(date.getTime(), Double.parseDouble(weights[1]), Integer.parseInt(reps[1]), index);
                exercise.addRepsDone(Double.parseDouble(weights[2]), Integer.parseInt(reps[2]));
                databaseHelper.updateDataBeginnerTable(date.getTime(), Double.parseDouble(weights[2]), Integer.parseInt(reps[2]), index);
                exercise.addRepsDone(Double.parseDouble(weights[3]), Integer.parseInt(reps[3]));
                databaseHelper.updateDataBeginnerTable(date.getTime(), Double.parseDouble(weights[3]), Integer.parseInt(reps[3]), index);
            } else {
                exercise.addRepsDone(Double.parseDouble(weights[1]), Integer.parseInt(reps[1]));
                databaseHelper.insertData(date.getTime(), Double.parseDouble(weights[1]), Integer.parseInt(reps[1]), index);
                exercise.addRepsDone(Double.parseDouble(weights[2]), Integer.parseInt(reps[2]));
                databaseHelper.insertData(date.getTime(), Double.parseDouble(weights[2]), Integer.parseInt(reps[2]), index);
                exercise.addRepsDone(Double.parseDouble(weights[3]), Integer.parseInt(reps[3]));
                databaseHelper.insertData(date.getTime(), Double.parseDouble(weights[3]), Integer.parseInt(reps[3]), index);
            }







//          Set textview based on pass or fail
            int id = getResources().getIdentifier("message" + index, "id", getPackageName());
            TextView tv = findViewById(id);

            if (exercise.isWeightIncreased()) {
                exercise.removeRepsDone();
                exercise.setGoalWeight((exercise.getGoalWeight() - exercise.getIncrement()) / exercise.getPercentage());
                exercise.addRepsDone(Double.parseDouble(weights[1]), Integer.parseInt(reps[1]));
                exercise.addRepsDone(Double.parseDouble(weights[2]), Integer.parseInt(reps[2]));
                exercise.addRepsDone(Double.parseDouble(weights[3]), Integer.parseInt(reps[3]));
                if (exercise.passOrFail()) {
                    exercise.increaseWeight();
                    exercise.setWeightIncreased(true);
                    tv.setText("Congrats! Your next weight is " + exercise.getGoalWeight() + ".\n");
                } else {
                    tv.setText("Failure is inevitable! Stay at your current weight.\n");
                    exercise.setWeightIncreased(false);
                }
            } else if (exercise.passOrFail()) {
                exercise.increaseWeight();
                exercise.setWeightIncreased(true);
                tv.setText("Congrats! Your next weight is " + exercise.getGoalWeight() + ".\n");
            } else {
                tv.setText("Failure is inevitable! Stay at your current weight.\n");
            }
        } else {
            Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights and/or reps are blank", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    private void setDateText() {
        TextView dateView = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("EE, MMM d yyyy").format(date);
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

    private void setFirstExercise() {
        TextView exerciseOneView = findViewById(R.id.exercise_one);
        exerciseOneView.setText(currentExercise.getName());

        TextView exerciseOneWeightView = findViewById(R.id.exercise_one_weight);
        exerciseOneWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseOneSetView = findViewById(R.id.exercise_one_set);
        exerciseOneSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseOneRepsView = findViewById(R.id.exercise_one_reps);
        exerciseOneRepsView.setText("Reps: " + currentExercise.getGoalReps().get(0));
    }

    private void setSecondExercse() {
        TextView exerciseTwoView = findViewById(R.id.exercise_two);
        exerciseTwoView.setText(currentExercise.getName());

        TextView exerciseTwoWeightView = findViewById(R.id.exercise_two_weight);
        exerciseTwoWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseTwoSetView = findViewById(R.id.exercise_two_set);
        exerciseTwoSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseTwoRepsView = findViewById(R.id.exercise_two_reps);
        exerciseTwoRepsView.setText("Reps: " + currentExercise.getGoalReps().get(1));
    }

    private void setThirdExercise() {
        TextView exerciseThreeView = findViewById(R.id.exercise_three);
        exerciseThreeView.setText(currentExercise.getName());

        TextView exerciseThreeWeightView = findViewById(R.id.exercise_three_weight);
        exerciseThreeWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseThreeSetView = findViewById(R.id.exercise_three_set);
        exerciseThreeSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseThreeRepsView = findViewById(R.id.exercise_three_reps);
        exerciseThreeRepsView.setText("Reps: " + currentExercise.getGoalReps().get(1));
    }

    private void nextExercise() {
        currentWorkout.nextExercise();
        currentExercise = currentWorkout.getCurrentExercise();
    }
}

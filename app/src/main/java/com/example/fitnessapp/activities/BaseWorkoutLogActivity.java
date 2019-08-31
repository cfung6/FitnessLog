package com.example.fitnessapp.activities;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public abstract class BaseWorkoutLogActivity extends AppCompatActivity {

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private double[] exerciseWeights;
    private String[] exerciseNames;

    private int increment;
    private int percentage;

    private List<Workout> workouts;
    private List<Exercise> exercises;
    private List<Integer> goalReps;

    private Routine routine;
    private int routineID;

    private Workout currentWorkout;

    private long currentTime;
    private long todaysTime;

    private Intent intent;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_workout_log);

        intent = getIntent();

        currentTime = intent.getLongExtra("TIME", new Date().getTime());
        todaysTime = currentTime - currentTime % (24 * 60 * 60 * 1000);

        setDateText();
        initializeArrays();
        initializeExercises();
        initializeWorkouts();

        //Setting current workout depending on last entries in database
        currentWorkout = routine.getCurrentWorkout();
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

    public void submitOnClick(View view) {
        //Each submit button has an integer tag that is passed (First submit is 0, second is 1, etc)
        String tag = view.getTag().toString();
        int currentExerciseNum = Integer.parseInt(tag);

        submitOnClick(view, currentExerciseNum);
    }

    public void submitOnClick(View view, int currentExerciseNum) {
        //Sets current exercise depending on which submit button was pressed
        Exercise exercise = currentWorkout.getExerciseAtIndex(currentExerciseNum);
        //Gets index of current workout
        int workoutIndex = routine.getWorkouts().indexOf(currentWorkout);
        int numOfSets = exercise.getGoalReps().size();
        String exerciseName = exercise.getName();
        //Inserts exercise name and associated workout number into table if it does not already exist
        if (routineID == 1) {
            databaseHelper.insertBeginnerRoutineData(workoutIndex, exerciseName);
        } else if (routineID == 2) {
            databaseHelper.insertIntermediateRoutineData(workoutIndex, exerciseName);
        } else {
            databaseHelper.insertAdvancedRoutineData(workoutIndex, exerciseName);
        }


        EditText[] weightsET = new EditText[numOfSets];
        EditText[] repsET = new EditText[numOfSets];
        String[] weightsInput = new String[numOfSets];
        String[] repsInput = new String[numOfSets];

        //Assigns variables in the four arrays depending on which set and exercise the user is on
        for (int currentSetNum = 0; currentSetNum < numOfSets; currentSetNum++) {
            String weightsETName = "weight" + currentSetNum + "ex" + currentExerciseNum;
            String repsETName = "reps" + currentSetNum + "ex" + currentExerciseNum;

            //Finds the correct EditText depending on the current exercise and set
            int weightsETid = getResources().getIdentifier(weightsETName, "id", getPackageName());
            int repsETid = getResources().getIdentifier(repsETName, "id", getPackageName());

            weightsET[currentSetNum] = findViewById(weightsETid);
            repsET[currentSetNum] = findViewById(repsETid);

            weightsInput[currentSetNum] = weightsET[currentSetNum].getText().toString();
            repsInput[currentSetNum] = repsET[currentSetNum].getText().toString();
        }

        //Sets the next EditTexts to visible
        if (!setNextToVisible(numOfSets, weightsInput, repsInput, weightsET, repsET)) {
            int workoutExerciseID;
            //Checking to see if all EditTexts are filled
            if (areAllFilled(weightsInput, repsInput)) {
                try {
                    double[] weights = new double[currentWorkout.getExercises().size()];
                    int[] reps = new int[currentWorkout.getExercises().size()];

                    for (int i = 0; i < weights.length; i++) {
                        weights[i] = Double.parseDouble(weightsInput[i]);
                        reps[i] = Integer.parseInt(repsInput[i]);
                    }

                    //Set textview based on pass or fail
                    int textViewid = getResources().getIdentifier("message" + currentExerciseNum, "id", getPackageName());
                    TextView tv = findViewById(textViewid);

                    //After all lines are visible, submit button removes all weights and reps so all can be added at once
                    exercise.removeRepsDone();
                    addRepsDoneToExercise(exercise, weights, reps);

                    //Checks if goal weight has already increased when submit button is pressed to avoid incrementing more than once
                    if (exercise.isWeightIncreased()) {
                        exercise.setGoalWeight((exercise.getGoalWeight() - exercise.getIncrement()) / exercise.getPercentage());
                        exercise.removeRepsDone();
                        addRepsDoneToExercise(exercise, weights, reps);
                    }

                    //Checks if the required reps and weight were done for the exercise
                    if (exercise.passOrFail()) {
                        //Increases exercise goal weight
                        exercise.increaseWeight();
                        exercise.setWeightIncreased(true);
                        tv.setText("Congrats! Your next weight is " + exercise.getGoalWeight() + ".\n");
                    } else {
                        tv.setText("Failure is inevitable! Stay at your current weight.\n");
                        exercise.setWeightIncreased(false);
                    }

                    //Finds workoutExerciseID corresponding to the current workout and exercise
                    if (routineID == 1) {
                        workoutExerciseID = databaseHelper.selectWorkoutExerciseID("BeginnerTable", workoutIndex, exerciseName);
                    } else if (routineID == 2) {
                        workoutExerciseID = databaseHelper.selectWorkoutExerciseID("IntermediateTable", workoutIndex, exerciseName);
                    } else {
                        workoutExerciseID = databaseHelper.selectWorkoutExerciseID("AdvancedTable", workoutIndex, exerciseName);
                    }

                    //Gets new goal weight
                    double capableWeight = exercise.getCapableWeight();

                    //Checks if database contains any entries with the current time of today and workoutExerciseID
                    if (databaseHelper.haveEntriesBeenEntered(todaysTime, workoutExerciseID)) {
                        List<Double> weightsForDataTable = new ArrayList<>();
                        List<Integer> repsForDataTable = new ArrayList<>();

                        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
                            weightsForDataTable.add(weights[i]);
                            repsForDataTable.add(reps[i]);
                        }

                        databaseHelper.updateEntries(currentTime, todaysTime, routineID, workoutExerciseID,
                                weightsForDataTable, repsForDataTable, capableWeight);
                    } else {
                        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
                            databaseHelper.insertData(currentTime, todaysTime, routineID, workoutExerciseID, weights[i], reps[i], capableWeight);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    Snackbar mySnackbar = Snackbar.make(view, "Invalid input(s)", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            } else {
                Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights and/or reps are blank", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        }
    }

    //Sets textView for the current date
    private void setDateText() {
        TextView dateView = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("EE, MMM d yyyy").format(date);
        dateView.setText(full);
    }

    private void initializeArrays() {
        //Getting weights and names from previous activity
        exerciseWeights = intent.getDoubleArrayExtra("WEIGHTS");
        exerciseNames = intent.getStringArrayExtra("NAMES");
        exercises = new ArrayList<>();
        workouts = new ArrayList<>();
    }

    protected abstract void initializeWorkouts();

    private void initializeExercises() {
        //Goalreps may be different for each exercise
        goalReps = new ArrayList<>(Arrays.asList(5, 5, 5));
        for (int i = 0; i < exerciseWeights.length; i++) {
            exercises.add(new Exercise(exerciseNames[i], exerciseWeights[i], increment, percentage, goalReps));
        }
    }

    protected void initializeRoutine(String name, Context context) {
        routine = new Routine(name, workouts, context);
    }

    protected void setRoutineID(int id) {
        routineID = id;
    }

    protected void setIncrement(int incr) {
        increment = incr;
    }

    protected void setPercentage(int perc) {
        percentage = perc;
    }

    protected void setNewDatabaseHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    //Sets the next EditTexts to visible, returns true if it works, false if not
    private boolean setNextToVisible(int numOfSets, String[] weightsInput, String[] repsInput, EditText[] weightsET, EditText[] repsET) {
        for (int i = 0; i < numOfSets - 1; i++) {
            if (areWeightsAndRepsFilled(weightsInput[i], repsInput[i])
                    && areWeightsAndRepsInvisible(weightsET[i + 1], repsET[i + 1])) {

                weightsET[i + 1].setVisibility(View.VISIBLE);
                repsET[i + 1].setVisibility(View.VISIBLE);

                return true;
            }
        }
        return false;
    }

    private boolean areWeightsAndRepsFilled(String weight, String reps) {
        return !weight.isEmpty() && !weight.equals(".") && !reps.isEmpty();
    }

    private boolean areWeightsAndRepsInvisible(EditText weight, EditText reps) {
        return !weight.isShown() && !reps.isShown();
    }

    private Exercise getExerciseByName(String name) {
        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);

            if (exercise.getName().toLowerCase().equals(name.toLowerCase())) {
                return exercise;
            }
        }
        return null;
    }

    private void addRepsDoneToExercise(Exercise exercise, double[] weights, int[] reps) {
        for (int i = 0; i < weights.length; i++) {
            exercise.addRepsDone(weights[i], reps[i]);
        }
    }

    private boolean areAllFilled(String[] weights, String[] reps) {
        return areWeightsAndRepsFilled(weights[0], reps[0])
                && areWeightsAndRepsFilled(weights[1], reps[1])
                && areWeightsAndRepsFilled(weights[2], reps[2]);
    }
}

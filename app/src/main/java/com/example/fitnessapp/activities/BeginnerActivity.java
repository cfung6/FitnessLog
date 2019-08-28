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

public class BeginnerActivity extends AppCompatActivity {

    private double startingBenchWeight;
    private double startingOverheadWeight;
    private double startingSquatWeight;
    private double startingDeadliftWeight;
    private double startingBarbellRowWeight;

    private Routine beginnerRoutine;
    private int routineID;
    private int numOfWorkouts;

    private Workout currentWorkout;
    private Workout workoutA;
    private Workout workoutB;

    private Exercise benchPress;
    private Exercise overheadPress;
    private Exercise squat;
    private Exercise deadlift;
    private Exercise barbellRow;

    private long currentTime;
    private long todaysTime;
    private Intent intent;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        intent = getIntent();
        databaseHelper = new DatabaseHelper(this);
        //Beginner ID is 1, intermediate is 2, advanced is 3
        routineID = 1;
        numOfWorkouts = 3;

        //Receives time from WorkoutCalendar, defaults to today's time if the previous activity is anything else
        currentTime = intent.getLongExtra("TIME", new Date().getTime());
        todaysTime = currentTime - currentTime % (24 * 60 * 60 * 1000);

        setDateText();
        initializeWeights();
        initializeExercises();
        initializeWorkouts();
        initializeRoutine();

        //Setting current workout depending on last entries in database
        currentWorkout = beginnerRoutine.getCurrentWorkout();

        //Setting TextViews for exercises
        setFirstExercise();
        setSecondExercise();
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
        //Each submit button has an integer tag that is passed (First submit is 1, second is 2, etc)
        String tag = view.getTag().toString();
        int currentExerciseNum = Integer.parseInt(tag);

        submitOnClick(view, currentExerciseNum);
    }

    public void submitOnClick(View view, int currentExerciseNum) {
        //Sets current exercise depending on which submit button was pressed
        Exercise exercise = currentWorkout.getExerciseAtIndex(currentExerciseNum - 1);
        //Gets index of current workout
        int workoutIndex = beginnerRoutine.getWorkouts().indexOf(currentWorkout);
        int numOfSets = exercise.getGoalReps().size();
        String exerciseName = exercise.getName();
        //Inserts exercise name and associated workout number into table if it does not already exist
        databaseHelper.insertBeginnerRoutineData(workoutIndex, exerciseName);

        EditText[] weightsET = new EditText[numOfSets + 1];
        EditText[] repsET = new EditText[numOfSets + 1];
        String[] weights = new String[numOfSets + 1];
        String[] reps = new String[numOfSets + 1];

        //Assigns variables in the four arrays depending on which set and exercise the user is on
        for (int currentSetNum = 1; currentSetNum <= numOfSets; currentSetNum++) {
            String weightsETName = "weight" + currentSetNum + "ex" + currentExerciseNum;
            String repsETName = "reps" + currentSetNum + "ex" + currentExerciseNum;

            //Finds the correct EditText depending on the current exercise and set
            int weightsETid = getResources().getIdentifier(weightsETName, "id", getPackageName());
            int repsETid = getResources().getIdentifier(repsETName, "id", getPackageName());

            weightsET[currentSetNum] = findViewById(weightsETid);
            repsET[currentSetNum] = findViewById(repsETid);

            weights[currentSetNum] = weightsET[currentSetNum].getText().toString();
            reps[currentSetNum] = repsET[currentSetNum].getText().toString();
        }

        if (areWeightsAndRepsFilled(weights[1], reps[1]) && areWeightsAndRepsInvisible(weightsET[2], repsET[2])) {
            weightsET[2].setVisibility(View.VISIBLE);
            repsET[2].setVisibility(View.VISIBLE);

        } else if (areWeightsAndRepsFilled(weights[2], reps[2]) && areWeightsAndRepsInvisible(weightsET[3], repsET[3])) {
            weightsET[3].setVisibility(View.VISIBLE);
            repsET[3].setVisibility(View.VISIBLE);

        } else if (areAllFilled(weights, reps)) {
            try {
                double weights1 = Double.parseDouble(weights[1]);
                double weights2 = Double.parseDouble(weights[2]);
                double weights3 = Double.parseDouble(weights[3]);

                int reps1 = Integer.parseInt(reps[1]);
                int reps2 = Integer.parseInt(reps[2]);
                int reps3 = Integer.parseInt(reps[3]);

//              Set textview based on pass or fail
                int textViewid = getResources().getIdentifier("message" + currentExerciseNum, "id", getPackageName());
                TextView tv = findViewById(textViewid);

                //After all lines are visible, submit button removes all weights and reps so all can be added at once
                exercise.removeRepsDone();
                exercise.addRepsDone(weights1, reps1);
                exercise.addRepsDone(weights2, reps2);
                exercise.addRepsDone(weights3, reps3);

                //Checks if goal weight has already increased when submit button is pressed to avoid incrementing more than once
                if (exercise.isWeightIncreased()) {
                    exercise.setGoalWeight((exercise.getGoalWeight() - exercise.getIncrement()) / exercise.getPercentage());

                    if (exercise.passOrFail()) {
                        //Increases exercise goal weight
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

                //Finds workoutExerciseID corresponding to the current workout and exercise
                int workoutExerciseID = databaseHelper.selectWorkoutExerciseID("BeginnerTable", workoutIndex, exerciseName);
                //Gets new goal weight
                double capableWeight = exercise.getCapableWeight();

                //Checks if database contains any entries with the current time of today and workoutExerciseID
                if (databaseHelper.haveEntriesBeenEntered(todaysTime, workoutExerciseID)) {
                    List<Double> weightsForDataTable
                            = new ArrayList<>(Arrays.asList(weights1, weights2, weights3));
                    List<Integer> repsForDataTable
                            = new ArrayList<>(Arrays.asList(reps1, reps2, reps3));

                    databaseHelper.updateEntries(currentTime, todaysTime, routineID, workoutExerciseID,
                            weightsForDataTable, repsForDataTable, capableWeight);
                } else {
                    databaseHelper.insertData(currentTime, todaysTime, routineID, workoutExerciseID, weights1, reps1, capableWeight);
                    databaseHelper.insertData(currentTime, todaysTime, routineID, workoutExerciseID, weights2, reps2, capableWeight);
                    databaseHelper.insertData(currentTime, todaysTime, routineID, workoutExerciseID, weights3, reps3, capableWeight);
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

    //Sets textView for the current date
    private void setDateText() {
        TextView dateView = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("EE, MMM d yyyy").format(date);
        dateView.setText(full);
    }

    //Gets weights from NewProgramActivity
    private void initializeWeights() {
        startingBenchWeight = intent.getDoubleExtra("BENCH_PRESS_WEIGHT", -1);
        startingOverheadWeight = intent.getDoubleExtra("OVERHEAD_PRESS_WEIGHT", -1);
        startingSquatWeight = intent.getDoubleExtra("SQUAT_WEIGHT", -1);
        startingDeadliftWeight = intent.getDoubleExtra("DEADLIFT_WEIGHT", -1);
        startingBarbellRowWeight = intent.getDoubleExtra("BARBELL_ROW_WEIGHT", -1);
    }

    private void initializeExercises() {
        int increment = 5;
        int percentage = 1;
        ArrayList<Integer> goalReps = new ArrayList<>(Arrays.asList(5, 5, 5));

        benchPress = new Exercise("Bench Press", startingBenchWeight, increment, percentage, goalReps);
        overheadPress = new Exercise("Overhead Press", startingOverheadWeight, increment, percentage, goalReps);
        squat = new Exercise("Squat", startingSquatWeight, increment, percentage, goalReps);
        deadlift = new Exercise("Deadlift", startingDeadliftWeight, increment, percentage, goalReps);
        barbellRow = new Exercise("Barbell Row", startingBarbellRowWeight, increment, percentage, goalReps);
    }

    private void initializeWorkouts() {
        workoutA = new Workout("workoutA", new ArrayList<>(Arrays.asList(squat, benchPress, barbellRow)));
        workoutB = new Workout("workoutB", new ArrayList<>(Arrays.asList(squat, overheadPress, deadlift)));
    }

    private void initializeRoutine() {
        ArrayList<Workout> workouts = new ArrayList<>(Arrays.asList(workoutA, workoutB));
        beginnerRoutine = new Routine("beginner", workouts, this);
    }

    private boolean areWeightsAndRepsFilled(String weight, String reps) {
        return !weight.isEmpty() && !weight.equals(".") && !reps.isEmpty();
    }

    private boolean areWeightsAndRepsInvisible(EditText weight, EditText reps) {
        return !weight.isShown() && !reps.isShown();
    }

    private void setFirstExercise() {
        Exercise currentExercise = currentWorkout.getExercises().get(0);
        TextView exerciseOneView = findViewById(R.id.exercise_one);
        exerciseOneView.setText(currentExercise.getName());

        TextView exerciseOneWeightView = findViewById(R.id.exercise_one_weight);
        exerciseOneWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseOneSetView = findViewById(R.id.exercise_one_set);
        exerciseOneSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseOneRepsView = findViewById(R.id.exercise_one_reps);
        exerciseOneRepsView.setText("Reps: " + currentExercise.getGoalReps().get(0));
    }

    private void setSecondExercise() {
        Exercise currentExercise = currentWorkout.getExercises().get(1);
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
        Exercise currentExercise = currentWorkout.getExercises().get(2);
        TextView exerciseThreeView = findViewById(R.id.exercise_three);
        exerciseThreeView.setText(currentExercise.getName());

        TextView exerciseThreeWeightView = findViewById(R.id.exercise_three_weight);
        exerciseThreeWeightView.setText("Weight: " + currentExercise.getGoalWeight());

        TextView exerciseThreeSetView = findViewById(R.id.exercise_three_set);
        exerciseThreeSetView.setText("Sets: " + currentExercise.getGoalReps().size());

        TextView exerciseThreeRepsView = findViewById(R.id.exercise_three_reps);
        exerciseThreeRepsView.setText("Reps: " + currentExercise.getGoalReps().get(2));
    }

    private boolean areAllFilled(String[] weights, String[] reps) {
        return areWeightsAndRepsFilled(weights[1], reps[1])
                && areWeightsAndRepsFilled(weights[2], reps[2])
                && areWeightsAndRepsFilled(weights[3], reps[3]);
    }
}

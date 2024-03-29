package com.example.fitnesslog.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.CurrentDate;
import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.DefaultWeights;
import com.example.fitnesslog.R;
import com.example.fitnesslog.Routine;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AskingForWeightsActivity extends AppCompatActivity {

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private EditText[] exerciseEditTexts;
    private double[] rawWeightInputs;
    private List<Double> finalWeightInputs;
    private String[] exerciseStrings;
    private boolean[] exerciseChecked;
    private String[] exerciseNames;
    private double[] weightInputs;

    private DatabaseHelper databaseHelper;
    private int workoutNum;
    private Button submitButton;
    private Intent intent;
    private int numOfExercises;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);

        intent = getIntent();
        //Assigns routine to the level that the user picked
        routine = intent.getParcelableExtra("ROUTINE");
        submitButton = findViewById(R.id.submit_weight_button);
        databaseHelper = new DatabaseHelper(this);

        workoutNum = -1;
        numOfExercises = 5;

        initializeArrays();
        initializeEditTexts();
        limitEditTextsInput();
        initializeBooleans();
        onSubmitButtonClick();
    }

    //Button goes to ExerciseTutorial Activity
    public void onExerciseTutorialClick(View view) {
        intent = new Intent(this, ExerciseTutorials.class);
        startActivity(intent);
    }

    public void onCheckboxClick(View view) {
        //Each checkbox has an integer tag that is passed (First checkbox is 0, second is 1, etc)
        String tag = view.getTag().toString();
        int checkboxNum = Integer.parseInt(tag);

        onCheckboxClick(view, checkboxNum);
    }

    private void onCheckboxClick(View view, int checkboxNum) {
        exerciseChecked[checkboxNum] = ((CheckBox) view).isChecked();

        //When checkbox is checked, the EditText for that exercise appears
        if (exerciseChecked[checkboxNum]) {
            exerciseEditTexts[checkboxNum].setVisibility(View.VISIBLE);
        } else {
            exerciseEditTexts[checkboxNum].setVisibility(View.GONE);
        }
    }

    private void onSubmitButtonClick() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                initializeStringInputs();

                //Checking if any of the EditTexts are empty if the checkbox is checked
                if (!areEditTextsEmpty()) {
                    try {
                        convertStringsToDoubles();
                        passWeightsAndNames();
                    } catch (IllegalArgumentException e) {
                        Snackbar mySnackbar = Snackbar.make(view, "Invalid input(s)", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                    }
                } else {
                    Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights are blank", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            }
        });
    }

    //Passing weights and names for each exercise to next activity
    private void passWeightsAndNames() {
        //Adds input to array if EditTexts are filled, else adds default weight if checkboxes are unchecked
        for (int i = 0; i < numOfExercises; i++) {
            addExerciseInput(i);
        }

        //Converting List<Double> to double[]
        weightInputs = new double[exerciseNames.length];
        for (int i = 0; i < numOfExercises; i++) {
            weightInputs[i] = finalWeightInputs.get(i);
        }

        for (int i = numOfExercises; i < exerciseNames.length; i++) {
            weightInputs[i] = DefaultWeights.defaultWeights.get(10 + i);
        }

        routine.setExerciseWeights(weightInputs);

        insertDataToSQL(routine.getTable());

        intent = new Intent(this, WorkoutLogActivity.class);
        intent.putExtra("ROUTINE", routine);
        intent.putExtra("ACTIVITY", "activity");

        startActivity(intent);
    }

    //If checkboxes are left unchecked, default value is returned for that exercise
    private void addExerciseInput(int exerciseNum) {
        //Making sure that ArrayList of weights cannot be bigger than num of exercises
        if (finalWeightInputs.size() >= numOfExercises) {
            finalWeightInputs.clear();
        }
        if (rawWeightInputs[exerciseNum] == -1) {
            if (routine.getRoutineID() == 1) {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum));
            } else if (routine.getRoutineID() == 2) {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum + numOfExercises));
            } else {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum + (numOfExercises * 2)));
            }
        } else {
            finalWeightInputs.add(rawWeightInputs[exerciseNum]);
        }
    }

    //Converts strings to doubles only if EditTexts are filled
    private void convertStringsToDoubles() {
        for (int i = 0; i < numOfExercises; i++) {
            if (!exerciseStrings[i].isEmpty() && !exerciseStrings[i].equals(".") && exerciseChecked[i]) {
                rawWeightInputs[i] = Double.parseDouble(exerciseStrings[i]);
            } else {
                rawWeightInputs[i] = -1;
            }
        }
    }

    // EFFECTS: inserts each exercise into the given data table (name) then inserts the capableWeight
    //          into DATA_TABLE
    private void insertDataToSQL(String tableName) {
        int workoutExerciseID;
        int routineID = routine.getRoutineID();
        CurrentDate today = new CurrentDate();

        long currentTime = new Date().getTime();
        String currentDate = today.getDateString();

        addDatatoTable(exerciseNames);

        //Decreasing starting weights for advanced program
        if (routineID == 3) {
            for (int i = 0; i < 5; i++) {
                weightInputs[i] = routine.round(weightInputs[i] * .88);
            }
        }

        routine.setExerciseWeights(weightInputs);

        //Deletes all data from DataTable with matching currentDate
        if (databaseHelper.isThereDataFromToday(currentDate, -1)) {
            databaseHelper.deleteTodaysData(currentDate);
        }

        for (int i = 0; i < routine.getExerciseNames().length; i++) {
            workoutExerciseID = databaseHelper.getWorkoutExerciseID(tableName, exerciseNames[i], workoutNum);

            databaseHelper.insertData(currentTime, currentDate, routineID, workoutExerciseID,
                    -1, 0, weightInputs[i]);
        }
    }

    private void addDatatoTable(String[] exercises) {
        for (String exercise : exercises) {
            routine.insertRoutineData(workoutNum, exercise, databaseHelper);
        }
    }

    //Checking if checkboxes are checked but EditTexts are empty
    private boolean areEditTextsEmpty() {
        for (int i = 0; i < numOfExercises; i++) {
            if (exerciseChecked[i]) {
                if (exerciseStrings[i].isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initializeArrays() {
        rawWeightInputs = new double[numOfExercises];
        finalWeightInputs = new ArrayList<>();
        exerciseStrings = new String[numOfExercises];
        exerciseEditTexts = new EditText[numOfExercises];
        exerciseChecked = new boolean[numOfExercises];
        exerciseNames = routine.getExerciseNames();
    }

    private void initializeBooleans() {
        for (int i = 0; i < numOfExercises; i++) {
            exerciseChecked[i] = false;
        }
    }

    //Limiting EditText inputs to only numbers and decimal
    private void limitEditTextsInput() {
        for (int i = 0; i < numOfExercises; i++) {
            exerciseEditTexts[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }

    private void initializeStringInputs() {
        for (int i = 0; i < numOfExercises; i++) {
            exerciseStrings[i] = exerciseEditTexts[i].getText().toString();
        }
    }

    private void initializeEditTexts() {
        exerciseEditTexts[0] = findViewById(R.id.textbox_bench);
        exerciseEditTexts[1] = findViewById(R.id.textbox_overhead);
        exerciseEditTexts[2] = findViewById(R.id.textbox_squat);
        exerciseEditTexts[3] = findViewById(R.id.textbox_deadlift);
        exerciseEditTexts[4] = findViewById(R.id.textbox_barbell_row);
    }
}

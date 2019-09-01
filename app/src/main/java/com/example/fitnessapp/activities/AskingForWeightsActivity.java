package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.DatabaseHelper;
import com.example.fitnessapp.DefaultWeights;
import com.example.fitnessapp.Levels;
import com.example.fitnessapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AskingForWeightsActivity extends AppCompatActivity {

    private Button submitButton;

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private EditText[] exerciseEditTexts;
    private int[] rawWeightInputs;
    private List<Integer> finalWeightInputs;
    private String[] exerciseStrings;
    private boolean[] exerciseChecked;
    private String[] exerciseNames;

    private DatabaseHelper databaseHelper;
    private int workoutNum;
    private int numOfExercises;
    private Levels levelChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);

        Intent intent = getIntent();
        //Assigns variable to the level that the user picked
        levelChosen = (Levels) intent.getSerializableExtra("LEVEL_CHOSEN");
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
        Intent intent = new Intent(this, ExerciseTutorials.class);
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
            @Override
            public void onClick(View view) {
                initializeStringInputs();

                //Checking if any of the EditTexts are empty if the checkbox is checked
                if (!areEditTextsEmpty()) {
                    try {
                        convertStringsToIntegers();
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
        Intent intent;
        String tableName;

        if (levelChosen == Levels.BEGINNER) {
            intent = new Intent(this, BeginnerActivity.class);
            tableName = "BeginnerTable";
        } else if (levelChosen == Levels.INTERMEDIATE) {
            intent = new Intent(this, IntermediateActivity.class);
            tableName = "IntermediateTable";
        } else {
            intent = new Intent(this, AdvancedActivity.class);
            tableName = "AdvancedTable";
        }

        //Adds input to array if EditTexts are filled, else adds default weight if checkboxes are unchecked
        for (int i = 0; i < numOfExercises; i++) {
            addExerciseInput(i);
        }

        //Converting List<Integer> to int[]
        int[] weightInputs = new int[finalWeightInputs.size()];
        for (int i = 0; i < weightInputs.length; i++) {
            weightInputs[i] = finalWeightInputs.get(i);
        }

        intent.putExtra("WEIGHTS", weightInputs);
        intent.putExtra("NAMES", exerciseNames);

        insertDataToSQL(tableName);
        startActivity(intent);
    }

    //If checkboxes are left unchecked, default value is returned for that exercise
    private void addExerciseInput(int exerciseNum) {
        //Making sure that ArrayList of weights cannot be bigger than num of exercises
        if (finalWeightInputs.size() >= numOfExercises) {
            finalWeightInputs.clear();
        }
        if (rawWeightInputs[exerciseNum] == -1) {
            if (levelChosen == Levels.BEGINNER) {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum));
            } else if (levelChosen == Levels.INTERMEDIATE) {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum + numOfExercises));
            } else {
                finalWeightInputs.add(DefaultWeights.defaultWeights.get(exerciseNum + (numOfExercises * 2)));
            }
        } else {
            finalWeightInputs.add(rawWeightInputs[exerciseNum]);
        }
    }

    //Converts strings to ints only if EditTexts are filled
    private void convertStringsToIntegers() {
        for (int i = 0; i < numOfExercises; i++) {
            if (!exerciseStrings[i].isEmpty() && !exerciseStrings[i].equals(".") && exerciseChecked[i]) {
                rawWeightInputs[i] = Integer.parseInt(exerciseStrings[i]);
            } else {
                rawWeightInputs[i] = -1;
            }
        }
    }

    // EFFECTS: inserts each exercise into the given data table (name) then inserts the capableWeight
    //          into DATA_TABLE
    private void insertDataToSQL(String tableName) {
        int workoutExerciseID;
        int routineID;

        long currentTime = new Date().getTime();
        long todaysTime = currentTime - currentTime % (24 * 60 * 60 * 1000);


        if (tableName.equals("BeginnerTable")) {
            addDataToBeginner(exerciseNames);
            routineID = 1;
        } else if (tableName.equals("IntermediateTable")) {
            addDataToIntermediate(exerciseNames);
            routineID = 2;
        } else {
            addDataToAdvanced(exerciseNames);
            routineID = 3;
        }

        for (int i = 0; i < numOfExercises; i++) {
            workoutExerciseID = databaseHelper.selectWorkoutExerciseID(tableName, workoutNum, exerciseNames[i]);
            databaseHelper.insertData(currentTime, todaysTime, routineID, workoutExerciseID, workoutNum, 0, finalWeightInputs.get(i));
        }
    }

    private void addDataToBeginner(String[] exercises) {
        for (int i = 0; i < exercises.length; i++) {
            databaseHelper.insertBeginnerRoutineData(workoutNum, exercises[i]);
        }
    }

    private void addDataToIntermediate(String[] exercises) {
        for (int i = 0; i < exercises.length; i++) {
            databaseHelper.insertIntermediateRoutineData(workoutNum, exercises[i]);
        }
    }

    private void addDataToAdvanced(String[] exercises) {
        for (int i = 0; i < exercises.length; i++) {
            databaseHelper.insertAdvancedRoutineData(workoutNum, exercises[i]);
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
        rawWeightInputs = new int[numOfExercises];
        finalWeightInputs = new ArrayList<>();
        exerciseStrings = new String[numOfExercises];
        exerciseEditTexts = new EditText[numOfExercises];
        exerciseChecked = new boolean[numOfExercises];
        exerciseNames = new String[numOfExercises];
        List<String> exerciseNameList = new ArrayList<>(Arrays.asList(
                "Bench Press",
                "Overhead Press",
                "Squat",
                "Deadlift",
                "Barbell Row"));

        //Turning List<String> into String[]
        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseNames[i] = exerciseNameList.get(i);
        }
    }

    private void initializeBooleans() {
        for (int i = 0; i < numOfExercises; i++) {
            exerciseChecked[i] = false;
        }
    }

    //Limiting EditText inputs to only numbers
    private void limitEditTextsInput() {
        for (int i = 0; i < numOfExercises; i++) {
            exerciseEditTexts[i].setInputType(InputType.TYPE_CLASS_NUMBER);
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

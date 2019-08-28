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
    private double[] exerciseWeightInputs;
    private String[] exerciseStrings;
    private boolean[] exerciseChecked;
    private List<String> exerciseWeightNames;

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
                        convertStringsToDoubles();
                        passWeightsToNext();
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

    //Passing starting weights for each exercise to next activity
    private void passWeightsToNext() {
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

        //Passes input if EditTexts are filled, else pass default weight if checkboxes are unchecked
        for (int i = 0; i < numOfExercises; i++) {
            intent.putExtra(exerciseWeightNames.get(i), getExerciseInput(i));
        }

        insertDataToSQL(tableName);
        startActivity(intent);
    }

    //If checkboxes are left unchecked, default value is returned for that exercise
    private double getExerciseInput(int exerciseNum) {
        if (exerciseWeightInputs[exerciseNum] == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.defaultWeights.get(exerciseNum);
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.defaultWeights.get(exerciseNum + numOfExercises);
            } else {
                return DefaultWeights.defaultWeights.get(exerciseNum + (numOfExercises * 2));
            }
        } else {
            return exerciseWeightInputs[exerciseNum];
        }
    }

    //Converts strings to doubles only if EditTexts are filled
    private void convertStringsToDoubles() {
        for (int i = 0; i < numOfExercises; i++) {
            if (!exerciseStrings[i].isEmpty() && !exerciseStrings[i].equals(".") && exerciseChecked[i]) {
                exerciseWeightInputs[i] = Double.parseDouble(exerciseStrings[i]);
            } else {
                exerciseWeightInputs[i] = -1;
            }
        }
    }

    // EFFECTS: inserts each exercise into the given data table (name) then inserts the capableWeight
    //          into DATA_TABLE
    private void insertDataToSQL(String tableName) {
        int workoutExerciseID;

        //If the list of exercises is different for each routine, exercises must be initialized within each if block
        List<String> exercises = new ArrayList<>(Arrays.asList(
                "Bench Press",
                "Overhead Press",
                "Squat",
                "Deadlift",
                "Barbell Row"));

        if (tableName.equals("BeginnerTable")) {
            addDataToBeginner(exercises);
        } else if (tableName.equals("IntermediateTable")) {
            addDataToIntermediate(exercises);
        } else {
            addDataToAdvanced(exercises);
        }

        for (int i = 0; i < numOfExercises; i++) {
            workoutExerciseID = databaseHelper.selectWorkoutExerciseID(tableName, workoutNum, exercises.get(i));
            databaseHelper.insertData(0, 0, workoutExerciseID, workoutNum, 0, getExerciseInput(i));
        }
    }

    private void addDataToBeginner(List<String> exercises) {
        for (int i = 0; i < exercises.size(); i++) {
            databaseHelper.insertBeginnerRoutineData(workoutNum, exercises.get(i));
        }
    }

    private void addDataToIntermediate(List<String> exercises) {
        for (int i = 0; i < exercises.size(); i++) {
            databaseHelper.insertIntermediateRoutineData(workoutNum, exercises.get(i));
        }
    }

    private void addDataToAdvanced(List<String> exercises) {
        for (int i = 0; i < exercises.size(); i++) {
            databaseHelper.insertAdvancedRoutineData(workoutNum, exercises.get(i));
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
        exerciseWeightInputs = new double[numOfExercises];
        exerciseStrings = new String[numOfExercises];
        exerciseEditTexts = new EditText[numOfExercises];
        exerciseChecked = new boolean[numOfExercises];
        exerciseWeightNames = new ArrayList<>(Arrays.asList(
                "BENCH_PRESS_WEIGHT",
                "OVERHEAD_PRESS_WEIGHT",
                "SQUAT_WEIGHT",
                "DEADLIFT_WEIGHT",
                "BARBELL_ROW_WEIGHT"
        ));
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

package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class AskingForWeightsActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText benchEditText;
    private EditText overheadPressEditText;
    private EditText squatEditText;
    private EditText deadliftEditText;
    private EditText barbellRowEditText;
    private double benchInput;
    private double overheadInput;
    private double squatInput;
    private double deadliftInput;
    private double barbellRowInput;
    private String benchString;
    private String overheadString;
    private String squatString;
    private String deadliftString;
    private String barbellRowString;
    private boolean benchPressChecked;
    private boolean overheadPressChecked;
    private boolean squatChecked;
    private boolean deadliftChecked;
    private boolean barbellRowChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);
        submitButton = findViewById(R.id.submit_weight_button);
        initializeEditTexts();
        limitEditTextsInput();
        intializeBooleans();
        onSubmitButtonClick();


    }

    private void intializeBooleans() {
        benchPressChecked = false;
        overheadPressChecked = false;
        squatChecked = false;
        deadliftChecked = false;
        barbellRowChecked = false;
    }

    private void initializeEditTexts() {
        benchEditText = findViewById(R.id.textbox_bench);
        overheadPressEditText = findViewById(R.id.textbox_overhead);
        squatEditText = findViewById(R.id.textbox_squat);
        deadliftEditText = findViewById(R.id.textbox_deadlift);
        barbellRowEditText = findViewById(R.id.textbox_barbell_row);
    }

    private void limitEditTextsInput() {
        benchEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        overheadPressEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        squatEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        deadliftEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        barbellRowEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private boolean isEmpty(String str1, String str2, String str3, String str4, String str5) {

        if (benchPressChecked) {
            if (str1.isEmpty()) return true;
        }

        if (overheadPressChecked) {
            if (str2.isEmpty()) return true;
        }

        if (squatChecked) {
            if (str3.isEmpty()) return true;
        }

        if (deadliftChecked) {
            if (str4.isEmpty()) return true;
        }

        if (barbellRowChecked) {
            if (str5.isEmpty()) return true;
        }

        return false;
    }

    private void onSubmitButtonClick () {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intializeStringInputs();

                if (!isEmpty(benchString, overheadString, squatString, deadliftString, barbellRowString)) {
                    convertStringsToDoubles();
                    if (benchPressChecked && overheadPressChecked && squatChecked && deadliftChecked && barbellRowChecked) {
                        Intent intent = new Intent(AskingForWeightsActivity.this, WorkoutActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent (AskingForWeightsActivity.this, UncheckedActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights are blank", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }
            }
        });
    }

    private void intializeStringInputs () {
        benchString = benchEditText.getText().toString();
        overheadString = overheadPressEditText.getText().toString();
        squatString = squatEditText.getText().toString();
        deadliftString = deadliftEditText.getText().toString();
        barbellRowString = barbellRowEditText.getText().toString();
    }

    private void convertStringsToDoubles () {
        benchInput = Double.parseDouble(benchString);
        overheadInput = Double.parseDouble(overheadString);
        squatInput = Double.parseDouble(squatString);
        deadliftInput = Double.parseDouble(deadliftString);
        barbellRowInput = Double.parseDouble(barbellRowString);
    }


    public void onBenchPressClick (View view) {
        benchPressChecked = ((CheckBox) view).isChecked();

        if (benchPressChecked) {
           benchEditText.setVisibility(View.VISIBLE);
        } else {
            benchEditText.setVisibility(View.GONE);
        }
    }

    public void onOverheadPressClick(View view) {
        overheadPressChecked = ((CheckBox) view).isChecked();

        if (overheadPressChecked) {
            overheadPressEditText.setVisibility(View.VISIBLE);
        } else {
            overheadPressEditText.setVisibility(View.GONE);
        }
    }

    public void onSquatClick(View view) {
        squatChecked = ((CheckBox) view).isChecked();

        if (squatChecked) {
            squatEditText.setVisibility(View.VISIBLE);
        } else {
            squatEditText.setVisibility(View.GONE);
        }
    }

    public void onDeadliftClick(View view) {
        deadliftChecked = ((CheckBox) view).isChecked();

        if (deadliftChecked) {
            deadliftEditText.setVisibility(View.VISIBLE);
        } else {
            deadliftEditText.setVisibility(View.GONE);
        }
    }

    public void onBarbellRowClick(View view) {
        barbellRowChecked = ((CheckBox) view).isChecked();

        if (barbellRowChecked) {
            barbellRowEditText.setVisibility(View.VISIBLE);
        } else {
            barbellRowEditText.setVisibility(View.GONE);
        }
    }


    public double getBenchInput() {
        return benchInput;
    }

    public double getOverheadInput() {
        return overheadInput;
    }

    public double getSquatInput() {
        return squatInput;
    }

    public double getDeadliftInput() {
        return deadliftInput;
    }

    public double getBarbellRowInput() {
        return barbellRowInput;
    }
}

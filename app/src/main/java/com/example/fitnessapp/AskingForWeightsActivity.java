package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);
        submitButton = findViewById(R.id.submit_weight_button);
        initializeEditTexts();
        limitEditTextsInput();
        onSubmitButtonClick();
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

    private boolean checkIfEmpty (String str1, String str2, String str3, String str4, String str5) {

        return (str1.isEmpty() || str2.isEmpty() || str3.isEmpty() || str4.isEmpty() || str1.isEmpty());

    }

    private void onSubmitButtonClick () {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intializeStringInputs();;

                if (!checkIfEmpty(benchString, overheadString, squatString, deadliftString, barbellRowString)) {
                    convertStringsToDoubles();
                } else {
                    
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
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
           benchEditText.setVisibility(View.VISIBLE);
        } else {
            benchEditText.setVisibility(View.GONE);
        }
    }

    public void onOverheadPressClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            overheadPressEditText.setVisibility(View.VISIBLE);
        } else {
            overheadPressEditText.setVisibility(View.GONE);
        }
    }

    public void onSquatClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            squatEditText.setVisibility(View.VISIBLE);
        } else {
            squatEditText.setVisibility(View.GONE);
        }
    }

    public void onDeadliftClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            deadliftEditText.setVisibility(View.VISIBLE);
        } else {
            deadliftEditText.setVisibility(View.GONE);
        }
    }

    public void onBarbellRowClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
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

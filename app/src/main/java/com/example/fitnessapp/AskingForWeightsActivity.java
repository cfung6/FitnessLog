package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AskingForWeightsActivity extends AppCompatActivity {

    Button submitButton;
    EditText benchEditText;
    EditText overheadPressEditText;
    EditText squatEditText;
    EditText deadliftEditText;
    EditText barbellRowEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);
        submitButton = (Button)findViewById(R.id.submit_weight_button);
        initializeEditTexts();
        limitEditTextsInput();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String benchInput = benchEditText.getText().toString();
                String overheadInput = overheadPressEditText.getText().toString();
                String squatInput = squatEditText.getText().toString();
                String deadliftInput = squatEditText.getText().toString();
                String barbellRowInput = barbellRowEditText.getText().toString();
            }
        });
    }

    private void initializeEditTexts() {
        benchEditText = (EditText)findViewById(R.id.textbox_bench);
        overheadPressEditText = (EditText)findViewById(R.id.textbox_overhead);
        squatEditText = (EditText)findViewById(R.id.textbox_squat);
        deadliftEditText = (EditText)findViewById(R.id.textbox_deadlift);
        barbellRowEditText = (EditText)findViewById(R.id.textbox_barbell_row);
    }

    private void limitEditTextsInput() {
        benchEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        overheadPressEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        squatEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        deadliftEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        barbellRowEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

}

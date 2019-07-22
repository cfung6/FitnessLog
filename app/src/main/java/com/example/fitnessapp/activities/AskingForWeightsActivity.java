package com.example.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.fitnessapp.DefaultWeights;
import com.example.fitnessapp.Levels;
import com.example.fitnessapp.R;
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
    private Levels levelChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);
        Intent intent = getIntent();
        levelChosen = (Levels) intent.getSerializableExtra("LEVEL_CHOSEN");
        submitButton = findViewById(R.id.submit_weight_button);
        initializeEditTexts();
        limitEditTextsInput();
        initializeBooleans();
        onSubmitButtonClick();
    }

    private void initializeBooleans() {
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
                initializeStringInputs();

                if (!isEmpty(benchString, overheadString, squatString, deadliftString, barbellRowString)) {
                    convertStringsToDoubles();
                    passWeightsToNext ();
                } else {
                    Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights are blank", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }
            }
        });
    }

    private void passWeightsToNext () {

        Intent intent;
        if (levelChosen == Levels.BEGINNER) {
            intent = new Intent(AskingForWeightsActivity.this, BeginnerActivity.class);
        } else if (levelChosen == Levels.INTERMEDIATE) {
            intent = new Intent(AskingForWeightsActivity.this, IntermediateActivity.class);
        } else  {
            intent = new Intent(AskingForWeightsActivity.this, AdvancedActivity.class);
        }
        intent.putExtra ("BENCH_PRESS_WEIGHT", getBenchInput());
        intent.putExtra ("OVERHEAD_PRESS_WEIGHT", getOverheadInput());
        intent.putExtra ("SQUAT_WEIGHT", getSquatInput());
        intent.putExtra ("DEADLIFT_WEIGHT", getDeadliftInput());
        intent.putExtra ("BARBELL_ROW_WEIGHT", getBarbellRowInput());
        startActivity(intent);
    }

    private void initializeStringInputs() {
        benchString = benchEditText.getText().toString();
        overheadString = overheadPressEditText.getText().toString();
        squatString = squatEditText.getText().toString();
        deadliftString = deadliftEditText.getText().toString();
        barbellRowString = barbellRowEditText.getText().toString();
    }

    private void convertStringsToDoubles () {

        if (!benchString.isEmpty() && !benchString.equals(".") && benchPressChecked){
            benchInput = Double.parseDouble(benchString);
        } else {
            benchInput = -1;
        }
        if (!overheadString.isEmpty() && !overheadString.equals(".") && overheadPressChecked) {
            overheadInput = Double.parseDouble(overheadString);
        } else {
            overheadInput = -1;
        }
        if (!squatString.isEmpty() && !squatString.equals(".") && squatChecked) {
            squatInput = Double.parseDouble(squatString);
        } else {
            squatInput = -1;
        }
        if (!deadliftString.isEmpty() && !deadliftString.equals(".") && deadliftChecked){
            deadliftInput = Double.parseDouble(deadliftString);
        } else {
            deadliftInput = -1;
        }
        if (!barbellRowString.isEmpty() && !barbellRowString.equals(".") && barbellRowChecked) {
            barbellRowInput = Double.parseDouble(barbellRowString);
        } else {
            barbellRowInput = -1;
        }
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
        if (benchInput == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.BENCH_PRESS_BEGINNER;
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.BENCH_PRESS_INTERMEDIATE;
            } else {
                return DefaultWeights.BENCH_PRESS_ADVANCED;
            }
        } else {
            return benchInput;
        }

    }

    public double getOverheadInput() {
        if (overheadInput == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.OVERHEAD_PRESS_BEGINNER;
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.OVERHEAD_PRESS_INTERMEDIATE;
            } else {
                return DefaultWeights.OVERHEAD_PRESS_ADVANCED;
            }
        } else {
            return overheadInput;
        }
    }

    public double getSquatInput() {
        if (squatInput == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.SQUAT_BEGINNER;
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.SQUAT_INTERMEDIATE;
            } else {
                return DefaultWeights.SQUAT_ADVANCED;
            }
        } else {
            return squatInput;
        }
    }

    public double getDeadliftInput() {
        if (deadliftInput == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.DEADLIFT_BEGINNER;
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.DEADLIFT_INTERMEDIATE;
            } else {
                return DefaultWeights.DEADLIFT_ADVANCED;
            }
        } else {
            return deadliftInput;
        }
    }

    public double getBarbellRowInput() {
        if (barbellRowInput == -1) {
            if (levelChosen == Levels.BEGINNER) {
                return DefaultWeights.BARBELL_ROW_BEGINNER;
            } else if (levelChosen == Levels.INTERMEDIATE) {
                return DefaultWeights.BARBELL_ROW_INTERMEDIATE;
            } else {
                return DefaultWeights.BARBELL_ROW_ADVANCED;
            }
        } else {
            return barbellRowInput;
        }
    }
}

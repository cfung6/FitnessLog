package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class AskingForWeightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_weights);

    }

    public void onBenchPressClick (View view) {

        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_bench);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

    public void onOverheadPressClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_overhead);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

    public void onSquatClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_squat);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

    public void onDeadliftClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_deadlift);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

    public void onBarbellRowClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_barbell_row);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

}

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

    public void onCheckboxClick (View view) {

        boolean checked = ((CheckBox) view).isChecked();
        EditText editText = findViewById(R.id.textbox_bench);

        if (checked) {
            editText.setVisibility(View.VISIBLE);
        }
        else {
            editText.setVisibility(View.GONE);
        }

    }
}

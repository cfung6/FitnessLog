package com.example.fitnesslog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.R;
import com.example.fitnesslog.Routine;

public class NewProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);
    }

    public void levelClicked(Routine routine) {
        Intent intent = new Intent(this, AskingForWeightsActivity.class);

        //Passing on which routine was chosen to next activity
        intent.putExtra("ROUTINE", routine);
        startActivity(intent);
    }

    public void beginnerLevelClicked(View view) {
        levelClicked(new Routine(1));
    }

    public void intermediateLevelClicked(View view) {
        levelClicked(new Routine(2));
    }

    public void advancedLevelClicked(View view) {
        levelClicked(new Routine(3));
    }

}

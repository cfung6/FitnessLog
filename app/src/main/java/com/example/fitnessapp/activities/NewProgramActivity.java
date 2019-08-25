package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.Levels;
import com.example.fitnessapp.R;

public class NewProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);
    }

    public void levelClicked(Levels level) {
        Intent intent = new Intent(this, AskingForWeightsActivity.class);

        //Passing on the level chosen to next activity
        intent.putExtra("LEVEL_CHOSEN", level);
        startActivity(intent);
    }

    public void beginnerLevelClicked(View view) {
        levelClicked(Levels.BEGINNER);
    }

    public void intermediateLevelClicked(View view) {
        levelClicked(Levels.INTERMEDIATE);
    }

    public void advancedLevelClicked(View view) {
        levelClicked(Levels.ADVANCED);
    }

}

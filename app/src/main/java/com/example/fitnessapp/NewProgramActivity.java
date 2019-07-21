package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewProgramActivity extends AppCompatActivity {

    private Levels levelClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);
    }

    public void levelClicked(View view, Levels level) {
        Intent intent = new Intent (this, AskingForWeightsActivity.class);
        intent.putExtra("LEVEL_CHOSEN", level);
        startActivity(intent);
    }

    public void beginnerLevelClicked (View view) {

        levelClicked(view, Levels.BEGINNER);
    }

    public void intermediateLevelClicked (View view) {

        levelClicked(view, Levels.INTERMEDIATE);
    }

    public void advancedLevelClicked (View view) {

        levelClicked(view, Levels.ADVANCED);
    }

}

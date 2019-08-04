package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newProgramClicked(View view) {
        Intent intent = new Intent (this, NewProgramActivity.class);
        startActivity(intent);
    }

    public void continueProgramClicked(View view) {

    }
}

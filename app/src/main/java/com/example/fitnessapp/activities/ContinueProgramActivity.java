package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.DatabaseHelper;

public class ContinueProgramActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);

        //If database is empty, defaults to new program
        if (databaseHelper.isEmpty()) {
            Intent intent = new Intent(this, NewProgramActivity.class);
            startActivity(intent);
        }
        databaseHelper.orderByDate();

    }
}

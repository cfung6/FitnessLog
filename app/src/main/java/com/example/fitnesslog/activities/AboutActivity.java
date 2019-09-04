package com.example.fitnesslog.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tv = findViewById(R.id.message);
        String message = "Fitness App is an application that generates and tracks workouts. " +
                "First, you select a level (beginner, intermediate, or advanced) " +
                "based on your experience in the gym as well as your availability to go to the gym. " +
                "Next, you will be prompted for the weight " +
                "that you are able to lift for certain exercises. " +
                "Workouts will then be generated for you that target major muscle groups of " +
                "the body setting a goal for you to lift, as well as the number of " +
                "sets and reps that you should try to attempt. Your following workouts will " +
                "be determined based on how you did in the current workout. Workouts will be stored in our " +
                "calendar tool, where you can check and track your progress!";
        tv.setText(message);
    }
}

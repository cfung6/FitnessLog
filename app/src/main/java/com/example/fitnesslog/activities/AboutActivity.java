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
        String message = "FitnessLog is an application that generates and tracks workouts. " +
                "First, you select a level (beginner, intermediate, or advanced) " +
                "based on your experience in the gym as well as your availability to go to the gym. " +
                "Next, you will be prompted for the weight " +
                "that you are able to lift for certain exercises. " +
                "This app will generate workouts and " +
                "set a goal weight for you to lift, as well as the number of " +
                "sets and reps that you should try to attempt. Your following workouts will " +
                "be determined based on how you did in the current workout. Workouts will be stored in our " +
                "calendar tool, where you can check and track your progress!";
        tv.setText(message);

        tv = findViewById(R.id.beginner_message);
        message = "This program is a three times per week routine that focuses on linear progression" +
                " using the five main compound exercises. After successfully completing the necessary weight" +
                " and reps for each exercise, increase the weight by 5 pounds the next time you have to do the" +
                " exercise.";
        tv.setText(message);

        tv = findViewById(R.id.intermediate_message);
        message = "This intermediate program is done three times per week. The first day of the week focuses on volume," +
                " requiring 5 sets of 5 for squat and either bench or overhead press (using 90 percent of 5 rep max)." +
                " The second workout, the light workout, contains squat and bench/overhead press (whichever one was not done" +
                " in the first workout) using 81 percent of your 5 rep max. The last workout, the intensity day, is when" +
                " you try to achieve a new 5 rep max in squat and the exercise done in the first workout.";
        tv.setText(message);

        tv = findViewById(R.id.advanced_message);
        message = "This advanced program focuses on volume and hypertrophy and should be done four to six times a week." +
                " This routine contains more than triple the exercises of the other two routines." +
                " After each exercise is completed successfully, five pounds is increased to that exercise.";
        tv.setText(message);
    }
}

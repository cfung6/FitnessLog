package com.example.fitnessapp.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;

public class ExerciseTutorials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        initializeBenchPress();
        initializeSquat();
    }


    // EFFECTS: sets text within the start and end index to bold and sets the text for the given view
    private void boldText(String text, int startIndex, int endIndex, TextView textView) {
        SpannableString spannableString = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void initializeSquat() {
        TextView stepOneView = findViewById(R.id.step_one_squat);
        String stepOneText = "1. Stand with the bar on your upper-back, and your feet shoulder-width apart.";
        boldText(stepOneText, 30, 40, stepOneView);

        TextView stepTwoView = findViewById(R.id.step_two_squat);
        String stepTwoText = "2. Squat down by pushing your knees to the side while moving hips back";
        boldText(stepTwoText, 9, 13, stepTwoView);

        TextView stepThreeView = findViewById(R.id.step_three_squat);
        String stepThreeText = "3. Break parallel by Squatting down until your hips are lower than your knees";
        boldText(stepThreeText, 3, 17, stepThreeView);

        TextView stepFourView = findViewById(R.id.step_four_squat);
        String stepFourText = "4. Squat back up while keeping your knees out and chest up";
        boldText(stepFourText, 36, 45, stepFourView);

        TextView stepFiveView = findViewById(R.id.step_five_squat);
        String stepFiveText = "5. Stand with your hips and knees locked at the top";
        boldText(stepFiveText, 34, 40, stepFiveView);
    }
    
    private void initializeBenchPress() {
        TextView stepOneView = findViewById(R.id.step_one_bench);
        String stepOneText = "1. Setup. Lie on the flat bench with your eyes under the bar. " +
                "Lift your chest and squeeze your shoulder-blades. Feet flat on the floor.";
        boldText(stepOneText, 3, 8, stepOneView);

        TextView stepTwoView = findViewById(R.id.step_two_bench);
        String stepTwoText = "2. Grab the bar. Put your pinky on the ring marks of your bar. " +
                "Hold the bar in the base of your palm with a full grip and straight wrists.";
        boldText(stepTwoText, 3, 15, stepTwoView);

        TextView stepThreeView = findViewById(R.id.step_three_bench);
        String stepThreeText = "3. Unrack. Take a big breath and unrack the bar by straightening " +
                "your arms. Move the bar over your shoulders with your elbows locked.";
        boldText(stepThreeText, 3, 9, stepThreeView);

        TextView stepFourView = findViewById(R.id.step_four_bench);
        String stepFourText = "4. Lower the bar. Lower it to your mid-chest while tucking your " +
                "elbows 75°. Keep your forearms vertical. Hold your breath at the bottom.";
        boldText(stepFourText, 3, 16, stepFourView);

        TextView stepFiveView = findViewById(R.id.step_five_bench);
        String stepFiveText = "5. Press. Press the bar from your mid-chest to above your shoulders. " +
                "Keep your butt on the bench. Lock your elbows at the top. Breathe.";
        boldText(stepFiveText, 3, 8, stepFiveView);
    }
}

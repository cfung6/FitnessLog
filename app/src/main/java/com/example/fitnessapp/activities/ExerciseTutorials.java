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
        initializeOverhead();
        initializeDeadLift();
        initializeBarbellRow();
    }


    // EFFECTS: sets text within the start and end index to bold and sets the text for the given view
    private void boldText(String text, int startIndex, int endIndex, TextView textView) {
        SpannableString spannableString = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void initializeDeadLift() {
        TextView stepOneView = findViewById(R.id.step_one_deadlift);
        String stepOneText = "1. Stand with your mid-foot under the barbell.";
        boldText(stepOneText, 28, 33, stepOneView);

        TextView stepTwoView = findViewById(R.id.step_two_deadlift);
        String stepTwoText = "2. Bend over and grab the bar with a shoulder-width grip.";
        boldText(stepTwoText, 0, 0, stepTwoView);

        TextView stepThreeView = findViewById(R.id.step_three_deadlift);
        String stepThreeText = "3. Bend your knees until your shins touch the bar.";
        boldText(stepThreeText, 3, 18, stepThreeView);

        TextView stepFourView = findViewById(R.id.step_four_deadlift);
        String stepFourText = "4. Lift your chest up and straighten your lower back.";
        boldText(stepFourText, 26, 36, stepFourView);

        TextView stepFiveView = findViewById(R.id.step_five_deadlift);
        String stepFiveText = "5. Take a big breath, hold it, and stand up with the weight.";
        boldText(stepFiveText, 10, 20, stepFiveView);
    }

    private void initializeOverhead() {
        TextView stepOneView = findViewById(R.id.step_one_overhead);
        String stepOneText = "1. Stand with the bar on your front shoulders, and your hands next to your shoulders.";
        boldText(stepOneText, 0, 0, stepOneView);

        TextView stepTwoView = findViewById(R.id.step_two_overhead);
        String stepTwoText = "2. Press the bar over your head, until it’s balanced over your shoulders and mid-foot.";
        boldText(stepTwoText, 17, 21, stepTwoView);

        TextView stepThreeView = findViewById(R.id.step_three_overhead);
        String stepThreeText = "3. Lock your elbows at the top, and shrug your shoulders to the ceiling.";
        boldText(stepThreeText, 3, 19, stepThreeView);
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

    private void initializeBarbellRow() {
        TextView stepOneView = findViewById(R.id.step_one_barbellrow);
        String stepOneText = "1. Stand with your mid-foot under the bar (medium stance).";
        boldText(stepOneText, 28, 33, stepOneView);

        TextView stepTwoView = findViewById(R.id.step_two_barbellrow);
        String stepTwoText = "2. Bend over and grab the bar (palms down, medium-grip).";
        boldText(stepTwoText, 3, 12, stepTwoView);

        TextView stepThreeView = findViewById(R.id.step_three_barbellrow);
        String stepThreeText = "3. Unlock your knees while keeping your hips high.";
        boldText(stepThreeText, 3, 9, stepThreeView);

        TextView stepFourView = findViewById(R.id.step_four_barbellrow);
        String stepFourText = "4. Lift your chest and straighten your back.";
        boldText(stepFourText, 23, 33, stepFourView);

        TextView stepFiveView = findViewById(R.id.step_five_barbellrow);
        String stepFiveText = "5. Pull the bar against your lower chest.";
        boldText(stepFiveText, 3, 7, stepFiveView);

    }
}

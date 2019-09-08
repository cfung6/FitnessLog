package com.example.fitnesslog.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.fitnesslog.Exercise;
import com.example.fitnesslog.TodaysDate;
import com.example.fitnesslog.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AdvancedActivity extends BaseWorkoutLogActivity {

     /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
        INCLINE DUMBBELL PRESS
        LAT PULLDOWN
        BARBELL CURL
        HAMMER CURL
        ROPE PULLDOWN
        OVERHEAD TRICEP EXTENSION
        LATERAL RAISE
        FACE PULLS
     */

    private double[] exerciseWeights;
    private String[] exerciseNames;
    List<Workout> workouts;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        TodaysDate today = new TodaysDate();

        //Getting weights and names from previous activity
        exerciseWeights = intent.getDoubleArrayExtra("WEIGHTS");
        exerciseNames = intent.getStringArrayExtra("NAMES");

        long currentTime = intent.getLongExtra("TIME", Calendar.getInstance().getTimeInMillis());
        String todaysDate = intent.getStringExtra("DATE");
        String previousActivity = intent.getStringExtra("ACTIVITY");

        if (todaysDate == null || todaysDate.isEmpty()) {
            todaysDate = today.getDateString();
        }

        workouts = new ArrayList<>();
        exercises = new ArrayList<>();

        setRoutineID(3);
        setCurrentTime(currentTime);
        setTodaysDate(todaysDate);
        setPreviousActivity(previousActivity);
        setExerciseNames(exerciseNames);
        setExerciseWeights(exerciseWeights);
        initializeExercises();
        setExercises(exercises);
        initializeWorkouts();
        setWorkouts(workouts);
        initializeRoutine("Advanced");
        initializeCurrentWorkout(getTodaysDate());
        createAbstractXML(currentWorkout);
    }

    protected void initializeExercises() {
        List<Integer> sevenReps = new ArrayList<>(Arrays.asList(7, 7, 7));
        List<Integer> tenReps = new ArrayList<>(Arrays.asList(10, 10, 10));
        List<Integer> twelveReps = new ArrayList<>(Arrays.asList(12, 12, 12));
        List<Integer> fiveReps = new ArrayList<>(Arrays.asList(5, 5, 5));

        int incr = 5;
        int perc = 1;

        for (int i = 0; i < 2; i++) {
            exercises.add(new Exercise(exerciseNames[i], round(exerciseWeights[i]), incr, perc, sevenReps));
        }

        for (int i = 2; i < 4; i++) {
            exercises.add(new Exercise(exerciseNames[i], round(exerciseWeights[i]), incr, perc, fiveReps));
        }

        exercises.add(new Exercise(exerciseNames[4], round(exerciseWeights[4]), incr, perc, sevenReps));

        for (int i = 5; i < 7; i++) {
            exercises.add(new Exercise(exerciseNames[i], exerciseWeights[i], incr, perc, tenReps));
        }

        for (int i = 7; i < 13; i++) {
            exercises.add(new Exercise(exerciseNames[i], exerciseWeights[i], incr, perc, twelveReps));
        }
    }

    protected void initializeWorkouts() {
        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Bench Press"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Incline Dumbbell Press"),
                getExerciseByName("Lateral Raise"),
                getExerciseByName("Rope Pulldown"),
                getExerciseByName("Overhead Tricep Extension"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Barbell Row"),
                getExerciseByName("Lat Pulldown"),
                getExerciseByName("Face Pulls"),
                getExerciseByName("Barbell Curl"),
                getExerciseByName("Hammer Curl"))));

        Workout workoutC = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Deadlift"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
        workouts.add(workoutC);
    }
}

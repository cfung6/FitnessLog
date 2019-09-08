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

public class BeginnerActivity extends BaseWorkoutLogActivity {

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
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

        setRoutineID(1);
        setCurrentTime(currentTime);
        setTodaysDate(todaysDate);
        setPreviousActivity(previousActivity);
        setExerciseNames(exerciseNames);
        setExerciseWeights(exerciseWeights);
        initializeExercises();
        setExercises(exercises);
        initializeWorkouts();
        setWorkouts(workouts);
        initializeRoutine("Beginner");
        initializeCurrentWorkout(getTodaysDate());
        createAbstractXML(currentWorkout);
    }

    protected void initializeExercises() {
        List<Integer> goalReps = new ArrayList<>(Arrays.asList(5, 5, 5));
        List<Integer> deadliftGoal = new ArrayList<>(Arrays.asList(5));
        int incr = 5;
        int perc = 1;

        exercises.add(new Exercise("Bench Press", exerciseWeights[0], incr, perc, goalReps));
        exercises.add(new Exercise("Overhead Press", exerciseWeights[1], incr, perc, goalReps));
        exercises.add(new Exercise("Squat", exerciseWeights[2], incr, perc, goalReps));
        exercises.add(new Exercise("Deadlift", exerciseWeights[3], incr, perc, deadliftGoal));
        exercises.add(new Exercise("Barbell Row", exerciseWeights[4], incr, perc, goalReps));
    }

    protected void initializeWorkouts() {
        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Bench Press"),
                getExerciseByName("Barbell Row"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Deadlift"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
    }
}

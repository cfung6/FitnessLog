package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntermediateActivity extends BaseWorkoutLogActivity {

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private int[] exerciseWeights;
    private String[] exerciseNames;
    List<Workout> workouts;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        //Getting weights and names from previous activity
        exerciseWeights = intent.getIntArrayExtra("WEIGHTS");
        exerciseNames = intent.getStringArrayExtra("NAMES");

        workouts = new ArrayList<>();
        exercises = new ArrayList<>();

        setContext(this);
        setRoutineID(2);
        setIncrement(5);
        setPercentage(1);
        setExerciseNames(exerciseNames);
        setExerciseWeights(exerciseWeights);
    }

    @Override
    protected void initializeExercises() {
        List<Integer> volumeDayGoalReps = new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5));

        exercises.add(new Exercise("Bench Press1", round(.9 * exerciseWeights[0]), 0, 1, volumeDayGoalReps));
    }

    @Override
    protected void initializeWorkouts() {
        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat1"),
                getExerciseByName("Bench Press1"),
                getExerciseByName("Deadlift"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat2"),
                getExerciseByName("Overhead Press2"))));

        Workout workoutC = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat3"),
                getExerciseByName("Bench Press3"),
                getExerciseByName("Barbell Row"))));

        Workout workoutD = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat1"),
                getExerciseByName("Overhead Press1"),
                getExerciseByName("Deadlift"))));

        Workout workoutE = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat2"),
                getExerciseByName("Bench Press2"))));

        Workout workoutF = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat3"),
                getExerciseByName("Overhead Press3"),
                getExerciseByName("Barbell Row"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
        workouts.add(workoutC);
        workouts.add(workoutD);
        workouts.add(workoutE);
        workouts.add(workoutF);
    }
}

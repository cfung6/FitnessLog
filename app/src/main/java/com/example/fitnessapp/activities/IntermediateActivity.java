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

    private double[] exerciseWeights;
    private String[] exerciseNames;
    List<Workout> workouts;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        //Getting weights and names from previous activity
        exerciseWeights = intent.getDoubleArrayExtra("WEIGHTS");
        exerciseNames = intent.getStringArrayExtra("NAMES");

        workouts = new ArrayList<>();
        exercises = new ArrayList<>();

        setRoutineID(2);
        setIncrement(5);
        setPercentage(1);
        setExerciseNames(exerciseNames);
        setExerciseWeights(exerciseWeights);
        initializeExercises();
        setExercises(exercises);
        initializeWorkouts();
        setWorkouts(workouts);
        initializeRoutine("Intermediate");
        initializeCurrentWorkout();
        createAbstractXML(currentWorkout);
    }

    @Override
    protected void initializeExercises() {
        List<Integer> volumeDayGoalReps = new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5));
        List<Integer> squatLightReps = new ArrayList<>(Arrays.asList(5, 5));
        List<Integer> lightDayGoalReps = new ArrayList<>(Arrays.asList(5, 5, 5));
        List<Integer> intensityDayGoalReps = new ArrayList<>(Arrays.asList(5));

        exercises.add(new Exercise("Bench Press", exerciseWeights[0], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Bench Press1", round(.9 * exerciseWeights[0]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Bench Press2", round(.81 * exerciseWeights[0]), 0, 1, lightDayGoalReps));

        exercises.add(new Exercise("Overhead Press", exerciseWeights[1], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Overhead Press1", round(.9 * exerciseWeights[1]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Overhead Press2", round(.81 * exerciseWeights[1]), 0, 1, lightDayGoalReps));

        exercises.add(new Exercise("Squat", exerciseWeights[2], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Squat1", round(.9 * exerciseWeights[2]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Squat2", round(.72 * exerciseWeights[2]), 0, 1, squatLightReps));

        exercises.add(new Exercise("Deadlift", round(.9 * exerciseWeights[3]), 5, 1, intensityDayGoalReps));

        exercises.add(new Exercise("Barbell Row", exerciseWeights[4], 5, 1, lightDayGoalReps));
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
                getExerciseByName("Squat"),
                getExerciseByName("Bench Press"),
                getExerciseByName("Barbell Row"))));

        Workout workoutD = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat1"),
                getExerciseByName("Overhead Press1"),
                getExerciseByName("Deadlift"))));

        Workout workoutE = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat2"),
                getExerciseByName("Bench Press2"))));

        Workout workoutF = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Barbell Row"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
        workouts.add(workoutC);
        workouts.add(workoutD);
        workouts.add(workoutE);
        workouts.add(workoutF);
    }
}

package com.example.fitnessapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class Workout {
    private String name;
    private List<Exercise> exercises;
    private int currentExercise;


    public Workout(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
        currentExercise = 0;
    }

    public void addExercise (Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise (Exercise exercise) {

        this.exercises.remove(exercise);
    }

    public String getName () {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Exercise getExerciseAtIndex(int index) {
        return exercises.get(index);
    }

    public Exercise getCurrentExercise() {
        return exercises.get(currentExercise);
    }

    public void nextExercise() {
        int length = exercises.size();
        currentExercise++;
        currentExercise %= length;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;
        return Objects.equals(this.name, workout.getName()) &&
                Objects.equals(this.exercises, workout.getExercises());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, exercises);
    }

}

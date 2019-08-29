package com.example.fitnessapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class Workout {

    private List<Exercise> exercises;

    public Workout(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Exercise getExerciseAtIndex(int index) {
        return exercises.get(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;
        return Objects.equals(this.exercises, workout.getExercises());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(exercises);
    }
}

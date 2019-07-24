package com.example.fitnessapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Set;
import java.util.Objects;

public class Workout {
    private String name;
    private Set<Exercise> exercises;

    public Workout (String name, Set<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public void addExercise (Exercise exercise) {

        this.exercises.add(exercise);
    }

    public void removeExercise (Exercise exercise) {

        if (this.exercises.contains(exercise)) {

            this.exercises.remove (exercise);
        }
    }

    public String getName () {
        return name;
    }

    public Set<Exercise> getExercises () {
        return exercises;
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

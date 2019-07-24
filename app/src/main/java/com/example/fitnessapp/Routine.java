package com.example.fitnessapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class Routine {
    private int week;
    private int numOfWorkouts;
    private Calendar c;
    private String name;
    private Set<Workout> workouts;

    public Routine(int numOfWorkouts, String name, Set<Workout> workouts) {
        week = 1;
        this.numOfWorkouts = numOfWorkouts;
        this.name = name;
        this.workouts = workouts;
        c = Calendar.getInstance();
    }

    // EFFECTS: calls addWeek every Sunday
    public void updateWeek() {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            addWeek();
        }
    }

    private void addWeek() {
        week++;
    }

    public int getWeek() {
        return week;
    }

    public void addWorkout (Workout workout) {

        this.workouts.add(workout);
    }

}

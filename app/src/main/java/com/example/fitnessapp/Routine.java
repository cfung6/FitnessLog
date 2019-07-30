package com.example.fitnessapp;


import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Routine {
    private int week;
    private int numOfWorkouts;
    private Calendar c;
    private String name;
    private List<Workout> workouts;
    private int currentWorkout;

    public Routine(int numOfWorkouts, String name, List<Workout> workouts) {
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

    public int getNumOfWorkouts() {
        return numOfWorkouts;
    }

    public Calendar getCurrentCalendar() {
        return c;
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public Workout getCurrentWorkout() {
        return workouts.get(currentWorkout);
    }

    public void nextWorkout() {
        int length = workouts.size();
        currentWorkout++;
        currentWorkout %= length;
    }
}

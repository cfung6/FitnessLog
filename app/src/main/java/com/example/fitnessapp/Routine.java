package com.example.fitnessapp;

import java.util.ArrayList;
import java.util.Calendar;

class Routine {
    private int week;
    private int numOfWorkouts;
    private Calendar c;
    private String name;
    private ArrayList<Workout> workouts;

    public Routine(int numOfWorkouts, String name, ArrayList<Workout> workouts) {
        week = 1;
        this.numOfWorkouts = numOfWorkouts;
        this.name = name;
        this.workouts = workouts;
        c = Calendar.getInstance();
    }

    // EFFECTS: calls addWeek every Sunday
    public void updateWeek() {
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            addWeek();
        }
    }

    private void addWeek() {
        week++;
    }

    public int getWeek() {
        return week;
    }


}

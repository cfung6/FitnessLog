package com.example.fitnessapp;

import java.util.List;

public class Routine {

    private int numOfWorkouts;
    private String name;
    private List<Workout> workouts;
    private int currentWorkout;

    // EFFECTS: constructs a routine that has a name, a list of workouts, and sets the week of
    //          the routine to 1
    public Routine(int numOfWorkouts, String name, List<Workout> workouts) {
        this.numOfWorkouts = numOfWorkouts;
        this.name = name;
        this.workouts = workouts;
        //Will need to change currentWorkout to be dependent on previous entry in database
        currentWorkout = 0;
    }

    public int getNumOfWorkouts() {
        return numOfWorkouts;
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

}

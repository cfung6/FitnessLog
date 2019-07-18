package com.example.fitnessapp;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Exercise {

    private String name;
    private String description;
    private double weight;
    private Map<Double, List<Integer>> goal;
    private Map<Double, List<Integer>> actual;
    private List<Integer> reps;

    public Exercise (String name, String description, double weight, List<Integer> reps) {

        this.name = name;
        this.description = description;
        this.goal.put(weight, reps);
    }

    public boolean completeSet () {

        //Write to file
    }

    public List<Integer> getGoalReps (double weight) {


    }

    public void increaseWeight (int increment) {

        //Read from file
    }

    public String getName () {

        return this.getName();
    }

}

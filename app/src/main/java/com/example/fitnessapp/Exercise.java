package com.example.fitnessapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Exercise {


    private String name;
    private String description;
    private double weight;
    private Map<Double, List<Integer>> goal;
    private Map<Double, List<Integer>> actual;

    public Exercise (String name, String description) {
        this.name = name;
        this.description = description;
        this.actual = new HashMap<Double, List<Integer>>();
        this.goal = new HashMap<Double, List<Integer>>();
    }

    public void addWeight(Double weight, List<Integer> reps) {
        this.goal.put(weight, reps);
    }

    public String getName () {
        return this.getName();
    }




    public boolean completeSet () {
        //Write to file
        return false;
    }

//    public List<Integer> getGoalReps (double weight) {
//
//
//    }

    public void increaseWeight (int increment) {
        //Read from file
    }


}

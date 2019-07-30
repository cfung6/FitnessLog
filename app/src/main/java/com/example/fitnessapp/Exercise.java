package com.example.fitnessapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Exercise{

    private String name;
    private List<Integer> actualRepsDone;
    private List<Integer> goalReps;
    private double capableWeight;
    private double goalWeight;
    private int increment;
    private double percentage;
    private List<Double> actualWeightList;
    private boolean pass;

    public Exercise(String name, double weight, int increment, double percentage, List<Integer> reps) {
        this.name = name;
        this.increment = increment;
        this.percentage = percentage;
        capableWeight = weight;
        goalWeight = weight * percentage + increment;
        actualRepsDone = new ArrayList<>();
        actualWeightList = new ArrayList<>();
        goalReps = reps;
        pass = true;
    }

    public void addRepsDone(double weight, int reps) {
        if (weight < goalWeight) {
            pass = false;
        }
        actualWeightList.add(weight);
        actualRepsDone.add(reps);
    }

    public void increaseWeight () {
        if (pass && completeExercise()) {
            setActualAndGoalWeight (setNewCapableWeight());
        }
    }

    private boolean completeExercise() {
        if (actualRepsDone.size() < goalReps.size()) {
            return false;
        }
        Collections.sort(actualRepsDone, Collections.<Integer>reverseOrder());
        Collections.sort(goalReps, Collections.<Integer>reverseOrder());
        for (int i = 0; i < goalReps.size(); i++) {
            if (actualRepsDone.get(i) < goalReps.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void setActualAndGoalWeight(double weight) {
        capableWeight = weight;
        goalWeight = weight * percentage + increment;
    }

    private double setNewCapableWeight(){
        Collections.sort(actualWeightList);
        return actualWeightList.get(0);
    }

    public boolean getPass() {
        return pass && completeExercise();
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getActualRepsDone() {
        return actualRepsDone;
    }

    public List<Integer> getGoalReps() {
        return goalReps;
    }

    public double getCapableWeight() {
        return capableWeight;
    }

    public int getIncrement() {
        return increment;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<Double> getActualWeightList() {
        return actualWeightList;
    }

}

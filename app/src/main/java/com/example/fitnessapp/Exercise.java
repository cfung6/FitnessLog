package com.example.fitnessapp;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise {


    private String name;
    private Map<Double, List<Integer>> goal;
    private Map<Double, List<Integer>> actual;
    private Context context;
    private FileOutputStream fileOutputStream;

    public Exercise (String name, Context context) {
        this.name = name;
        this.actual = new HashMap<>();
        this.goal = new HashMap<>();
        this.context = context;
    }

    public void addWeight(double weight, List<Integer> reps) {
        this.goal.put(weight, reps);
        writeToFile(weight, reps);
    }

    public String getName () {
        return this.name;
    }


    public boolean completeSet () {
        //Write to file
        return false;
    }

//    public List<Integer> getGoalReps (double weight) {
//
//
//    }

    public void increaseWeight (int increment, double weight, List<Integer> reps) {
        //Read from file
        double newWeight = weight + increment;

        this.goal.put(weight,reps);
    }

    private void writeToFile (Double weight, List<Integer> reps) {

        String str = this.name.trim().toLowerCase() + " " + weight + " " + listToString(reps) + "\n";

        try {
            fileOutputStream = this.context.openFileOutput("beginner_data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String listToString (List<Integer> reps) {

        String str = "";

        for (int num : reps) {

            str += num + " ";
        }

        str += "\b";

        return str;
    }

}

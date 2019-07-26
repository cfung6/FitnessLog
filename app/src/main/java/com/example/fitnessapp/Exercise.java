package com.example.fitnessapp;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise{
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

    public void addWeightDone(double weight, List<Integer> reps) {
        this.actual.put(weight, reps);
        increaseWeight(5, weight, reps);
        writeToFile(weight, reps);
    }

    public void addCompletedReps (double weight, int reps) {

        if (!this.actual.containsKey(weight)) {
            this.actual.put(weight, new ArrayList<Integer>());
        }
        this.actual.get(weight).add(reps);
    }

    public void setInitalGoalReps (double weight, List<Integer> reps) {

    }

    public String getName () {
        return this.name;
    }

    private boolean completeSet () {
        Set<Double> goalWeights = this.goal.keySet();
        Set<Double> actualWeights = this.actual.keySet();
        List<Double> goalWeightsList = new ArrayList<> (goalWeights);
        List<Double> actualWeightsList = new ArrayList<> (actualWeights);

        //Sorts the list of weights in the hashmaps from highest to lowest
        Collections.sort(goalWeightsList, new Comparator<Double>() {
            @Override
            public int compare (Double d1, Double d2) {
                return (int)(d2-d1);
            }
        });
        Collections.sort(actualWeightsList, new Comparator<Double>() {
            @Override
            public int compare (Double d1, Double d2) {
                return (int)(d2-d1);
            }
        });

        //Checking if the required amount of weights are done
        if (this.actual.keySet().size() < this.goal.keySet().size()) {
            Log.d("myTag", "1");
            return false;
        }

        for (int i = 0; i < this.goal.keySet().size(); i++) {
            if (actualWeightsList.get(i) >= goalWeightsList.get(i)) {
                List<Integer> actualRepsList = actual.get(actualWeightsList.get(i));
                List<Integer> goalRepsList = goal.get(goalWeightsList.get(i));

                //Checking if required amount of sets are done
                if (actualRepsList.size() >= goalRepsList.size()) {
                    for (int j = 0; j < goalRepsList.size(); j++) {
                        if (actualRepsList.get(j) < goalRepsList.get(j)) {
                            Log.d("myTag", "2");
                            return false;
                        }
                    }
                } else {
                    Log.d("myTag", "3");
                    return false;
                }
            } else {
                Log.d("myTag", "4");
                return false;
            }
        }
        Log.d("myTag", "5");
        return true;
    }




    public Map<Double,List<Integer>> getGoalMap () {
        return this.goal;
    }

    public Map<Double,List<Integer>> getActualMap () {
        return this.actual;
    }
    private void increaseWeight (int increment, double weight, List<Integer> reps) {
        if (completeSet()) {
            double newWeight = weight + increment;
            this.goal.put(newWeight,reps);
        }
        this.actual.remove(weight-increment);
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

    public Map<Double, List<Integer>> getActualMap() {
        return actual;
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

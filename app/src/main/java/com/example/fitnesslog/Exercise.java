package com.example.fitnesslog;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise implements Parcelable {

    private String name;

    private List<Integer> actualRepsDone;
    private List<Integer> goalReps;
    private List<Double> actualWeightList;

    private double goalWeight;

    private int increment;
    private double percentage;

    private boolean pass;
    private boolean weightIncreased;

    public Exercise(String name, double weight, int increment, double percentage, List<Integer> reps) {
        this.name = name;
        this.increment = increment;
        this.percentage = percentage;
        goalWeight = weight * percentage + increment;
        actualRepsDone = new ArrayList<>();
        actualWeightList = new ArrayList<>();
        goalReps = reps;
        pass = true;
        weightIncreased = false;
    }

    // EFFECTS: called when submit button is pressed
    //          if the weight completed by the user is less than the goal weight,
    //          pass is set to fail
    public void addRepsDone(double weight, int reps) {
        if (weight < goalWeight) {
            pass = false;
        }
        actualWeightList.add(weight);
        actualRepsDone.add(reps);
    }

    // EFFECTS: empties all user input for weight and reps
    public void removeRepsDone() {
        actualWeightList.clear();
        actualRepsDone.clear();
        pass = true;
    }

    // EFFECTS: if user passed the prescribed goal reps/sets/weight, and set the new capable
    //          and goal weight
    public void increaseWeight() {
        if (passOrFail()) {
            setActualAndGoalWeight(getNewCapableWeight());
        }
    }

    // EFFECTS: Determines if an exercise is complete. Returns false if the required number of sets
    //          is not done or if any of the reps done by the user is less than required. Otherwise,
    //          returns true
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

    // EFFECTS: sets the weight that the user is capable of lifting and sets the goal weight
    private void setActualAndGoalWeight(double weight) {
        goalWeight = weight * percentage + increment;
    }

    // EFFECTS: returns the lowest weight completed by the user in the current workout
    private double getNewCapableWeight() {
        Collections.sort(actualWeightList);
        return actualWeightList.get(0);
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(double d) {
        goalWeight = d;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGoalReps() {
        return goalReps;
    }

    public int getIncrement() {
        return increment;
    }

    public double getPercentage() {
        return percentage;
    }

    public boolean passOrFail() {
        return pass && completeExercise();
    }

    public boolean isWeightIncreased() {
        return weightIncreased;
    }

    public void setWeightIncreased(boolean b) {
        weightIncreased = b;
    }

    // EFFECTS: returns the capable weight that was used to determine the goal weight
    public double getCapableWeight() {
        return (goalWeight - increment) / percentage;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            actualRepsDone = new ArrayList<Integer>();
            in.readList(actualRepsDone, Integer.class.getClassLoader());
        } else {
            actualRepsDone = null;
        }
        if (in.readByte() == 0x01) {
            goalReps = new ArrayList<Integer>();
            in.readList(goalReps, Integer.class.getClassLoader());
        } else {
            goalReps = null;
        }
        if (in.readByte() == 0x01) {
            actualWeightList = new ArrayList<Double>();
            in.readList(actualWeightList, Double.class.getClassLoader());
        } else {
            actualWeightList = null;
        }
        goalWeight = in.readDouble();
        increment = in.readInt();
        percentage = in.readDouble();
        pass = in.readByte() != 0x00;
        weightIncreased = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (actualRepsDone == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(actualRepsDone);
        }
        if (goalReps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(goalReps);
        }
        if (actualWeightList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(actualWeightList);
        }
        dest.writeDouble(goalWeight);
        dest.writeInt(increment);
        dest.writeDouble(percentage);
        dest.writeByte((byte) (pass ? 0x01 : 0x00));
        dest.writeByte((byte) (weightIncreased ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}

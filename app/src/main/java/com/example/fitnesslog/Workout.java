package com.example.fitnesslog;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class Workout implements Parcelable {

    private List<Exercise> exercises;

    public Workout(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Workout(Parcel in) {
        exercises = in.createTypedArrayList(Exercise.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(exercises);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Exercise getExerciseAtIndex(int index) {
        return exercises.get(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout)) return false;
        Workout workout = (Workout) o;
        return Objects.equals(this.exercises, workout.getExercises());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(exercises);
    }
}

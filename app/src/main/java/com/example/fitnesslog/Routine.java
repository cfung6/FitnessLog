package com.example.fitnesslog;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Routine implements Parcelable {

    private String name;
    private int routineID;
    private String tableName;

    private double[] exerciseWeights;
    private List<Exercise> exercises;
    private String[] exerciseNames;
    private List<Workout> workouts;

    public Routine(int routineID) {
        this.routineID = routineID;
        workouts = new ArrayList<>();
        exercises = new ArrayList<>();

        initializeNames();
    }

    public Routine(Parcel in) {
        name = in.readString();
        routineID = in.readInt();
        tableName = in.readString();
        exerciseWeights = in.createDoubleArray();
        exerciseNames = in.createStringArray();
        if (in.readByte() == 0x01) {
            exercises = new ArrayList<>();
            in.readList(exercises, Exercise.class.getClassLoader());
        } else {
            exercises = null;
        }
        if (in.readByte() == 0x01) {
            workouts = new ArrayList<>();
            in.readList(workouts, Workout.class.getClassLoader());
        } else {
            workouts = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(routineID);
        parcel.writeString(tableName);
        parcel.writeDoubleArray(exerciseWeights);
        parcel.writeStringArray(exerciseNames);
        if (exercises == null) {
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeList(exercises);
        }
        if (workouts == null) {
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeList(workouts);
        }

    }

    public static final Creator<Routine> CREATOR = new Creator<Routine>() {
        @Override
        public Routine createFromParcel(Parcel in) {
            return new Routine(in);
        }

        @Override
        public Routine[] newArray(int size) {
            return new Routine[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTable() {
        return tableName;
    }

    public int getRoutineID() {
        return routineID;
    }

    public String[] getExerciseNames() {
        return exerciseNames;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    //Sets the capable weights array
    public void setExerciseWeights(double[] weights) {
        exerciseWeights = weights;
        initializeExAndWork();
    }

    public Workout getCurrentWorkout(DatabaseHelper databaseHelper) {
        int currentWorkoutNum = getCurrentWorkoutNum(databaseHelper);
        return workouts.get(currentWorkoutNum);
    }

    public void insertRoutineData(int workoutIndex, String exerciseName, DatabaseHelper databaseHelper) {
        if (routineID == 1) {
            databaseHelper.insertBeginnerRoutineData(workoutIndex, exerciseName);
        } else if (routineID == 2) {
            databaseHelper.insertIntermediateRoutineData(workoutIndex, exerciseName);
        } else if (routineID == 3) {
            databaseHelper.insertAdvancedRoutineData(workoutIndex, exerciseName);
        } else {
            throw new NullPointerException();
        }
    }

    // EFFECTS: initializes the capable weight of all the exercises
    public void initializeCapableWeight(DatabaseHelper databaseHelper, int routineID, String date) {
        exerciseWeights = new double[exerciseNames.length];
        int workoutNum = databaseHelper.getWorkoutNumByDate(tableName, date);

        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseWeights[i] =
                    databaseHelper.getCapableWeightRecent(tableName, exerciseNames[i], routineID, workoutNum, date);
        }
        initializeExAndWork();
    }

    //Rounds exercise weight to nearest multiple of 5
    public double round(double weight) {
        return 5 * Math.round(weight / 5);
    }

    private int getCurrentWorkoutNum(DatabaseHelper databaseHelper) {
        String tableName = this.name + "Table";
        int lastWorkout = databaseHelper.getLatestWorkout(tableName);
        return (lastWorkout + 1) % this.workouts.size();
    }

    //Initializes names of exercises and routine
    private void initializeNames() {
        if (routineID == 1) {
            exerciseNames = ExerciseNames.BEGINNER_NAMES;
            name = "Beginner";
        } else if (routineID == 2) {
            exerciseNames = ExerciseNames.INTERMEDIATE_NAMES;
            name = "Intermediate";
        } else if (routineID == 3) {
            exerciseNames = ExerciseNames.ADVANCED_NAMES;
            name = "Advanced";
        } else {
            throw new NullPointerException();
        }
        tableName = name + "Table";
    }

    //Initializes exercises and workouts with the exercise weights
    private void initializeExAndWork() {
        if (routineID == 1) {
            initializeBegEx();
            initializeBegWorkouts();
        } else if (routineID == 2) {
            initializeIntEx();
            initializeIntWorkouts();
        } else if (routineID == 3) {
            initializeAdvEx();
            initializeAdvWorkouts();
        } else {
            throw new NullPointerException();
        }
    }

    private void initializeBegEx() {
        limitExerciseSize();

        List<Integer> goalReps = new ArrayList<>(Arrays.asList(5, 5, 5));
        List<Integer> deadliftGoal = new ArrayList<>(Arrays.asList(5));
        int incr = 5;
        int perc = 1;

        exercises.add(new Exercise("Bench Press", exerciseWeights[0], incr, perc, goalReps));
        exercises.add(new Exercise("Overhead Press", exerciseWeights[1], incr, perc, goalReps));
        exercises.add(new Exercise("Squat", exerciseWeights[2], incr, perc, goalReps));
        exercises.add(new Exercise("Deadlift", exerciseWeights[3], incr, perc, deadliftGoal));
        exercises.add(new Exercise("Barbell Row", exerciseWeights[4], incr, perc, goalReps));
    }

    private void initializeIntEx() {
        limitExerciseSize();

        List<Integer> volumeDayGoalReps = new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5));
        List<Integer> squatLightReps = new ArrayList<>(Arrays.asList(5, 5));
        List<Integer> lightDayGoalReps = new ArrayList<>(Arrays.asList(5, 5, 5));
        List<Integer> intensityDayGoalReps = new ArrayList<>(Arrays.asList(5));

        exercises.add(new Exercise("Bench Press", exerciseWeights[0], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Bench Press1", round(.9 * exerciseWeights[0]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Bench Press2", round(.81 * exerciseWeights[0]), 0, 1, lightDayGoalReps));

        exercises.add(new Exercise("Overhead Press", exerciseWeights[1], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Overhead Press1", round(.9 * exerciseWeights[1]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Overhead Press2", round(.81 * exerciseWeights[1]), 0, 1, lightDayGoalReps));

        exercises.add(new Exercise("Squat", exerciseWeights[2], 5, 1, intensityDayGoalReps));
        exercises.add(new Exercise("Squat1", round(.9 * exerciseWeights[2]), 0, 1, volumeDayGoalReps));
        exercises.add(new Exercise("Squat2", round(.72 * exerciseWeights[2]), 0, 1, squatLightReps));

        exercises.add(new Exercise("Deadlift", round(.9 * exerciseWeights[3]), 5, 1, intensityDayGoalReps));

        exercises.add(new Exercise("Barbell Row", exerciseWeights[4], 5, 1, lightDayGoalReps));
    }

    private void initializeAdvEx() {
        limitExerciseSize();

        List<Integer> sevenReps = new ArrayList<>(Arrays.asList(7, 7, 7));
        List<Integer> tenReps = new ArrayList<>(Arrays.asList(10, 10, 10));
        List<Integer> twelveReps = new ArrayList<>(Arrays.asList(12, 12, 12));
        List<Integer> fiveReps = new ArrayList<>(Arrays.asList(5, 5, 5));

        int incr = 5;
        int perc = 1;

        for (int i = 0; i < 2; i++) {
            exercises.add(new Exercise(exerciseNames[i], round(exerciseWeights[i]), incr, perc, sevenReps));
        }

        for (int i = 2; i < 4; i++) {
            exercises.add(new Exercise(exerciseNames[i], round(exerciseWeights[i]), incr, perc, fiveReps));
        }

        exercises.add(new Exercise(exerciseNames[4], round(exerciseWeights[4]), incr, perc, sevenReps));

        for (int i = 5; i < 7; i++) {
            exercises.add(new Exercise(exerciseNames[i], exerciseWeights[i], incr, perc, tenReps));
        }

        for (int i = 7; i < 13; i++) {
            exercises.add(new Exercise(exerciseNames[i], exerciseWeights[i], incr, perc, twelveReps));
        }
    }

    private void initializeBegWorkouts() {
        limitWorkoutSize(2);

        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Bench Press"),
                getExerciseByName("Barbell Row"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Deadlift"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
    }

    private void initializeIntWorkouts() {
        limitWorkoutSize(6);

        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat1"),
                getExerciseByName("Bench Press1"),
                getExerciseByName("Deadlift"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat2"),
                getExerciseByName("Overhead Press2"))));

        Workout workoutC = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Bench Press"),
                getExerciseByName("Barbell Row"))));

        Workout workoutD = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat1"),
                getExerciseByName("Overhead Press1"),
                getExerciseByName("Deadlift"))));

        Workout workoutE = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat2"),
                getExerciseByName("Bench Press2"))));

        Workout workoutF = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Barbell Row"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
        workouts.add(workoutC);
        workouts.add(workoutD);
        workouts.add(workoutE);
        workouts.add(workoutF);
    }

    private void initializeAdvWorkouts() {
        limitWorkoutSize(3);

        Workout workoutA = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Bench Press"),
                getExerciseByName("Overhead Press"),
                getExerciseByName("Incline Dumbbell Press"),
                getExerciseByName("Lateral Raise"),
                getExerciseByName("Rope Pulldown"),
                getExerciseByName("Overhead Tricep Extension"))));

        Workout workoutB = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Barbell Row"),
                getExerciseByName("Lat Pulldown"),
                getExerciseByName("Face Pulls"),
                getExerciseByName("Barbell Curl"),
                getExerciseByName("Hammer Curl"))));

        Workout workoutC = new Workout(new ArrayList<>(Arrays.asList(
                getExerciseByName("Squat"),
                getExerciseByName("Deadlift"))));

        workouts.add(workoutA);
        workouts.add(workoutB);
        workouts.add(workoutC);
    }

    private Exercise getExerciseByName(String name) {
        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);

            if (exercise.getName().toLowerCase().equals(name.toLowerCase())) {
                return exercise;
            }
        }
        return null;
    }

    //Making sure that ArrayList of exercises cannot be bigger than num of exercises
    private void limitExerciseSize() {
        if (exercises.size() >= exerciseNames.length) {
            exercises.clear();
        }
    }

    //Making sure that ArrayList of workouts cannot be bigger than max num of workouts
    private void limitWorkoutSize(int maxSize) {
        if (workouts.size() >= maxSize) {
            workouts.clear();
        }
    }
}

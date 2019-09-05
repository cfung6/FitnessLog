package com.example.fitnesslog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseNames {
    public static final List<String> BEGINNER_NAMES_LIST = new ArrayList<>(Arrays.asList(
            "Bench Press",
            "Overhead Press",
            "Squat",
            "Deadlift",
            "Barbell Row"));

    public static final String[] BEGINNER_NAMES = getStringArray(BEGINNER_NAMES_LIST);

    public static final List<String> INTERMEDIATE_NAMES_LIST = new ArrayList<>(Arrays.asList(
            "Bench Press",
            "Overhead Press",
            "Squat",
            "Deadlift",
            "Barbell Row"));

    public static final String[] INTERMEDIATE_NAMES = getStringArray(INTERMEDIATE_NAMES_LIST);

    public static final List<String> ADVANCED_NAMES_LIST = new ArrayList<>(Arrays.asList(
            "Bench Press",
            "Overhead Press",
            "Squat",
            "Deadlift",
            "Barbell Row",
            "Incline Dumbbell Press",
            "Lat Pulldown",
            "Barbell Curl",
            "Hammer Curl",
            "Rope Pulldown",
            "Overhead Tricep Extension",
            "Lateral Raise",
            "Face Pulls"));

    public static final String[] ADVANCED_NAMES = getStringArray(ADVANCED_NAMES_LIST);

    //Turning String List to String[]
    private static String[] getStringArray(List<String> exerciseNameList) {
        String[] exerciseNames = new String[exerciseNameList.size()];
        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseNames[i] = exerciseNameList.get(i);
        }
        return exerciseNames;
    }
}

package com.example.fitnessapp.persistence;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.Level;
import com.example.fitnessapp.Routine;
import com.example.fitnessapp.Workout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Jsonifier {

    //EFFECTS: returns a JSON representation of a Workout
    public static JSONObject workoutToJson(Workout workout) {
        JSONObject workoutJson = new JSONObject();
        try {
            workoutJson.put("name", workout.getName());
            workoutJson.put("exercises", workout.getExercises());
        } catch (Exception e) {
            System.out.println("Exception from workoutJSON conversion thrown");
        }
        return workoutJson;
    }

    public static JSONObject routineToJson(Routine routine) {
        JSONObject routineJson = new JSONObject();
        try {
            routineJson.put("week", routine.getWeek());
            routineJson.put("numOfWorkouts", routine.getNumOfWorkouts());
            routineJson.put("c", routine.getCurrentCalendar());
            routineJson.put("name", routine.getName());
            routineJson.put("workouts", workoutsToJsonArray(routine.getWorkouts()));
        } catch (Exception e) {
            System.out.println("Exception from routineJSON conversion thrown");
        }
        return routineJson;
    }

    private static JSONArray workoutsToJsonArray(List<Workout> workouts) {
        JSONArray workoutsJsonArray = new JSONArray();
        for (Workout w : workouts) {
            workoutsJsonArray.put(workoutToJson(w));
        }
        return workoutsJsonArray;
    }

    public static JSONObject levelToJson(Level level) {
        JSONObject levelJson = new JSONObject();
        try {
            levelJson.put("name", level.getName());
            levelJson.put("routine", level.getRoutine());
        } catch (Exception e) {
            System.out.println("Exception from workoutJSON conversion thrown");
        }
        return levelJson;
    }

    public static JSONObject exerciseToJson(Exercise exercise) {
        JSONObject exerciseJson = new JSONObject();
        try {
            exerciseJson.put("name", exercise.getName());
            exerciseJson.put("actualRepsDone", exercise.getActualRepsDone());
            exerciseJson.put("goalReps", exercise.getGoalReps());
            exerciseJson.put("capableWeight", exercise.getCapableWeight());
            exerciseJson.put("goalWeight", exercise.getGoalWeight());
            exerciseJson.put("increment", exercise.getIncrement());
            exerciseJson.put("percentage", exercise.getPercentage());
            exerciseJson.put("actualWeightList", exercise.getActualWeightList());
            exerciseJson.put("pass", exercise.getPass());
        } catch (Exception e) {
            System.out.println("Exception from exerciseJSON conversion thrown");
        }
        return exerciseJson;
    }


}

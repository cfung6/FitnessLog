package com.example.fitnessapp.persistence;

import com.example.fitnessapp.Workout;

import org.json.JSONObject;

public class Jsonifier {

    //EFFECTS: returns a JSON representation of a Workout
    public static JSONObject workoutToJson(Workout workout) {
        JSONObject workoutJson = new JSONObject();
        try {
            workoutJson.put("name", workout.getName());
            workoutJson.put("exercises", workout.getExercises());
        } catch (Exception e) {
            System.out.println("Exception from JSON conversion thrown");
        }
        return workoutJson;
    }

}

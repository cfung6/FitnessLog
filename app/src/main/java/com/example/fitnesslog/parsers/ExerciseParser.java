package com.example.fitnesslog.parsers;

import com.example.fitnesslog.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseParser {

    public List<Exercise> parse(String input) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            JSONArray exerciseArray = new JSONArray(input);
            for (int i = 0; i < exerciseArray.length(); i++) {
                JSONObject exerciseJSON = exerciseArray.getJSONObject(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exercises;
    }
}

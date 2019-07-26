package com.example.fitnessapp.persistence;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.Level;
import com.example.fitnessapp.Routine;
import com.example.fitnessapp.Workout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

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

    private static JSONArray workoutsToJsonArray(Set<Workout> workouts) {
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

//    public static JSONObject exerciseToJson(Exercise exercise) {
//        JSONObject exerciseJson = new JSONObject();
//        JSONObject goalMap = new JSONObject(exercise.getGoalMap());
//        JSONArray goalArray = new JSONArray();
//        JSONObject actualMap = new JSONObject(exercise.getActualMap());
//        JSONArray actualArray = new JSONArray();
//        try {
//            for(Iterator iteratorOne = goalMap.keys(); iteratorOne.hasNext();) {
//                Double key = (Double) iteratorOne.next();
//                String keyAsString = Double.toString(key);
//                JSONObject o = new JSONObject().put(keyAsString, exercise.getGoalMap().get(key));
//                goalArray.put(o);
//            }
//            for (Iterator iteratorTwo = actualMap.keys(); iteratorTwo.hasNext();) {
//                Double key = (Double) iteratorTwo.next();
//                String keyAsString = Double.toString(key);
//                JSONObject o = new JSONObject().put(keyAsString, exercise.getActualMap().get(key));
//                actualArray.put(o);
//            }
//            exerciseJson.put("name", exercise.getName());
//            exerciseJson.put("goal", goalArray);
//            exerciseJson.put("actual", actualArray);
//        } catch(Exception e) {
//            System.out.println("Exception from exerciseJSON conversion thrown");
//        }
//        return exerciseJson;
//    }


}

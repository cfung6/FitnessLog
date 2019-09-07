package com.example.fitnesslog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final String BEGINNER_TABLE = "BeginnerTable";
    private static final String INTERMEDIATE_TABLE = "IntermediateTable";
    private static final String ADVANCED_TABLE = "AdvancedTable";
    private static final String WORKOUT_EXERCISE_ID = "WorkoutExerciseID";
    private static final String EXERCISE_COL = "Exercise";
    private static final String WORKOUT_COL = "Workout";
    private static final String DATA_TABLE = "DataTable";
    private static final String CURRENT_TIME_COL = "CurrentTime";
    private static final String TODAYS_DATE_COL = "TodaysDate";
    private static final String WEIGHT_COL = "Weight";
    private static final String REPS_COL = "Reps";
    private static final String ROUTINE_ID = "RoutineID";
    private static final String CAPABLE_WEIGHT_COL = "CapableWeight";

    private SQLiteDatabase db;
    private Cursor cursor;
    private String selection;
    private String orderBy;
    private ContentValues contentValues;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // EFFECTS: creates four SQL data tables - Beginner/Intermediate/Advanced and Data Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BEGINNER_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + INTERMEDIATE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + ADVANCED_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + DATA_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CURRENT_TIME_COL + " INTEGER, "
                + TODAYS_DATE_COL + " TEXT, "
                + ROUTINE_ID + " INTEGER, "
                + WORKOUT_EXERCISE_ID + " INTEGER, "
                + WEIGHT_COL + " REAL, "
                + REPS_COL + " INTEGER, "
                + CAPABLE_WEIGHT_COL + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + BEGINNER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTERMEDIATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADVANCED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);
        onCreate(db);
    }

    //Inserting data into data table
    public boolean insertData(long currentTime, String todaysDate, int routineID, int workoutExerciseID, double weight, int reps, double capableWeight) {
        db = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(CURRENT_TIME_COL, currentTime);
        contentValues.put(TODAYS_DATE_COL, todaysDate);
        contentValues.put(ROUTINE_ID, routineID);
        contentValues.put(WORKOUT_EXERCISE_ID, workoutExerciseID);
        contentValues.put(WEIGHT_COL, weight);
        contentValues.put(REPS_COL, reps);
        contentValues.put(CAPABLE_WEIGHT_COL, capableWeight);
        long result = db.insert(DATA_TABLE, null, contentValues);
        return !(result == -1);
    }

    //Inserts exercise name and associated workout number into table if it does not already exist
    public boolean insertBeginnerRoutineData(int workout, String exercise) {
        db = this.getWritableDatabase();
        selection = WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'";
        cursor = db.query(BEGINNER_TABLE, null, selection, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(WORKOUT_COL, workout);
            contentValues.put(EXERCISE_COL, exercise);
            long result = db.insert(BEGINNER_TABLE, null, contentValues);
            cursor.close();
            return !(result == -1);
        }
        cursor.close();
        return false;
    }

    public boolean insertIntermediateRoutineData(int workout, String exercise) {
        db = this.getWritableDatabase();
        selection = WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'";
        cursor = db.query(INTERMEDIATE_TABLE, null, selection, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(WORKOUT_COL, workout);
            contentValues.put(EXERCISE_COL, exercise);
            long result = db.insert(INTERMEDIATE_TABLE, null, contentValues);
            cursor.close();
            return !(result == -1);
        }
        cursor.close();
        return false;
    }

    public boolean insertAdvancedRoutineData(int workout, String exercise) {
        db = this.getWritableDatabase();
        selection = WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'";
        cursor = db.query(ADVANCED_TABLE, null, selection, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(WORKOUT_COL, workout);
            contentValues.put(EXERCISE_COL, exercise);
            long result = db.insert(ADVANCED_TABLE, null, contentValues);
            cursor.close();
            return !(result == -1);
        }
        cursor.close();
        return false;
    }

    public boolean isThereDataInExercise(String table, Exercise exercise, int routineID, String date) {
        db = this.getWritableDatabase();
        String query = "SELECT * FROM DataTable INNER JOIN " + table +
                " ON DataTable.WorkoutExerciseID = " + table +
                ".ID WHERE " + EXERCISE_COL + " = '" + exercise.getName() + "' AND "
                + TODAYS_DATE_COL + " = '" + date + "' AND "
                + ROUTINE_ID + " = " + routineID;

        cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        return count != 0;
    }

    //Returns workoutExerciseID in beg/int/adv table that corresponds to the workout and exercise
    public int selectWorkoutExerciseID(String table, int workout, String exercise) {
        db = this.getWritableDatabase();
        selection = WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'";
        cursor = db.query(table, new String[]{"ID"}, selection, null, null, null, null);
        int id = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("ID"));
            cursor.close();
        }
        return id;
    }

    //Checks if database contains any entries with the current date and workoutExerciseID
    public boolean haveEntriesBeenEntered(String todaysDate, int routineID, int workoutExerciseID) {
        int count;
        db = this.getWritableDatabase();
        selection = TODAYS_DATE_COL + " = '" + todaysDate + "' AND " + ROUTINE_ID + " = " + routineID + " AND " + WORKOUT_EXERCISE_ID + " = " + workoutExerciseID;
        cursor = db.query(DATA_TABLE, null, selection, null, null, null, null);

        cursor.moveToFirst();
        count = cursor.getCount();
        cursor.close();
        return count != 0;
    }

    public void updateEntries(long time, String todaysDate, int routineID, int workoutExerciseID, List<Double> weights, List<Integer> reps, double capableWeight) {
        db = this.getWritableDatabase();
        List<Integer> ids = new ArrayList<>();
        selection = TODAYS_DATE_COL + " = '" + todaysDate + "' AND " + ROUTINE_ID + " = " + routineID + " AND " + WORKOUT_EXERCISE_ID + " = " + workoutExerciseID;
        cursor = db.query(DATA_TABLE, new String[]{"ID"}, selection, null, null, null, null);

        //Finds all entries with the matching date and workoutExerciseID and puts the primary key of those entries into an array
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));

                ids.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();

        //Updates database entries with the new corresponding weights and reps value
        for (int i = 0; i < ids.size() || i < weights.size(); i++) {
            contentValues = new ContentValues();

            contentValues.put(CURRENT_TIME_COL, time);
            contentValues.put(TODAYS_DATE_COL, todaysDate);
            contentValues.put(ROUTINE_ID, routineID);
            contentValues.put(WEIGHT_COL, weights.get(i));
            contentValues.put(REPS_COL, reps.get(i));
            contentValues.put(WORKOUT_EXERCISE_ID, workoutExerciseID);
            contentValues.put(CAPABLE_WEIGHT_COL, capableWeight);
            db.update(DATA_TABLE, contentValues, "ID = " + ids.get(i), null);
        }
    }

    //Checks if table is completely empty
    public boolean isEmpty() {
        db = this.getWritableDatabase();
        cursor = db.query(DATA_TABLE, new String[]{"ID"}, null, null, null, null, null);

        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    // EFFECTS: returns 1 for beginner routine, 2 for intermediate routine, and 3 for advance routine
    public int getLatestRoutineID() {
        db = this.getWritableDatabase();
        orderBy = CURRENT_TIME_COL + " DESC";
        cursor = db.query(DATA_TABLE, null, null, null, null, null, orderBy, "1");
        int id = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(ROUTINE_ID));
            cursor.close();
        }
        return id;
    }

    public int getLatestRoutineByDate(String todaysDate) {
        db = this.getWritableDatabase();
        orderBy = CURRENT_TIME_COL + " DESC";
        selection = TODAYS_DATE_COL + " = '" + todaysDate + "' ";
        cursor = db.query(DATA_TABLE, null, selection, null, null, null, orderBy);
        int routineid = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            routineid = cursor.getInt(cursor.getColumnIndex(ROUTINE_ID));
            cursor.close();
        }
        return routineid;
    }

    public int getWorkoutByDate(String table, String todaysDate) {
        int workoutExerciseID = getWorkoutExerciseIDByDate(todaysDate);
        db = this.getWritableDatabase();
        selection = "ID = " + workoutExerciseID;
        cursor = db.query(table, null, selection, null, null, null, null);
        int workoutNum = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            workoutNum = cursor.getInt(cursor.getColumnIndex("Workout"));
            cursor.close();
        }
        return workoutNum;
    }

    private int getWorkoutExerciseIDByDate(String todaysDate) {
        db = this.getWritableDatabase();
        orderBy = CURRENT_TIME_COL + " DESC";
        selection = TODAYS_DATE_COL + " = '" + todaysDate + "'";
        cursor = db.query(DATA_TABLE, null, selection, null, null, null, orderBy);
        int workoutExerciseID = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            workoutExerciseID = cursor.getInt(cursor.getColumnIndex(WORKOUT_EXERCISE_ID));
            cursor.close();
        }
        return workoutExerciseID;
    }

    // EFFECTS: given beg/int/adv table and an exercise name, returns a double representing the
    //          weight that the user is capable of lifting
    public double getExerciseCapableWeight(String table, String exerciseName) {
        db = this.getWritableDatabase();
        String query = "SELECT CapableWeight FROM DataTable INNER JOIN " + table +
                " ON DataTable.WorkoutExerciseID = " + table +
                ".ID WHERE Exercise = " + "'" + exerciseName + "' " + "ORDER BY " + CURRENT_TIME_COL + " DESC";
        cursor = db.rawQuery(query, null);
        double weight = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            weight = cursor.getDouble(cursor.getColumnIndex(CAPABLE_WEIGHT_COL));
            cursor.close();
        }
        return weight;
    }

    public double[] getExerciseWeightArray(int routineID, String todaysDate) {
        db = this.getWritableDatabase();
        String[] exerciseNames;
        double[] weights;
        String table;

        if (routineID == 1) {
            exerciseNames = ExerciseNames.BEGINNER_NAMES;
            table = BEGINNER_TABLE;
        } else if (routineID == 2) {
            exerciseNames = ExerciseNames.INTERMEDIATE_NAMES;
            table = INTERMEDIATE_TABLE;
        } else {
            exerciseNames = ExerciseNames.ADVANCED_NAMES;
            table = ADVANCED_TABLE;
        }

        weights = new double[exerciseNames.length];

        for (int i = 0; i < exerciseNames.length; i++) {
            weights[i] = getCapableWeightByDate(table, exerciseNames[i], todaysDate);
        }

        return weights;
    }

    private double getCapableWeightByDate(String table, String exerciseName, String todaysDate) {
        db = this.getWritableDatabase();
        String query = "SELECT CapableWeight FROM DataTable INNER JOIN " + table +
                " ON DataTable.WorkoutExerciseID = " + table +
                ".ID WHERE Exercise = " + "'" + exerciseName + "' AND TodaysDate = " + todaysDate
                + " ORDER BY " + CURRENT_TIME_COL + " DESC";
        cursor = db.rawQuery(query, null);
        double weight = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            weight = cursor.getDouble(cursor.getColumnIndex(CAPABLE_WEIGHT_COL));
            cursor.close();
        }
        return weight;
    }

//    public HashMap<String, List<Integer>> getExerciseRepsArray(int routineID, String todaysDate) {
//        db = this.getWritableDatabase();
//        String[] exerciseNames;
//        HashMap<String, List<Integer>> reps = new HashMap<>();
//        String table;
//
//        if (routineID == 1) {
//            exerciseNames = ExerciseNames.BEGINNER_NAMES;
//            table = BEGINNER_TABLE;
//        } else if (routineID == 2) {
//            exerciseNames = ExerciseNames.INTERMEDIATE_NAMES;
//            table = INTERMEDIATE_TABLE;
//        } else {
//            exerciseNames = ExerciseNames.ADVANCED_NAMES;
//            table = ADVANCED_TABLE;
//        }
//
//        for (String exerciseName : exerciseNames) {
//            reps.put(exerciseName, getReps(table, exerciseName, todaysDate));
//        }
//
//        return reps;
//    }

    // EFFECTS: returns a list of reps done for the given exercise on the given date
    public List<Integer> getRepsByExerciseAndDate(String table, Exercise exercise, String todaysDate) {
        db = this.getWritableDatabase();
        List<Integer> reps = new ArrayList<>();
        String query = "SELECT " + REPS_COL + " FROM DataTable INNER JOIN " + table +
                " ON DataTable.WorkoutExerciseID = " + table +
                ".ID WHERE " + EXERCISE_COL + " = '" + exercise.getName() + "' AND "
                + TODAYS_DATE_COL + " = '" + todaysDate + "' "
                + " ORDER BY " + CURRENT_TIME_COL + " DESC";
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int repsFromData = cursor.getInt(cursor.getColumnIndex(REPS_COL));
                reps.add(repsFromData);
                cursor.moveToNext();
            }
        }
        return reps;
    }

    // EFFECTS: returns a list of weights done for the given exercise on the given date
    public List<Double> getWeightByExerciseAndDate(String table, Exercise exercise, String todaysDate) {
        db = this.getWritableDatabase();
        List<Double> weights = new ArrayList<>();
        String query = "SELECT " + WEIGHT_COL + " FROM DataTable INNER JOIN " + table +
                " ON DataTable.WorkoutExerciseID = " + table +
                ".ID WHERE " + EXERCISE_COL + " = '" + exercise.getName() + "' AND "
                + TODAYS_DATE_COL + " = '" + todaysDate + "' "
                + " ORDER BY " + CURRENT_TIME_COL + " DESC";

        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                double weightFromData = cursor.getDouble(cursor.getColumnIndex(WEIGHT_COL));
                weights.add(weightFromData);
                cursor.moveToNext();
            }
        }
        return weights;
    }

    public List<String> returnAllDistinctDates() {
        db = this.getWritableDatabase();
        List<String> dates = new ArrayList<>();
        String query = "SELECT DISTINCT " + TODAYS_DATE_COL + " FROM " + DATA_TABLE;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String todaysDate = cursor.getString(cursor.getColumnIndex(TODAYS_DATE_COL));

                dates.add(todaysDate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }

    //Gets the latest workout number from beg/int/adv tables using the latest workoutExerciseID
    public int getLatestWorkout(String table) {
        int workoutExerciseID = getLatestWorkoutExerciseID();
        db = this.getWritableDatabase();
        selection = "ID = " + workoutExerciseID;
        cursor = db.query(table, null, selection, null, null, null, null);
        int workoutNum = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            workoutNum = cursor.getInt(cursor.getColumnIndex("Workout"));
            cursor.close();
        }
        return workoutNum;
    }

    //Gets the latest workoutExerciseID from DataTable
    private int getLatestWorkoutExerciseID() {
        db = this.getWritableDatabase();
        orderBy = CURRENT_TIME_COL + " DESC";
        cursor = db.query(DATA_TABLE, null, null, null, null, null, orderBy, "1");
        int workoutExerciseID = -1;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            workoutExerciseID = cursor.getInt(cursor.getColumnIndex(WORKOUT_EXERCISE_ID));
            cursor.close();
        }
        return workoutExerciseID;
    }
}

package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    //    private static final String ROUTINE_TABLE = "Routine_table";
    private static final String BEGINNER_TABLE = "BeginnerTable";
    private static final String INTERMEDIATE_TABLE = "IntermediateTable";
    private static final String ADVANCED_TABLE = "AdvancedTable";
    private static final String ROUTINE_ID = "Routine_ID";
    private static final String EXERCISE_COL = "Exercise";
    private static final String WORKOUT_COL = "Workout";
    private static final String DATA_TABLE = "DataTable";
    private static final String DATE_COL = "Date";
    private static final String WEIGHT_COL = "Weight";
    private static final String REPS_COL = "Reps";

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
                + DATE_COL + " INTEGER, "
                + WEIGHT_COL + " REAL, "
                + REPS_COL + " INTEGER, "
                + ROUTINE_ID + " INTEGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + ROUTINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BEGINNER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTERMEDIATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADVANCED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);
        onCreate(db);
    }

    public boolean insertData(long dateTime, double weight, int reps, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE_COL, dateTime);
        contentValues.put(WEIGHT_COL, weight);
        contentValues.put(REPS_COL, reps);
        contentValues.put(ROUTINE_ID, id);
        long result = db.insert(DATA_TABLE, null, contentValues);
        return !(result == -1);
    }

    public boolean insertBeginnerRoutineData(int workout, String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BEGINNER_TABLE, null, WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'", null, null, null, null);
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(INTERMEDIATE_TABLE, null, WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'", null, null, null, null);
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ADVANCED_TABLE, null, WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'", null, null, null, null);
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

    //Returns routineID in beg/int/adv table that corresponds to the workout and exercise
    public int selectRoutineID(String table, int workout, String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(table, new String[]{"ID"}, WORKOUT_COL + "=" + workout + " AND " + EXERCISE_COL + " LIKE '" + exercise + "'", null, null, null, null);
        int id = -1;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex("ID"));
            cursor.close();
        }
        return id;
    }

    //Deletes rows in Data_Table that contain the matching time and routineID so duplicates are not stored when pressing submit button multiple times
    public void deleteRowsInData(long time, int routineID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATA_TABLE, DATE_COL + " = " + time + " AND " + ROUTINE_ID + " = " + routineID, null);
    }

    public boolean haveEntriesBeenEntered(long time, int routineID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DATA_TABLE, null, DATE_COL + " = " + time + " AND " + ROUTINE_ID + " = " + routineID, null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        Log.d("TAG", "count = " + count);
        cursor.close();
        return count != 0;
    }

    public void updateEntries(long time, List<Double> weights, List<Integer> reps, int routineID) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Integer> ids = new ArrayList<>();
        Cursor cursor = db.query(DATA_TABLE, new String[]{"ID"}, DATE_COL + " = " + time + " AND " + ROUTINE_ID + " = " + routineID, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                ids.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = 0; i < ids.size() || i < weights.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATE_COL, time);
            contentValues.put(WEIGHT_COL, weights.get(i));
            contentValues.put(REPS_COL, reps.get(i));
            contentValues.put(ROUTINE_ID, routineID);
            db.update(DATA_TABLE, contentValues, "ID = " + ids.get(i), null);
        }
    }
}

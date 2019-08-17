package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + ROUTINE_TABLE
//                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + ROUTINE_COL + " TEXT, "
//                + WORKOUT_COL + " INTEGER, "
//                + EXERCISE_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + BEGINNER_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT, "
                + ROUTINE_ID + " INTEGER) ");

        db.execSQL("CREATE TABLE " + INTERMEDIATE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT, "
                + ROUTINE_ID + " INTEGER) ");

        db.execSQL("CREATE TABLE " + ADVANCED_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_COL + " INTEGER, "
                + EXERCISE_COL + " TEXT, "
                + ROUTINE_ID + " INTEGER) ");

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

    public boolean isExerciseInBeginnerTable(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BEGINNER_TABLE, null, EXERCISE_COL + " LIKE '" + exercise + "'", null, null, null, null);
        return cursor.getCount() != 0;
    }

    public boolean updateDataBeginnerTable(long dateTime, double weight, int reps, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE_COL, dateTime);
        contentValues.put(WEIGHT_COL, weight);
        contentValues.put(REPS_COL, reps);
        contentValues.put(ROUTINE_ID, id);
        long result = db.update(DATA_TABLE, contentValues, "_id=" + dateTime, null);
        return !(result == -1);
    }
}

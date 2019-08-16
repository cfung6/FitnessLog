package com.example.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final String ROUTINE_TABLE = "routine_table";
    private static final String ROUTINE_NAME_COL = "Routine_name";
    private static final String BEGINNER_TABLE = "beginner_table";
    private static final String INTERMEDIATE_TABLE = "intermediate_table";
    private static final String ADVANCED_TABLE = "advanced_table";
    private static final String WORKOUT_NAME_COL = "Workout_name";
    private static final String EXERCISE_TABLE = "exercise_table";
    private static final String EXERCISE_NAME_COL = "Exercise_name";
    private static final String DATE_COL = "Date";
    private static final String ROUTINE_ID = "Routine_ID";
    private static final String EXERCISE_ID = "Exercise_ID";
    private static final String WORKOUT_ID = "Workout_ID";
    private static final String WEIGHT_COL = "Weight";
    private static final String SETS_COL = "Sets";
    private static final String REPS_COL = "Reps";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ROUTINE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROUTINE_NAME_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + EXERCISE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_NAME_COL + " TEXT)");

        db.execSQL("CREATE TABLE " + BEGINNER_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME_COL + "TEXT)");

        db.execSQL("CREATE TABLE " + INTERMEDIATE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME_COL + "TEXT)");

        db.execSQL("CREATE TABLE " + ADVANCED_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME_COL + "TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
        onCreate(db);
    }

//    public boolean insertData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//    }
}

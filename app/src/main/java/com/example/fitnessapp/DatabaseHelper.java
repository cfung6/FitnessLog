package com.example.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final String TABLE_NAME = "fitness_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Date";
    private static final String COL4 = "Routine";
    private static final String COL5 = "Workout";
    private static final String COL6 = "Weight";
    private static final String COL7 = "Sets";
    private static final String COL8 = "Reps";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "
                + COL3 + " INTEGER, "
                + COL4 + " TEXT, "
                + COL5 + " TEXT, "
                + COL6 + " REAL, "
                + COL7 + " INTEGER, "
                + COL8 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    public boolean insertData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//    }
}

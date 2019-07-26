package com.example.fitnessapp;

public abstract class Level {
    private String name;
    private Routine routine;

    public Level(String s, Routine r) {
        name = s;
        routine = r;
    }

    public String getName() {
        return name;
    }

    public Routine getRoutine() {
        return routine;
    }
}

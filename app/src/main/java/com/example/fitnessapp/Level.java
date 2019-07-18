package com.example.fitnessapp;

public abstract class Level {
    private String name;
    private Routine routine;

    public Level(String s, Routine r) {
        this.name = s;
    }
}

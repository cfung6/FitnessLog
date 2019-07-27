package com.example.fitnessapp.persistence;

import com.example.fitnessapp.Exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonFileIO {
    public static final File jsonDataFile = new File("./assets/exercises.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of exercises from the content of jsonDataFile
    public static List<Exercise> read() {
      List<Exercise> exercises = new ArrayList<>();
      try {
          Scanner scanner = new Scanner(jsonDataFile);
          String jsonAsString = "";

      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }


      return exercises;
    }
}

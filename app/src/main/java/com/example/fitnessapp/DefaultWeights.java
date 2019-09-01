package com.example.fitnessapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultWeights {

    //Default weights that are assigned when checkbox for exercises in NewProgramActivity are unchecked
    public static final int BENCH_PRESS_BEGINNER = 65;
    public static final int OVERHEAD_PRESS_BEGINNER = 45;
    public static final int SQUAT_BEGINNER = 95;
    public static final int DEADLIFT_BEGINNER = 135;
    public static final int BARBELL_ROW_BEGINNER = 45;

    public static final int BENCH_PRESS_INTERMEDIATE = 165;
    public static final int OVERHEAD_PRESS_INTERMEDIATE = 105;
    public static final int SQUAT_INTERMEDIATE = 225;
    public static final int DEADLIFT_INTERMEDIATE = 250;
    public static final int BARBELL_ROW_INTERMEDIATE = 135;

    public static final int BENCH_PRESS_ADVANCED = 225;
    public static final int OVERHEAD_PRESS_ADVANCED = 135;
    public static final int SQUAT_ADVANCED = 315;
    public static final int DEADLIFT_ADVANCED = 350;
    public static final int BARBELL_ROW_ADVANCED = 185;

    public static final List<Integer> defaultWeights = new ArrayList<>(Arrays.asList(
            BENCH_PRESS_BEGINNER,
            OVERHEAD_PRESS_BEGINNER,
            SQUAT_BEGINNER,
            DEADLIFT_BEGINNER,
            BARBELL_ROW_BEGINNER,

            BENCH_PRESS_INTERMEDIATE,
            OVERHEAD_PRESS_INTERMEDIATE,
            SQUAT_INTERMEDIATE,
            DEADLIFT_INTERMEDIATE,
            BARBELL_ROW_INTERMEDIATE,

            BENCH_PRESS_ADVANCED,
            OVERHEAD_PRESS_ADVANCED,
            SQUAT_ADVANCED,
            DEADLIFT_ADVANCED,
            BARBELL_ROW_ADVANCED
    ));
}

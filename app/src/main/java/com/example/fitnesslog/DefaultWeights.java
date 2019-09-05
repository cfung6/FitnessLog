package com.example.fitnesslog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultWeights {

    //Default weights that are assigned when checkbox for exercises in NewProgramActivity are unchecked
    public static final double BENCH_PRESS_BEGINNER = 65;
    public static final double OVERHEAD_PRESS_BEGINNER = 45;
    public static final double SQUAT_BEGINNER = 95;
    public static final double DEADLIFT_BEGINNER = 135;
    public static final double BARBELL_ROW_BEGINNER = 45;

    public static final double BENCH_PRESS_INTERMEDIATE = 165;
    public static final double OVERHEAD_PRESS_INTERMEDIATE = 105;
    public static final double SQUAT_INTERMEDIATE = 225;
    public static final double DEADLIFT_INTERMEDIATE = 250;
    public static final double BARBELL_ROW_INTERMEDIATE = 135;

    public static final double BENCH_PRESS_ADVANCED = 225;
    public static final double OVERHEAD_PRESS_ADVANCED = 135;
    public static final double SQUAT_ADVANCED = 315;
    public static final double DEADLIFT_ADVANCED = 350;
    public static final double BARBELL_ROW_ADVANCED = 185;

    public static final double INCLINE_DUMBBELL_PRESS = 45;
    public static final double LAT_PULLDOWN = 120;
    public static final double BARBELL_CURL = 45;
    public static final double HAMMER_CURL = 25;
    public static final double ROPE_PULLDOWN = 45;
    public static final double OVERHEAD_TRICEP_EXTENSION = 25;
    public static final double LATERAL_RAISE = 15;
    public static final double FACE_PULLS = 20;

    public static final List<Double> defaultWeights = new ArrayList<>(Arrays.asList(
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
            BARBELL_ROW_ADVANCED,

            INCLINE_DUMBBELL_PRESS,
            LAT_PULLDOWN,
            BARBELL_CURL,
            HAMMER_CURL,
            ROPE_PULLDOWN,
            OVERHEAD_TRICEP_EXTENSION,
            LATERAL_RAISE,
            FACE_PULLS
    ));
}

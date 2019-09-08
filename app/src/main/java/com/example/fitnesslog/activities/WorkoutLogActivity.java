package com.example.fitnesslog.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.Exercise;
import com.example.fitnesslog.R;
import com.example.fitnesslog.Routine;
import com.example.fitnesslog.TodaysDate;
import com.example.fitnesslog.Workout;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutLogActivity extends AppCompatActivity {

    /*
    ALL ARRAYS ARE IN THE FOLLOWING ORDER:
        BENCH
        OVERHEAD PRESS
        SQUAT
        DEADLIFT
        BARBELL ROW
     */

    private Routine routine;
    private int routineID;

    protected Workout currentWorkout;

    private long currentTime;
    private String todaysDate;

    private DatabaseHelper databaseHelper;

    private Map<String, EditText> editTextNames;
    private SparseArray<TextView> passFailMessages;
    private SparseArray<List<TextView>> setNumbers;

    private String previousActivity;

    protected EditText[] weightsET;
    protected EditText[] repsET;
    protected String[] weightsInput;
    protected String[] repsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_workout_log);
        ;

        Intent intent = getIntent();
        TodaysDate today = new TodaysDate();
        databaseHelper = new DatabaseHelper(this);

        routine = intent.getParcelableExtra("ROUTINE");
        assert routine != null;
        setTitle(routine.getName());
        routineID = routine.getRoutineID();

        currentTime = intent.getLongExtra("TIME", Calendar.getInstance().getTimeInMillis());
        todaysDate = intent.getStringExtra("DATE");
        previousActivity = intent.getStringExtra("ACTIVITY");

        if (todaysDate == null || todaysDate.isEmpty()) {
            todaysDate = today.getDateString();
        }

        setDateText();
        initializeListsAndMaps();
        initializeCurrentWorkout(getTodaysDate());
        createAbstractXML(currentWorkout);
    }

    // EFFECTS: creates menu for calendar, stopwatch, and tutorials options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // EFFECTS: responds to menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.calendar:
                startActivity(new Intent(this, WorkoutCalendar.class));
                return true;
            case R.id.stopwatch:
                startActivity(new Intent(this, Stopwatch.class));
                return true;
            case R.id.exercise_tutorials:
                startActivity(new Intent(this, ExerciseTutorials.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // EFFECTS: called when submit button is pressed. Each submit button has a tag that reflects
    //          which exercise it is connected to (0, 1, 2, etc.).
    public void submitOnClick(View view) {
        //Each submit button has an integer tag that is passed (First submit is 0, second is 1, etc)
        String tag = view.getTag().toString();
        int currentExerciseNum = Integer.parseInt(tag);

        submitOnClick(view, currentExerciseNum);
    }

    public void submitOnClick(View view, int currentExerciseNum) {
        //Sets current exercise depending on which submit button was pressed
        Exercise exercise = currentWorkout.getExerciseAtIndex(currentExerciseNum);
        int numOfSets = exercise.getGoalReps().size();
        routine.insertRoutineData(routine.getWorkouts().indexOf(currentWorkout), exercise.getName(), databaseHelper);
        instantiateETAndInputs(numOfSets);
        assignETNames(currentExerciseNum, numOfSets);

        //Sets the next EditTexts to visible
        if (!setNextToVisible(currentExerciseNum, numOfSets, weightsInput, repsInput, weightsET, repsET)) {
            //Checking to see if all EditTexts are filled
            setPassFailMessages(numOfSets, exercise, currentExerciseNum, view);
        }
    }

    private void instantiateETAndInputs(int numOfSets) {
        weightsET = new EditText[numOfSets];
        repsET = new EditText[numOfSets];
        weightsInput = new String[numOfSets];
        repsInput = new String[numOfSets];
    }

    // EFFECTS: Assigns variables in the four arrays depending on which set and exercise the user is on
    private void assignETNames(int currentExerciseNum, int numOfSets) {
        for (int currentSetNum = 0; currentSetNum < numOfSets; currentSetNum++) {
            String weightsETName = "weight" + currentSetNum + "ex" + currentExerciseNum;
            String repsETName = "reps" + currentSetNum + "ex" + currentExerciseNum;

            //Finds the correct EditText depending on the current exercise and set
            weightsET[currentSetNum] = editTextNames.get(weightsETName);
            repsET[currentSetNum] = editTextNames.get(repsETName);

            weightsInput[currentSetNum] = weightsET[currentSetNum].getText().toString();
            repsInput[currentSetNum] = repsET[currentSetNum].getText().toString();
        }
    }

    // EFFECTS: if all EditTexts are filled with inputs from the user, then determine and set textview
    //          to pass or fail message. Otherwise, create Snackbar text that prompts user to fill in
    //          other EditTexts.
    private void setPassFailMessages(int numOfSets, Exercise exercise, int currentExerciseNum, View view) {
        if (areAllFilled(weightsInput, repsInput)) {
            try {
                double[] weights = new double[numOfSets];
                int[] reps = new int[numOfSets];

                for (int i = 0; i < numOfSets; i++) {
                    weights[i] = Double.parseDouble(weightsInput[i]);
                    reps[i] = Integer.parseInt(repsInput[i]);
                }
                TextView tv = passFailMessages.get(currentExerciseNum);
                //After all lines are visible, submit button removes all weights and reps so all can be added at once
                exercise.removeRepsDone();
                addRepsDoneToExercise(exercise, weights, reps);
                checkIncremented(exercise, weights, reps);
                setPassFailHelper(exercise, tv);

                double capableWeight = exercise.getCapableWeight();
                insertData(numOfSets, weights, reps, exercise, capableWeight);
            } catch (IllegalArgumentException e) {
                Snackbar mySnackbar = Snackbar.make(view, "Invalid input(s)", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        } else {
            Snackbar mySnackbar = Snackbar.make(view, "One or more of the weights and/or reps are blank", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    // EFFECTS: checks if goal weight has already increased when submit button is pressed to avoid
    //          incrementing more than once
    private void checkIncremented(Exercise exercise, double[] weights, int[] reps) {
        if (exercise.isWeightIncreased()) {
            exercise.setGoalWeight((exercise.getGoalWeight() - exercise.getIncrement()) / exercise.getPercentage());
            exercise.removeRepsDone();
            addRepsDoneToExercise(exercise, weights, reps);
        }
    }

    // EFFECTS: if the required reps and weight were done for the exercise, set textview to pass message.
    //          Otherwise, set textview to fail message
    private void setPassFailHelper(Exercise exercise, TextView tv) {
        if (exercise.passOrFail()) {
            //Increases exercise goal weight
            exercise.increaseWeight();
            exercise.setWeightIncreased(true);
            if (exercise.getIncrement() == 0) {
                tv.setText("Congrats! Complete the rest of this week's workouts to achieve a new max.\n");
            } else {
                tv.setText("Congrats! Your next weight is " + exercise.getGoalWeight() + ".\n");
            }
        } else {
            tv.setText("Failure is inevitable! Stay at your current weight.\n");
            exercise.setWeightIncreased(false);
        }
    }

    // EFFECTS: returns workoutExerciseID corresponding to the current workout and exercise
    private int getWorkoutExerciseID(Exercise exercise) {
        if (routineID == 1) {
            return databaseHelper.selectWorkoutExerciseID("BeginnerTable", routine.getWorkouts().indexOf(currentWorkout), exercise.getName());
        } else if (routineID == 2) {
            return databaseHelper.selectWorkoutExerciseID("IntermediateTable", routine.getWorkouts().indexOf(currentWorkout), exercise.getName());
        } else {
            return databaseHelper.selectWorkoutExerciseID("AdvancedTable", routine.getWorkouts().indexOf(currentWorkout), exercise.getName());
        }
    }

    // EFFECTS: if database contains any entries with the current date and workoutExerciseID, then
    //          update the entry. Otherwise, insert the entry
    private void insertData(int numOfSets, double[] weights, int[] reps, Exercise exercise, double capableWeight) {
        if (databaseHelper.haveEntriesBeenEntered(todaysDate, routineID, getWorkoutExerciseID(exercise))) {
            List<Double> weightsForDataTable = new ArrayList<>();
            List<Integer> repsForDataTable = new ArrayList<>();

            for (int i = 0; i < numOfSets; i++) {
                weightsForDataTable.add(weights[i]);
                repsForDataTable.add(reps[i]);
            }

            databaseHelper.updateEntries(currentTime, todaysDate, routineID, getWorkoutExerciseID(exercise),
                    weightsForDataTable, repsForDataTable, capableWeight);
        } else {
            for (int i = 0; i < numOfSets; i++) {
                databaseHelper.insertData(currentTime, todaysDate, routineID, getWorkoutExerciseID(exercise),
                        weights[i], reps[i], capableWeight);
            }
        }
    }

    protected void initializeCurrentWorkout(String date) {
        //Setting current workout depending on last entries in database
        currentWorkout = routine.getCurrentWorkout(databaseHelper);
        if (databaseHelper.isThereDataFromToday(date, routineID) && previousActivity == null) {
            int workoutNum = databaseHelper.getWorkoutByDate(routine.getName() + "Table", date);

            try {
                currentWorkout = routine.getWorkouts().get(workoutNum);
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d("myTag", "out of bounds");
            }
        }
    }

    protected String getTodaysDate() {
        return todaysDate;
    }

    //Sets textView for the current date
    private void setDateText() {
        TextView dateView = findViewById(R.id.base_date);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String full = new SimpleDateFormat("EE, MMM d yyyy").format(date);
        dateView.setText(full);
    }

    private void initializeListsAndMaps() {
        editTextNames = new HashMap<>();
        passFailMessages = new SparseArray<>();
        setNumbers = new SparseArray<>();
    }

    //Sets the next EditTexts to visible, returns true if it works, false if not
    private boolean setNextToVisible(int exerciseNum, int numOfSets, String[] weightsInput, String[] repsInput, EditText[] weightsET, EditText[] repsET) {
        for (int i = 0; i < numOfSets - 1; i++) {
            if (areWeightsAndRepsFilled(weightsInput[i], repsInput[i])
                    && areWeightsAndRepsInvisible(weightsET[i + 1], repsET[i + 1])) {

                weightsET[i + 1].setVisibility(View.VISIBLE);
                repsET[i + 1].setVisibility(View.VISIBLE);
                setNumbers.get(exerciseNum).get(i + 1).setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    private boolean areWeightsAndRepsFilled(String weight, String reps) {
        return !weight.isEmpty() && !weight.equals(".") && !reps.isEmpty();
    }

    private boolean areWeightsAndRepsInvisible(EditText weight, EditText reps) {
        return !weight.isShown() && !reps.isShown();
    }

    private void addRepsDoneToExercise(Exercise exercise, double[] weights, int[] reps) {
        for (int i = 0; i < weights.length; i++) {
            exercise.addRepsDone(weights[i], reps[i]);
        }
    }

    private boolean areAllFilled(String[] weights, String[] reps) {
        for (int i = 0; i < weights.length; i++) {
            if (!areWeightsAndRepsFilled(weights[i], reps[i])) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: creates the XML based on the exercises from the current workout
    protected void createAbstractXML(Workout currentWorkout) {
        int exerciseNum = 0;
        LinearLayout parentLinearLayout = findViewById(R.id.parent_linear_layout);
        for (Exercise exercise : currentWorkout.getExercises()) {
            LinearLayout exerciseLayout = new LinearLayout(this);
            exerciseLayout.setOrientation(LinearLayout.VERTICAL);
            exerciseLayout.setBackgroundResource(R.drawable.layout_bg);
            LinearLayout.LayoutParams paramsEL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsEL.leftMargin = 30;
            paramsEL.rightMargin = 30;
            paramsEL.bottomMargin = 15;
            exerciseLayout.setLayoutParams(paramsEL);

            LinearLayout setsWeightsRepsContainer = new LinearLayout(this);
            setsWeightsRepsContainer.setOrientation(LinearLayout.HORIZONTAL);
            setsWeightsRepsContainer.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams paramsSWR = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsSWR.weight = 2f;
            LinearLayout setsWeightReps = new LinearLayout(this);
            setsWeightReps.setOrientation(LinearLayout.HORIZONTAL);
            setsWeightReps.setLayoutParams(paramsSWR);

            Button submitButton = new Button(this);
            submitButton.setText("Submit");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1f;
            params.gravity = Gravity.CENTER;
            submitButton.setLayoutParams(params);

            submitButton.setTag("" + exerciseNum);
            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    submitOnClick(v);
                }
            });

            setsWeightsRepsContainer.addView(setsWeightReps);
            setsWeightsRepsContainer.addView(submitButton);

            exerciseLayout.addView(generateExerciseGoals(exercise));
            setsWeightReps.addView(generateSets(exercise, exerciseNum));
            setsWeightReps.addView(generateWeight(exercise, exerciseNum));
            setsWeightReps.addView(generateReps(exercise, exerciseNum));

            exerciseLayout.addView(setsWeightsRepsContainer);

            fillOutEditTexts(exerciseNum);

            TextView passFailMessage = new TextView(this);
            TableLayout.LayoutParams paramMSG = new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            paramMSG.leftMargin = 15;
            paramMSG.rightMargin = 15;
            passFailMessage.setLayoutParams(paramMSG);
            passFailMessage.setGravity(Gravity.CENTER_HORIZONTAL);
            exerciseLayout.addView(passFailMessage);
            passFailMessages.put(exerciseNum, passFailMessage);

            exerciseNum++;
            parentLinearLayout.addView(exerciseLayout);
        }
    }

    // EFFECTS: returns a horizontal linear layout that has the exercises name, weight, sets and reps
    private LinearLayout generateExerciseGoals(Exercise exercise) {
        LinearLayout currentExercise = new LinearLayout(this);
        currentExercise.setOrientation(LinearLayout.HORIZONTAL);
        TextView name = new TextView(this);
        TextView weight = new TextView(this);
        TextView sets = new TextView(this);
        TextView reps = new TextView(this);

        name.setText(changeNameForXML(exercise.getName()));
        weight.setText("Weight:\n" + exercise.getGoalWeight() + " lb");
        sets.setText("Sets: " + exercise.getGoalReps().size());
        reps.setText("Reps: " + exercise.getGoalReps().get(0));
        LinearLayout.LayoutParams exerciseGoalsParam = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        exerciseGoalsParam.weight = 1f;
        exerciseGoalsParam.leftMargin = 25;
        exerciseGoalsParam.bottomMargin = 25;
        exerciseGoalsParam.topMargin = 25;

        name.setLayoutParams(exerciseGoalsParam);
        weight.setLayoutParams(exerciseGoalsParam);
        sets.setLayoutParams(exerciseGoalsParam);
        reps.setLayoutParams(exerciseGoalsParam);

        currentExercise.addView(name);
        currentExercise.addView(weight);
        currentExercise.addView(sets);
        currentExercise.addView(reps);

        return currentExercise;
    }

    // EFFECTS: returns a vertical linear layout that has textviews for Sets and number of sets
    private LinearLayout generateSets(Exercise exercise, int exerciseNum) {
        LinearLayout setsColumn = new LinearLayout(this);
        LinearLayout.LayoutParams paramsSets = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsSets.weight = 1f;
        paramsSets.leftMargin = 30;
        setsColumn.setLayoutParams(paramsSets);
        setsColumn.setOrientation(LinearLayout.VERTICAL);
        TextView sets = new TextView(this);
        sets.setText("Sets");
        sets.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        setsColumn.addView(sets);
        List<TextView> setTextViews = new ArrayList<>();
        setNumbers.put(exerciseNum, setTextViews);
        for (int i = 1; i < exercise.getGoalReps().size() + 1; i++) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams paramsTV = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            paramsTV.weight = 1f;
            tv.setLayoutParams(paramsTV);
            tv.setText(i + "");
            setNumbers.get(exerciseNum).add(tv);
            setsColumn.addView(tv);
            if (i > 1) {
                tv.setVisibility(View.GONE);
            }
        }


        return setsColumn;
    }

    // EFFECTS: returns a vertical linear layout that has a textview for weight and edittexts for
    //          user input
    private LinearLayout generateWeight(Exercise exercise, int exerciseNum) {
        LinearLayout weightsColumn = new LinearLayout(this);
        LinearLayout.LayoutParams paramsWeight = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsWeight.weight = 1f;
        weightsColumn.setLayoutParams(paramsWeight);
        weightsColumn.setOrientation(LinearLayout.VERTICAL);
        TextView weights = new TextView(this);
        weights.setText("Weight");
        weights.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        weightsColumn.addView(weights);
        for (int i = 0; i < exercise.getGoalReps().size(); i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editTextNames.put("weight" + i + "ex" + exerciseNum, editText);
            weightsColumn.addView(editText);
            if (i > 0) {
                editText.setVisibility(View.GONE);
            }
        }
        return weightsColumn;
    }

    // EFFECTS: returns a vertical linear layout that has a textview for reps and edittexts for
    //          user input
    private LinearLayout generateReps(Exercise exercise, int exerciseNum) {
        LinearLayout repsColumn = new LinearLayout(this);
        LinearLayout.LayoutParams paramsReps = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsReps.weight = 1f;
        repsColumn.setLayoutParams(paramsReps);
        repsColumn.setOrientation(LinearLayout.VERTICAL);
        TextView reps = new TextView(this);
        reps.setText("Reps");
        reps.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        repsColumn.addView(reps);
        for (int i = 0; i < exercise.getGoalReps().size(); i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editTextNames.put("reps" + i + "ex" + exerciseNum, editText);
            repsColumn.addView(editText);
            if (i > 0) {
                editText.setVisibility(View.GONE);
            }
        }
        return repsColumn;
    }

    //Changes exercise name to the correct one for XML
    private String changeNameForXML(String name) {
        for (String exerciseName : routine.getExerciseNames()) {
            if (name.equals(exerciseName)) {
                return name;
            }
        }
        return name.substring(0, name.length() - 1);
    }

    //Fills out EditTexts with existing data from the current date
    private void fillOutEditTexts(int exerciseNum) {
        Exercise exercise = currentWorkout.getExerciseAtIndex(exerciseNum);
        int currentWorkoutNum = routine.getWorkouts().indexOf(currentWorkout);
        String table = routine.getName() + "Table";
        String exerciseName = exercise.getName();

        if (databaseHelper.isThereDataInExercise(table, exerciseName, currentWorkoutNum, routineID, todaysDate)
                && previousActivity == null && !databaseHelper.wasExerciseReset(table, changeNameForXML(exerciseName))) {
            List<Integer> repsList =
                    databaseHelper.getRepsByExerciseAndDate(table, exerciseName, currentWorkoutNum, routineID, todaysDate);
            List<Double> weightsList =
                    databaseHelper.getWeightByExerciseAndDate(table, exerciseName, currentWorkoutNum, routineID, todaysDate);

            for (int i = 0; i < repsList.size(); i++) {
                EditText et = editTextNames.get("reps" + i + "ex" + exerciseNum);
                assert et != null;
                try {
                    et.setText(repsList.get(i) + "");
                    et.setVisibility(View.VISIBLE);
                } catch (NullPointerException e) {
                    Log.d("myTag", "no such edittext");
                }
            }

            for (int i = 0; i < weightsList.size(); i++) {
                EditText et = editTextNames.get("weight" + i + "ex" + exerciseNum);
                assert et != null;
                try {
                    et.setText(weightsList.get(i) + "");
                    et.setVisibility(View.VISIBLE);
                } catch (NullPointerException e) {
                    Log.d("myTag", "no such edittext");
                }
            }
        }
    }
}

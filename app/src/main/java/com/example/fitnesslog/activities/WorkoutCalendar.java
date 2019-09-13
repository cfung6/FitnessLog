package com.example.fitnesslog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.R;
import com.example.fitnesslog.Routine;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutCalendar extends AppCompatActivity {

    private CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth;
    private DatabaseHelper databaseHelper;
    private SimpleDateFormat sdf;

    private double[] capableWeights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        databaseHelper = new DatabaseHelper(this);
        dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(false);
        //Displays month and year in the action bar
        actionBar.setTitle(dateFormatMonth.format(new Date()));

        compactCalendar = findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        if (!databaseHelper.isEmpty()) {
            fillOutDates();
        }

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Intent intent;

                for (Event event : events) {
                    //Checking if day clicked time matches with the date time of a workout
                    if (dateClicked.getTime() == event.getTimeInMillis()) {
                        //Finding the most recent routine done on that day
                        int routineID = databaseHelper.getLatestRoutineByDate(sdf.format(dateClicked));
                        Routine routine = new Routine(routineID);
                        intent = new Intent(getApplicationContext(), WorkoutLogActivity.class);

                        //Getting an array of capable weights from that day
                        capableWeights = databaseHelper.getCapableWeightArray(routineID, sdf.format(dateClicked));
                        routine.setExerciseWeights(capableWeights);

                        //Passing routine object and date to the next activity
                        intent.putExtra("ROUTINE", routine);
                        intent.putExtra("DATE", sdf.format(dateClicked));

                        //Go to workout that corresponds to event.getTimeInMillis()
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

    //Puts all the dates in which a workout was done in a List
    private void fillOutDates() {
        List<String> dates = databaseHelper.returnAllDistinctDates();
        for (String date : dates) {
            createEvent(date);
        }
    }

    //Creating an event for each day a workout was done
    private void createEvent(String currentDate) {
        try {
            Date date = sdf.parse(currentDate);
            assert date != null;
            Event ev1 = new Event(Color.BLUE, date.getTime(), "Workout");

            compactCalendar.addEvent(ev1);
        } catch (ParseException e) {
            Log.d("myTag", "Invalid date");
        }
    }
}
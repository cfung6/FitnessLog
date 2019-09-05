package com.example.fitnesslog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.R;
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
        actionBar.setTitle(null);

        compactCalendar = findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        fillOutDates();

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                for (Event event : events) {
                    Log.d("myTag", "" + dateClicked.getTime());
                    Log.d("myTag", "" + event.getTimeInMillis());
                    if (dateClicked.getTime() == event.getTimeInMillis()) {
                        //Go to workout that corresponds to event.getTimeInMillis()
                        Intent intent = new Intent(getApplicationContext(), Stopwatch.class);
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

    private void fillOutDates() {
        List<String> dates = databaseHelper.returnAllDistinctDates();
        for (String date : dates) {
            createEvent(date);
        }
    }

    private void createEvent(String todaysTime) {
        try {
            Date date = sdf.parse(todaysTime);
            Event ev1 = new Event(Color.BLUE, date.getTime(), "Workout");
            compactCalendar.addEvent(ev1);
        } catch (ParseException e) {
            Log.d("Exception", "Invalid date");
        }
    }
}
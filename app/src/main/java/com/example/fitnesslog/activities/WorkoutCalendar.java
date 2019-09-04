package com.example.fitnesslog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.DatabaseHelper;
import com.example.fitnesslog.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutCalendar extends AppCompatActivity {

    private CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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
                    if (dateClicked.getTime() == event.getTimeInMillis()) {
                        //Go to workout that corresponds to event.getTimeInMillis()
                        Intent intent = new Intent(getApplicationContext(), BeginnerActivity.class);
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
        List<Long> times = databaseHelper.returnAllDistinctTimes();
        for (long time : times) {
            createEvent(time);
        }
    }

    private void createEvent(long time) {
        Date date = new Date(time);
        List<Event> events = compactCalendar.getEvents(date);
        if (events.isEmpty()) {
            Event ev1 = new Event(Color.BLUE, time, "Workout");
            compactCalendar.addEvent(ev1);
        }
    }
}
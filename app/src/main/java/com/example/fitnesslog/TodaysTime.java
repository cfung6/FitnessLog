package com.example.fitnesslog;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TodaysTime {

    long todaysTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TodaysTime() {
        TimeZone tz = TimeZone.getDefault();
        ZoneId z = ZoneId.of(tz.getID());
        todaysTime = ZonedDateTime.now(z).toLocalDate().atStartOfDay(z).toEpochSecond() * 1000;
    }

    public long getTodaysTime() {
        return todaysTime;
    }
}

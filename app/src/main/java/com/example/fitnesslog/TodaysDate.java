package com.example.fitnesslog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodaysDate {

    String date;

    public TodaysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());
    }

    public String getDateString() {
        return date;
    }
}

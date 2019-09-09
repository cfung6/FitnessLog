package com.example.fitnesslog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {

    String date;

    public CurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());
    }

    public String getDateString() {
        return date;
    }
}

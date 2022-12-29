package com.example.p3b_tubes_2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeSlot {
    private String day;
    private String start_time;
    private String end_time;

    public String getDay() {
        return day;
    }

    public String getStart_time() {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mm:ss'+07'");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

        String startTime = "";

        try {
            startTime = timeFormatter.format(inputFormatter.parse(this.start_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startTime;
    }

    public String getEnd_time() {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mm:ss'+07'");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

        String endTime = "";

        try {
            endTime = timeFormatter.format(inputFormatter.parse(this.end_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endTime;
    }
}

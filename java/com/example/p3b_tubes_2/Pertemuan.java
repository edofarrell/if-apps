package com.example.p3b_tubes_2;

public class Pertemuan {
    private String title;//length:1-256
    private String description;//length:1-1000
    private String startTime;//2022-04-12 19:00+0500
    private String endTime;//2022-04-12 19:00+0500

    public Pertemuan(String title, String description, String startTime,String endTime){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}

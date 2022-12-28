package com.example.p3b_tubes_2;

public class TimeSlot {
    private String hari;
    private String waktuKosong;

    public TimeSlot(String hari, String waktuKosong) {
        this.hari = hari;
        this.waktuKosong = waktuKosong;
    }

    public String getHari() {
        return this.hari;
    }

    public String getWaktuKosong() {
        return this.waktuKosong;
    }
}

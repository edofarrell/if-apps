package com.example.p3b_tubes_2;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PertemuanList {
    class Pertemuan {
        private String id;//uuidv4
        private String title;//length:1-256
        private String description;//length:1-1000
        private String start_datetime;//2022-04-12 19:00+0500
        private String end_datetime;//2022-04-12 19:00+0500

        public Pertemuan(String id, String title, String description, String startTime, String endTime) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.start_datetime = startTime;
            this.end_datetime = endTime;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getStartTime() {
            return start_datetime;
        }

        public String getEndTime() {
            return end_datetime;
        }

        public String getId() {
            return id;
        }
    }

    private ArrayList<Pertemuan> arr;
    private PertemuanPresenter pertemuanPresenter;
    private RequestQueue queue;
    private Gson gson;

    public PertemuanList(PertemuanPresenter presenter, Context context) {
        arr = new ArrayList<>();
        this.pertemuanPresenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

}

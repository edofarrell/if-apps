package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PertemuanList implements Response.Listener<String>, Response.ErrorListener {

    class Pertemuan {
        private String id;//uuidv4
        private String title;//length:1-256
        private String description;//length:1-1000
        private String start_datetime;//2022-04-12 19:00+0500
        private String end_datetime;//2022-04-12 19:00+0500
        private String organizer_name;
        private ArrayList<String> partisipan;

        public Pertemuan(String id, String title, String description, String startTime, String endTime, String organizer_name) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.start_datetime = startTime;
            this.end_datetime = endTime;
            this.organizer_name = organizer_name;
            this.partisipan = new ArrayList<>();
        }

        public void setPartisipan(ArrayList<String> partisipan) {
            this.partisipan = partisipan;
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

        public String getOrganizerName() {
            return organizer_name;
        }

        public String getDate() {
            return "";
        }

        public String getPartisipan() {
            String result = "";

            for (int i = 0; i < partisipan.size(); i++) {
                if (i != 0) {
                    result += "\n";
                }
                result += partisipan.get(i);
            }
            return result;
        }
    }

    private ArrayList<Pertemuan> arr;
    private PertemuanPresenter pertemuanPresenter;
    private RequestQueue queue;
    private Gson gson;

    public PertemuanList(PertemuanPresenter presenter, Context context) {
        this.arr = new ArrayList<>();
        this.pertemuanPresenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public PertemuanList(){
        this.arr = new ArrayList<>();
    }

    public void addPertemuan(Pertemuan pertemuan){
        this.arr.add(pertemuan);
    }

    public int getSize() {
        return this.arr.size();
    }

    public Pertemuan getPertemuan(int i) {
        return this.arr.get(i);
    }

    //id global = c549e314-73db-4adf-8ef7-82c1bf89a527
    public void getPertemuan(String startDate, String endDate) {
        String url = APIClient.BASE_URL + "/appointments" + "/start-date" + "/" + startDate + "/end-date" + "/" + endDate;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                this::onResponse,
                this::onErrorResponse
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };

        this.queue.add(request);
    }

    @Override
    public void onResponse(String response) {
        Type listType = new TypeToken<ArrayList<Pertemuan>>() {
        }.getType();
        this.arr = this.gson.fromJson(response, listType);
        this.pertemuanPresenter.onSuccessGet(this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "PertemuanList: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIUbahPertemuan: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }

}

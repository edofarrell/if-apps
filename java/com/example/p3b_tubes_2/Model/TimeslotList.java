package com.example.p3b_tubes_2.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.CustomJsonRequest;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeslotList {

    public class Timeslot {
        private String day;
        private String start_time;
        private String end_time;

        public String getDay() {
            return day;
        }

        public String getStartTime() {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mmZ");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

            String startTime = "";

            try {
                startTime = timeFormatter.format(inputFormatter.parse(this.start_time));
            } catch (ParseException e) {
                Log.d("DEBUG", "TimeSlot, getStartTime() catch ParseException");
            }

            return startTime;
        }

        public String getEndTime() {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mmZ");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

            String endTime = "";

            try {
                endTime = timeFormatter.format(inputFormatter.parse(this.end_time));
            } catch (ParseException e) {
                Log.d("DEBUG", "TimeSlot, getEndTime() catch ParseException");
            }

            return endTime;
        }
    }

    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private List<Timeslot> listTimeslot;
    private static APITimeslotGet apiGet;
    private static APITimeslotAdd apiAdd;

    public TimeslotList(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
        this.listTimeslot = new ArrayList<>();

        this.apiGet = new APITimeslotGet();
        this.apiAdd = new APITimeslotAdd();
    }

    public TimeslotList(){
        this.listTimeslot = new ArrayList<>();
    }

    public Timeslot get(int i){
        return this.listTimeslot.get(i);
    }

    public int size(){
        return this.listTimeslot.size();
    }

    private class APITimeslotGet implements Response.Listener<JSONArray>, Response.ErrorListener {

        public void get(String lecturerId) {
            String url = APIClient.BASE_URL + "/lecturer-time-slots" + "/lecturers" + "/" + lecturerId;

            CustomJsonRequest request = new CustomJsonRequest(
                    Request.Method.GET,
                    url,
                    null,
                    this::onResponse,
                    this::onErrorResponse)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };

            queue.add(request);
        }

        @Override
        public void onResponse(JSONArray response) {
            Type listType = new TypeToken<ArrayList<Timeslot>>() {}.getType();
            List<Timeslot> timeSlot = gson.fromJson(response.toString(), listType);
            listTimeslot = timeSlot;
            presenter.onSuccessGetTimeSlot(TimeslotList.this);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "TimeslotList: APITimeslotGet: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorGetTimeSlot(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "TimeslotList: APITimeslotGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public class APITimeslotAdd implements Response.Listener<JSONObject>, Response.ErrorListener{

        public void add(String day, String startTime, String endTime) {
            String url = APIClient.BASE_URL+"/lecturer-time-slots";

            JsonObject json = new JsonObject();
            json.addProperty("day",day);
            json.addProperty("start_time",startTime);
            json.addProperty("end_time",endTime);
            JSONObject JSON = null;
            try {
                JSON = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "TimeslotList: APITimeslotAdd: add() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,JSON,
                    this::onResponse,this::onErrorResponse){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };
            queue.add(request);
        }

        @Override
        public void onResponse(JSONObject response) {
            presenter.onSuccessAddTimeSlot();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "TimeslotList: APITimeslotAdd: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorAddTimeSlot(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "TimeslotList: APITimeslotAdd: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void fetch(String lecturerId){
        apiGet.get(lecturerId);
    }

    public static void addTimeslot(String day, String startTime, String endTime){
        apiAdd.add(day, startTime, endTime);
    }

}

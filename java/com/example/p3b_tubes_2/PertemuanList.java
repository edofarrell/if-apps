package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PertemuanList {
    class Pertemuan{
        private String id;//uuidv4
        private String organizer_id;
        private String title;//length:1-256
        private String description;//length:1-1000
        private String start_datetime;//2022-04-12 19:00+0500
        private String end_datetime;//2022-04-12 19:00+0500

        public Pertemuan(String id,String organizerId, String title, String description, String startTime, String endTime){
            this.id = id;
            this.organizer_id = organizerId;
            this.title = title;
            this.description = description;
            this.start_datetime = startTime;
            this.end_datetime = endTime;
        }

        public String getId(){
            return id;
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
    }

    private ArrayList<Pertemuan> arr;
    private String token;
    private PertemuanPresenter pertemuanPresenter;
    private RequestQueue queue;
    private Gson gson;

    public PertemuanList(PertemuanPresenter presenter, Context context, String token){
        arr = new ArrayList<>();
        this.pertemuanPresenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
        this.token = token;
    }
    //id global = c549e314-73db-4adf-8ef7-82c1bf89a527
    public void getPertemuan(String id, String startDate, String endDate){
        String url = APIClient.BASE_URL+"/appointments"+"/"+id+"/"+startDate+"/"+endDate;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DEBUG",response);
                Type listType = new TypeToken<ArrayList<Pertemuan>>(){}.getType();
                arr = gson.fromJson(response,listType);
                /*for(int i = 0;i<arr.size();i++){
                    Log.d("DEBUG",arr.get(i).getId());
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data,"utf-8");
                    Log.d("DEBUG",responseBody);
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization",APIClient.token);
                return params;
            }
        };
        this.queue.add(request);
    }

    public void addPertemuan(String title, String description, String startTime, String endTime) throws JSONException {
//        Log.d("DEBUG","["+token+"]");
        String url = APIClient.BASE_URL+"/appointments";
        JsonObject json = new JsonObject();
        json.addProperty("title",title);
        json.addProperty("description",description);
        json.addProperty("start_datetime",startTime);
        json.addProperty("end_datetime",endTime);
        JSONObject JSON = new JSONObject(json.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url, JSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String desc = response.getString("description");
                            String title = response.getString("title");
                            String startTime = response.getString("start_datetime");
                            String endTime = response.getString("end_datetime");
                            String org_id = response.getString("organizer_id");
                            arr.add(new Pertemuan(id,org_id,title,desc,startTime,endTime));
                            pertemuanPresenter.onFinished(id);
                            Log.d("DEBUG","SUCCESS");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{
                            String responseBody = new String(error.networkResponse.data,"utf-8");
                            Log.d("DEBUG",responseBody);
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization",APIClient.token);
                return params;
            }
        };
        this.queue.add(request);
    }
}

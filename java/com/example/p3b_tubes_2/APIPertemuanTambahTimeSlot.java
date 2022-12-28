package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIPertemuanTambahTimeSlot implements Response.Listener<JSONObject>, Response.ErrorListener{
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private PertemuanList.Pertemuan pertemuan;

    public APIPertemuanTambahTimeSlot(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getTimeSlot(String lecturerId){
        String url = APIClient.BASE_URL+"/lecturer-time-slots"+"/lecturers"+"/"+lecturerId;
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,url,null,
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
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPertemuanGetTimeSlot: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanGetTimeSlot: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("DEBUG","SUCCESS");
    }
}

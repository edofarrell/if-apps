package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeSlot;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPertemuanGetTimeSlot implements Response.Listener<JSONArray>, Response.ErrorListener {
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIPertemuanGetTimeSlot(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getTimeSlot(String lecturerId) {
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
        Log.d("DEBUG", "SUCCESS");
        Type listType = new TypeToken<ArrayList<TimeSlot>>() {}.getType();
        List<TimeSlot> timeSlot = this.gson.fromJson(response.toString(), listType);
        this.presenter.onSuccessGetTimeSlot(timeSlot);
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
}

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

public class PengumumanList implements Response.Listener<String>, Response.ErrorListener {

    class Pengumuman {
        private String id;
        private String title;
        private Tag[] tags;
    }

    class Tag {
        private String name;
        private String id;
    }

    private ArrayList<Pengumuman> arr;
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public PengumumanList(PengumumanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getPengumuman() {
        String url = APIClient.BASE_URL + "/announcements";

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
        Type listType = new TypeToken<ArrayList<Pengumuman>>(){}.getType();
        this.arr = this.gson.fromJson(response,listType);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "PengumumanList: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "PengumumanList: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }

}

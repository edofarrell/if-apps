package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APITambahPengumuman {
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APITambahPengumuman(PengumumanPresenter presenter, Context context){
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void addPengumuman(String title, String content, String[] idTags) throws JSONException {
        String url = APIClient.BASE_URL+"/announcements";
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for(int i = 0;i<idTags.length;i++){
            array.add(idTags[i]);
        }
        json.addProperty("title",title);
        json.addProperty("content",content);
        json.add("tags",array);
        JSONObject JSON = new JSONObject(json.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DEBUG","Tambah Pengumuman");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.d("DEBUG", "APITambahPengumuman: onErrorResponse(), Error=" + responseBody);
                } catch (UnsupportedEncodingException e) {
                    Log.d("DEBUG", "APITambahPengumuman: onErrorResponse() catch UnsupportedEncodingException");
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };
        queue.add(request);
    }
}

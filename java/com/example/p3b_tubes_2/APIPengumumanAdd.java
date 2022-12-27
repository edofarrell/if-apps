package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

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

public class APIPengumumanAdd implements Response.Listener<JSONObject>, Response.ErrorListener {
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIPengumumanAdd(PengumumanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void addPengumuman(String title, String content, String[] idTags){
        String url = APIClient.BASE_URL + "/announcements";

        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for (int i = 0; i < idTags.length; i++) {
            array.add(idTags[i]);
        }
        json.addProperty("title", title);
        json.addProperty("content", content);
        json.add("tags", array);

        JSONObject JSON = null;
        try {
            JSON = new JSONObject(json.toString());
        } catch (JSONException e) {
            Log.d("DEBUG", "APIPengumumanAdd: addPengumuman() catch JSONException");;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                JSON,
                this::onResponse,
                this::onErrorResponse
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };

        queue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        PengumumanList.Pengumuman pengumuman = this.gson.fromJson(response.toString(), PengumumanList.Pengumuman.class);
        this.presenter.AddOnSuccess(pengumuman);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPengumumanAdd: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPengumumanAdd: onErrorResponse() catch UnsupportedEncodingException");
        }
    }
}

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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIDeletePengumuman implements Response.Listener<JSONObject>, Response.ErrorListener {
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIDeletePengumuman(PengumumanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void deletePengumuman(String idPengumuman) {
        String url = APIClient.BASE_URL + "/announcements" + "/" + idPengumuman;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
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
        queue.add(request);
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d("DEBUG", "Delete Pengumuman");
        this.presenter.deleteOnSuccess();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIDeletePengumuman: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIDeletePengumuman: onErrorResponse() catch UnsupportedEncodingException");
        }
    }
}

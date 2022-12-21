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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIUbahPertemuan implements Response.Listener<JSONObject>, Response.ErrorListener {
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIUbahPertemuan(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void ubahPertemuan(PertemuanList.Pertemuan pertemuan) throws JSONException {
        String url = APIClient.BASE_URL + "/appointments/" + pertemuan.getId();

//        JsonObject json = new JsonObject();
//        json.addProperty("id", id);
//        json.addProperty("title", title);
//        json.addProperty("description", description);
//        json.addProperty("start_datetime", start_datetime);
//        json.addProperty("end_datetime", end_datetime);
//        JSONObject JSON = new JSONObject(json.toString());
        JSONObject JSON = new JSONObject(this.gson.toJson(pertemuan));

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                JSON,
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
    public void onResponse(JSONObject response) {
        PertemuanList.Pertemuan newPertemuan = this.gson.fromJson(response.toString(), PertemuanList.Pertemuan.class);
        this.presenter.onSuccessChange();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIUbahPertemuan: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIUbahPertemuan: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }


}

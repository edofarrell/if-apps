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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIPertemuanDeleteParticipants implements Response.Listener<JSONArray>, Response.ErrorListener {
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIPertemuanDeleteParticipants(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void deleteParticipants(String[] participantsId) throws JSONException {
        String url = APIClient.BASE_URL + "/appointments" + "/26783a4f-9b00-4a35-ab44-da9214794efb" + "/participants" + "/delete";

        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for (int i = 0; i < participantsId.length; i++) {
            array.add(participantsId[i]);
        }
        json.addProperty("appointment_id", "26783a4f-9b00-4a35-ab44-da9214794efb");
        json.add("participants", array);
        JSONObject jsonObject = new JSONObject(json.toString());

        CustomJsonRequest request = new CustomJsonRequest(
                Request.Method.POST,
                url,
                jsonObject,
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
    public void onResponse(JSONArray response) {
        presenter.onSuccessDeleteParticipants(response.toString());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPertemuanDeleteParticipants: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanDeleteParticipants: onErrorResponse() catch UnsupportedEncodingException");
        }
    }
}

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

public class APIPertemuanAddParticipants implements Response.Listener<JSONArray>, Response.ErrorListener {
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private User[] queryUsers;

    public APIPertemuanAddParticipants(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void addParticipants(User[] participants, String idPertemuan) {
        this.queryUsers = participants;
        String url = APIClient.BASE_URL + "/appointments" + "/" + idPertemuan + "/participants";

        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for (int i = 0; i < participants.length; i++) {
            array.add(participants[i].getId());
        }
        json.add("participants", array);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json.toString());
        } catch (JSONException e) {
            Log.d("DEBUG", "APIPertemuanAddParticipants: addParticipants() catch JSONException");
        }

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
        Log.d("DEBUG", "SUCCESS ADD PARTICIPANTS");
        this.presenter.onSuccessAddParticipants(this.queryUsers);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPertemuanAddParticipants: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanAddParticipants: onErrorResponse() catch UnsupportedEncodingException");
        }
    }
}

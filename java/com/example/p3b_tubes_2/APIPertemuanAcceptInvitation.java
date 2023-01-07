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
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIPertemuanAcceptInvitation implements Response.Listener<JSONObject>, Response.ErrorListener{
    private RequestQueue queue;
    private Gson gson;
    private PertemuanPresenter presenter;

    public APIPertemuanAcceptInvitation(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void acceptInvitation(String appointmentId, String userId) {
        String url = APIClient.BASE_URL + "/appointments" + "/" + appointmentId + "/participants" + "/" + userId;

        JsonObject json = new JsonObject();
        json.addProperty("attending", true);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json.toString());
        } catch (JSONException e) {
            Log.d("DEBUG", "APIPertemuanAcceptInvitation: acceptInvitation() catch JSONException");
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PATCH,
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
    public void onResponse(JSONObject response) {
        this.presenter.onSuccessChangeInvites();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPertemuanAcceptInvitation: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanAcceptInvitation: onErrorResponse() catch UnsupportedEncodingException");
        }
    }
}

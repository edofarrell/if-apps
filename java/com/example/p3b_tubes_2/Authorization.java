package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

    public class Authorization implements Response.Listener<JSONObject>, Response.ErrorListener {

    private UserPresenter userPresenter;
    private RequestQueue queue;

    public Authorization(UserPresenter userPresenter, Context context) {
        this.userPresenter = userPresenter;
        this.queue = Volley.newRequestQueue(context);
    }

    public void login(String email, String password, String role) throws JSONException {
        String url = APIClient.BASE_URL + "/authenticate";

        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("role", role);
        JSONObject jsonObject = new JSONObject(json.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                this::onResponse,
                this::onErrorResponse
        );

        this.queue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        String token;
        try {
            token = "Bearer " + response.getString("token");
            APIClient.token = token;
            userPresenter.onSuccessLogin();
        } catch (JSONException e) {
            Log.d("DEBUG", "Authorization: onResponse() catch JSONException");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "Authorization: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "Authorization: onErrorResponse() catch UnsupportedEncodingException");
        }
        userPresenter.onFailedLogin();
    }

}

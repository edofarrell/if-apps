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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIGetCurrentUser implements Response.Listener<String>, Response.ErrorListener {
    private UserPresenter loginPresenter;
    private RequestQueue queue;
    private Gson gson;

    public APIGetCurrentUser(UserPresenter loginPresenter, Context context) {
        this.loginPresenter = loginPresenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getCurrentUser() {
        String url = APIClient.BASE_URL + "/users/self";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                this::onResponse,
                this::onErrorResponse
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };

        this.queue.add(request);
    }

    @Override
    public void onResponse(String response) {
        User user = this.gson.fromJson(response, User.class);
        APIClient.loggedInId = user.getId();
        this.loginPresenter.onSuccessGetCurrentUser(user);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "User: APIGetCurrentUser(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "User: APIGetCurrentUser() catch UnsupportedEncodingException");
        }
        //handle error here
    }
}

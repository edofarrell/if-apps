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
import java.util.List;
import java.util.Map;

public class User implements Response.Listener<String>, Response.ErrorListener {
    private String id;
    private String name;
    private String email;
    private String archived_at;
    private String[] roles;

    private UserPresenter loginPresenter;
    private RequestQueue queue;
    private Gson gson;

    public User(UserPresenter loginPresenter, Context context) {
        this.loginPresenter = loginPresenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDosen(){
        for(int i=0; i<roles.length; i++){
            if(roles[i].equals("lecturer"))
                return true;
        }
        return false;
    }

    public void getUsers() {
        String url = APIClient.BASE_URL + "/users";

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
        Type listType = new TypeToken<List<User>>(){}.getType();
        List<User> users = this.gson.fromJson(response, listType);
        this.loginPresenter.onSuccessGet(users);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "User: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "User: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }
}

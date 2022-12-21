package com.example.p3b_tubes_2;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class User implements Response.Listener<JSONObject>, Response.ErrorListener{
    private String name;
    private String email;
    private String id;
    private String[] roles;
    private String token;

    public User(){

    }

    public void login(String email, String password, String role){

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}

package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Response.Listener<String>, Response.ErrorListener{
    private String id;
    private String name;
    private String email;
    private String archived_at;
    private String[] roles;

    private String token;
    private LoginPresenter loginPresenter;
    private RequestQueue queue;
    private Gson gson;

    public User(LoginPresenter loginPresenter, Context context) {
        this.loginPresenter = loginPresenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }


    public String getToken() {
        return this.token;
    }

    public void login(String email, String password, String role) throws JSONException {
        String endPoint = "/authenticate";

        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("role", role);
        JSONObject jsonObject = new JSONObject(json.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                APIClient.BASE_URL + endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            token = "Bearer " + response.getString("token");
                            getUsers();
                            loginPresenter.onSuccessLogin(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            Log.d("DEBUG", responseBody);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        loginPresenter.onFailedLogin();
                    }
                }
        );

        this.queue.add(request);
    }

    public void getUsers() {
        String endPoint = "/users";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                APIClient.BASE_URL + endPoint,
                this::onResponse,
                this::onErrorResponse
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };
        this.queue.add(request);
    }

    @Override
    public void onResponse(String response) {
<<<<<<< HEAD
        //User[] users = this.gson.fromJson(response, User.class);
=======
        ArrayList<User> users = this.gson.fromJson(response, ArrayList.class);
//        this.loginPresenter.something(users);
>>>>>>> origin/edo
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", responseBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

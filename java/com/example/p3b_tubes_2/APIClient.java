package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

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

public class APIClient {
    private final String BASE_URL = "http://ifportal.labftis.net/api/v1";
    private PengumumanPresenter pengumumanPresenter;
    private PertemuanPresenter pertemuanPresenter;
    private FRSPresenter frsPresenter;
    private Context context;
    private RequestQueue queue;
    private Gson gson;
    private String token;

    public APIClient(PengumumanPresenter pengumumanPresenter, PertemuanPresenter pertemuanPresenter, FRSPresenter frsPresenter, Context context) {
        this.pengumumanPresenter = pengumumanPresenter;
        this.pertemuanPresenter = pertemuanPresenter;
        this.frsPresenter = frsPresenter;
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        this.gson = new Gson();
    }

    public void login(String email, String password, String role) throws JSONException {
        String endPoint = "/authenticate";

        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("role", role);
        JSONObject jsonObject = new JSONObject(json.toString());
        Log.d("DEBUG", jsonObject.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                this.BASE_URL + endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            token = response.getString("token");
                            //pindah halaman
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DEBUG", error.toString());

                    }
                }
        );

        this.queue.add(request);
    }

    public void getPengumuman() {
        JsonObject jsonObject = new JsonObject();
    }

    public void getPertemuan(String id, String start_date, String end_date) {
        String endPoint = "/appointments/" + id + "/" + start_date + "/" + end_date;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                this.BASE_URL + endPoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pertemuanPresenter.onFinished();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pertemuanPresenter.onError();
                    }
                }
        );
        this.queue.add(request);
    }

}

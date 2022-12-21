package com.example.p3b_tubes_2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class APIClient {
    public static final String BASE_URL = "http://ifportal.labftis.net/api/v1";
    private LoginPresenter loginPresenter;
    private PengumumanPresenter pengumumanPresenter;
    private PertemuanPresenter pertemuanPresenter;
    private FRSPresenter frsPresenter;
    private Context context;
    private RequestQueue queue;
    private Gson gson;
    private String token;

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

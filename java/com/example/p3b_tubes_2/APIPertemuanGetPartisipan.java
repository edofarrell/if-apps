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
import java.util.Map;

public class APIPertemuanGetPartisipan implements Response.Listener<String>, Response.ErrorListener {

    class Partisipan {
        private String id;
        private String name;
        private boolean attending;
    }

    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private PertemuanList.Pertemuan pertemuan;

    public APIPertemuanGetPartisipan(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getPartisipan(PertemuanList.Pertemuan pertemuan) {
        this.pertemuan = pertemuan;
        String url = APIClient.BASE_URL + "/appointments/" + pertemuan.getId() + "/participants";

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
        Type listType = new TypeToken<ArrayList<Partisipan>>() {}.getType();
        ArrayList<Partisipan> partisipans = this.gson.fromJson(response, listType);

        ArrayList<String> attending = new ArrayList<>();
        for (int i = 0; i < partisipans.size(); i++) {
            if (partisipans.get(i).attending) {
                attending.add(partisipans.get(i).name);
            }
        }

        this.pertemuan.setPartisipan(attending);
        this.presenter.onSuccessGetPartisipanDibuat(this.pertemuan);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIPertemuanGetPartisipan: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanGetPartisipan: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }
}

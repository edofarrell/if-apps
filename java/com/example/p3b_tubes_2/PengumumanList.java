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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PengumumanList implements Response.Listener<String>, Response.ErrorListener {

    class Pengumuman {
        private String id;
        private String title;
        public ArrayList<Tag> tags;

        public String getTitle() {
            return title;
        }

        public String getTags() {
            String res = "";
            for (int i = 0; i < tags.size(); i++) {
                if (i != 0) {
                    res += ",";
                }
                res += tags.get(i).getName();
            }
            return res;
        }
    }

    class Tag {
        private String tag;
        private String tag_id;

        public String getName() {
            return tag;
        }
    }

    private ArrayList<Pengumuman> data;
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private String cursor;

    public PengumumanList(PengumumanPresenter presenter, Context context) {
        this.data = new ArrayList<>();
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public PengumumanList(){
        this.data = new ArrayList<>();
    }

    public int getSize() {
        return this.data.size();
    }

    public Pengumuman getPengumuman(int i) {
        return this.data.get(i);
    }

    public void getPengumuman() {
        String url = APIClient.BASE_URL + "/announcements";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
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

        this.queue.add(request);
    }

    @Override
    public void onResponse(String response) {
        PengumumanList pengumumanList = this.gson.fromJson(response, PengumumanList.class);
        Log.d("DEBUG", pengumumanList.data.get(0).tags.size()+"");
        presenter.OnSuccessGet(pengumumanList);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "PengumumanList: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "PengumumanList: onErrorResponse() catch UnsupportedEncodingException");
        }
        //handle error here
    }

}

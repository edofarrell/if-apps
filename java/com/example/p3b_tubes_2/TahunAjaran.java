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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TahunAjaran {
    private TahunAjar activeYear;
    private ArrayList<TahunAjar> listAcademicYears;
    private FRSPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public TahunAjaran(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public TahunAjar getActiveYear() {
        return activeYear;
    }

    public ArrayList<TahunAjar> getListAcademicYears() {
        return listAcademicYears;
    }

    public void getAcademicYears(){
        String url = APIClient.BASE_URL+"/academic-years";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DEBUG","Academic Years");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.d("DEBUG", "TahunAjaran: onErrorResponse(), Error=" + responseBody);
                } catch (UnsupportedEncodingException e) {
                    Log.d("DEBUG", "TahunAjaran: onErrorResponse() catch UnsupportedEncodingException");
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };
        queue.add(request);
    }

    public class TahunAjar{
        private String tahun;
        private String semester;

        public TahunAjar(String tahun, String semester){
            this.tahun = tahun;
            this.semester = semester;
        }
    }
}

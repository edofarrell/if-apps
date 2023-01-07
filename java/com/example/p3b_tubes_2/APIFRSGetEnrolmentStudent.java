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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APIFRSGetEnrolmentStudent implements Response.Listener<JSONArray>, Response.ErrorListener{
    private FRSPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIFRSGetEnrolmentStudent(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getEnrolmentStudent(String academicYear) throws JSONException {
        String url = APIClient.BASE_URL+"/enrolments/academic-years/"+academicYear;

        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,url,null,
                this::onResponse,this::onErrorResponse){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", APIClient.token);
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "APIFRSEnrolmentStudent: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIFRSEnrolmentStudent: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        ArrayList<String> namaMatKul = new ArrayList<>();
        Log.d("DEBUG","getMatKulEnrol");
        for(int i = 0;i<response.length();i++){
            try {
                namaMatKul.add(response.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

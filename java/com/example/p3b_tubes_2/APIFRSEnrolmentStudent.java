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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIFRSEnrolmentStudent implements Response.Listener<JSONObject>, Response.ErrorListener{
    private FRSPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIFRSEnrolmentStudent(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void enrolStudent(String id, String academicYear) throws JSONException {
        String url = APIClient.BASE_URL+"/enrolments";
        JsonObject json = new JsonObject();
        json.addProperty("course_id",id);
        json.addProperty("academic_year",Integer.parseInt(academicYear));
        JSONObject JSON = new JSONObject(json.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,JSON,
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
    public void onResponse(JSONObject response) {
        Log.d("DEBUG","enrolstudent");
    }
}

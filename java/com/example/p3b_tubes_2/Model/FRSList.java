package com.example.p3b_tubes_2.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.CustomJsonRequest;
import com.example.p3b_tubes_2.Presenter.FRSPresenter;
import com.example.p3b_tubes_2.MataKuliahList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FRSList {
    private FRSPresenter presenter;
    private Gson gson;
    private RequestQueue queue;
    private static APIFRSEnrolStudent apiEnrolStudent;
    private static APIFRSGetEnrolmentStudent apiEnrolmentStudent;

    public FRSList(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        this.gson = new Gson();
        this.queue = Volley.newRequestQueue(context);

        this.apiEnrolStudent = new APIFRSEnrolStudent();
        this.apiEnrolmentStudent = new APIFRSGetEnrolmentStudent();
    }

    private class APIFRSEnrolStudent implements Response.Listener<JSONObject>, Response.ErrorListener{

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
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(responseBody);
                JSONObject JSON = new JSONObject(json.toString());
                Log.d("DEBUG", "APIFRSEnrolmentStudent: onErrorResponse(), Error=" + JSON.toString());
                String errorCode = JSON.getString("errcode");
                if(errorCode.equals("E_UNSATISFIED_PREREQUISITE")){
                    String code = JSON.getJSONArray("reason").getJSONObject(0).getString("code");
                    String name = JSON.getJSONArray("reason").getJSONObject(0).getString("name");
                    presenter.OnErrorEnrolStudent(name,code);
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                Log.d("DEBUG", "APIFRSEnrolmentStudent: onErrorResponse() catch UnsupportedEncodingException");
            }
        }

        @Override
        public void onResponse(JSONObject response) {
            Log.d("DEBUG","enrolstudent");
            presenter.OnSuccessEnrolStudent();
        }
    }

    private class APIFRSGetEnrolmentStudent implements Response.Listener<JSONArray>, Response.ErrorListener{

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
            ArrayList<MataKuliahList.MataKuliah> listdata = new ArrayList<>();
            String res = response.toString();
            Type listType = new TypeToken<ArrayList<MataKuliahList.MataKuliah>>() {}.getType();
            listdata = gson.fromJson(res, listType);
            //Log.d("DEBUG",namaMatKul.size()+"");
            presenter.OnSuccessGetMataKuliahEnrolment(listdata);
        }
    }

    public static void enrollStudent(String id, String academicYear) throws JSONException {
        apiEnrolStudent.enrolStudent(id,academicYear);
    }

    public static void getEnrollmentStudent(String academicYear) throws JSONException {
        apiEnrolmentStudent.getEnrolmentStudent(academicYear);
    }
}

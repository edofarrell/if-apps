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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TahunAjaran implements Response.Listener<JSONObject>, Response.ErrorListener{
    private TahunAjar activeYear;
    private ArrayList<TahunAjar> listAcademicYears;
    private FRSPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public TahunAjaran(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        listAcademicYears = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public TahunAjar getActiveYear() {
        return activeYear;
    }

    public ArrayList<TahunAjar> getListAcademicYears() {
        return listAcademicYears;
    }

    public TahunAjaran(){
        this.listAcademicYears = new ArrayList<>();
    }

    public int getSize(){
        return listAcademicYears.size();
    }

    public TahunAjar getAcademicYear(int i){
        return this.listAcademicYears.get(i);
    }

    public void getAcademicYears(){
        String url = APIClient.BASE_URL+"/academic-years";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                this::onResponse,this::onErrorResponse){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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
            Log.d("DEBUG", "TahunAjaran: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "TahunAjaran: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            this.activeYear = new TahunAjar(response.getString("active_year").substring(0,4),
                    response.getString("active_year").substring(4));
            JSONArray array = response.getJSONArray("academic_years");
            for(int i = 0;i<array.length();i++){
                String tahun = array.getString(i).substring(0,4);
                String sem = array.getString(i).substring(4);
                if(sem.equals("1")){
                    sem = "Ganjil";
                }
                else if(sem.equals("2")){
                    sem = "Genap";
                }
                else{
                    sem = "Pendek";
                }
                listAcademicYears.add(new TahunAjar(tahun,sem));
            }
            presenter.OnSuccessGet(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class TahunAjar{
        private String tahun;
        private String semester;

        public TahunAjar(String tahun, String semester){
            this.tahun = tahun;
            this.semester = semester;
        }

        public String getTahun() {
            return tahun;
        }

        public String getSemester() {
            return semester;
        }

        public String toString(){
            String tahun = this.tahun+"/"+(Integer.parseInt(this.tahun)+1)+"";
            String result = "Semester "+this.semester+" "+tahun;
            return result;
        }
    }
}

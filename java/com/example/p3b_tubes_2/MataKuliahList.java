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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MataKuliahList implements Response.Listener<JSONArray>, Response.ErrorListener{

    class MataKuliah{
        private String id;//uuidv4
        private String name;//length:1-256
        private String code;//length:5-15
        private int semester;//range:1-8

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public int getSemester() {
            return semester;
        }
    }

    private ArrayList<MataKuliah> listMataKuliah;
    private FRSPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private int tempSemester;
    private String tempTahunAjar;

    public MataKuliahList(FRSPresenter presenter, Context context){
        this.presenter = presenter;
        this.gson = new Gson();
        this.listMataKuliah = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
    }

    public MataKuliahList(){
        this.listMataKuliah = new ArrayList<>();
    }

    public void getMataKuliah(int semester, String tahunAjar){
        listMataKuliah.clear();
        String url = APIClient.BASE_URL+"/courses";
        this.tempSemester = semester;
        this.tempTahunAjar = tahunAjar;
        CustomJsonRequest request = new CustomJsonRequest(Request.Method.GET,url,null,
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
            Log.d("DEBUG", "MataKuliahList: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "MataKuliahList: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        ArrayList<MataKuliah> listdata = new ArrayList<>();
        String res = response.toString();
        Type listType = new TypeToken<ArrayList<MataKuliah>>() {}.getType();
        listdata = this.gson.fromJson(res, listType);
        for(int i = 0;i<listdata.size();i++){
            if(listdata.get(i).semester==tempSemester){
                listMataKuliah.add(listdata.get(i));
            }
        }
        this.presenter.OnSuccessGetDetail(listMataKuliah,this.tempTahunAjar);
    }

}

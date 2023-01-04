package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPertemuanGetInvites implements Response.Listener<JSONArray>, Response.ErrorListener{
    class Invites{
        String appointment_id;
        String title;
        String description;
        boolean attending;
    }

    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private ArrayList<APIPertemuanGetInvites.Invites> listInvites;

    public APIPertemuanGetInvites(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public APIPertemuanGetInvites(){
        this.listInvites = new ArrayList<>();
    }

    public int getListSize(){
        return this.listInvites.size();
    }

    public Invites getInvitation(int i){
        return this.listInvites.get(i);
    }

    public void getInvites(){
        String url = APIClient.BASE_URL+"/appointments/invitations";
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
            Log.d("DEBUG", "APIPertemuanGetInvites: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "APIPertemuanGetInvites: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("DEBUG","Invites");
        Type listType = new TypeToken<ArrayList<APIPertemuanGetInvites.Invites>>() {}.getType();
        ArrayList<APIPertemuanGetInvites.Invites> listInvites = this.gson.fromJson(response.toString(), listType);
        this.listInvites = listInvites;
        this.presenter.onSuccessGetInvites(this);
    }
}

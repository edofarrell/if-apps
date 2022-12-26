package com.example.p3b_tubes_2;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class APIAddParticipantsPertemuan {
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public APIAddParticipantsPertemuan(PertemuanPresenter presenter, Context context){
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void addParticipants(PertemuanList.Pertemuan pertemuan){
        String url = APIClient.BASE_URL+"/appointments"+"id"+"/participants";
        JsonObject json = new JsonObject();
        json.addProperty("appointment_id","id");
        //json.addProperty("participants",);
    }
}

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
import java.util.List;
import java.util.Map;

public class TagList implements Response.Listener<String>, Response.ErrorListener {

    class Tag {
        private String id;
        private String tag;

        public String getName() {
            return tag;
        }

        public String getId() {
            return id;
        }
    }

    private ArrayList<Tag> listTag;
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;

    public TagList(PengumumanPresenter presenter, Context context) {
        this.listTag = new ArrayList<>();
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();
    }

    public void getTag() {
        String url = APIClient.BASE_URL + "/tags";

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
        queue.add(request);
    }

    @Override
    public void onResponse(String response) {
        Type listType = new TypeToken<ArrayList<TagList.Tag>>() {}.getType();
        listTag = gson.fromJson(response, listType);
        presenter.GetTagOnSuccess(listTag);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("DEBUG", "TagList: onErrorResponse(), Error=" + responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("DEBUG", "TagList: onErrorResponse() catch UnsupportedEncodingException");
        }
    }

    public List<String> getActiveTagFilter(List<String> arr) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < listTag.size(); j++) {
                if (arr.get(i).equals(listTag.get(j).getName())) {
                    result.add(listTag.get(j).getId());
                    break;
                }
            }
        }
        return result;
    }
}

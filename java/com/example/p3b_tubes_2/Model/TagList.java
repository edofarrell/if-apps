package com.example.p3b_tubes_2.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Presenter.PengumumanPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagList {

    public class Tag {
        private String id;
        private String tag;
        private String tag_id;

        public String getName() {
            return tag;
        }

        public String getId() {
            if (id == null) {
                return tag_id;
            } else {
                return id;
            }
        }
    }

    private ArrayList<Tag> listTag;
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private static APITagGet apiGet;
    private static APITagAdd apiAdd;

    public TagList(PengumumanPresenter presenter, Context context) {
        this.listTag = new ArrayList<>();
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();

        this.apiGet = new APITagGet();
        this.apiAdd = new APITagAdd();
    }

    public TagList(){
        this.listTag = new ArrayList<>();
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

    public Tag get(int i){
        return this.listTag.get(i);
    }

    public int size(){
        return this.listTag.size();
    }

    private class APITagGet implements Response.Listener<String>, Response.ErrorListener {

        public void get() {
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
            Type listType = new TypeToken<ArrayList<TagList.Tag>>() {
            }.getType();
            listTag = gson.fromJson(response, listType);
            presenter.GetTagOnSuccess(TagList.this);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "TagList: APITagGet: onErrorResponse(), Error=" + responseBody);
                presenter.GetTagOnError(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "TagList: APITagGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APITagAdd implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void add(String tag) {
            String url = APIClient.BASE_URL + "/tags";

            JsonObject json = new JsonObject();
            json.addProperty("tag", tag);
            JSONObject JSON = null;
            try {
                JSON = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "TagList: APITagAdd: addTag() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    JSON,
                    this::onResponse,
                    this::onErrorResponse
            ) {
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
        public void onResponse(JSONObject response) {
            presenter.AddTagOnSuccess();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "TagList: APITagAdd: onErrorResponse(), Error=" + responseBody);

                String msg;
                APIError err = gson.fromJson(responseBody, APIError.class);
                if(err.getErrcode().equals("E_DUPLICATE")){
                    msg = "Tag sudah pernah ditambahkan";
                }else{
                    msg = responseBody;
                }

                presenter.AddTagOnError(msg);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "TagList: APITagAdd: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void fetch() {
        apiGet.get();
    }

    public static void addTag(String tag) {
        apiAdd.add(tag);
    }
}

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
import com.example.p3b_tubes_2.APIError;
import com.example.p3b_tubes_2.Presenter.PengumumanPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PengumumanList {

    public class Pengumuman {
        private String id;
        private String title;
        private String updated_at;
        private String created_at;
        private TagList.Tag[] tags;
        private String content;

        public String getTitle() {
            return title;
        }

        public String getId() {
            return this.id;
        }

        public String getContent() {
            return this.content;
        }

        public String getTags() {
            String res = "";
            for (int i = 0; i < tags.length; i++) {
                if (i != 0) {
                    res += ",";
                }
                res += tags[i].getName();
            }
            return res;
        }
    }

    class Cursor {
        String next;

        public String getCursor() {
            if (this.next == null) {
                return "none";
            }
            return this.next;
        }
    }

    private ArrayList<Pengumuman> data;
    private PengumumanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private Cursor metadata;
    private static APIPengumumanGet apiGet;
    private static APIPengumumanDelete apiDelete;
    private static APIPengumumanAdd apiAdd;
    private static APIPengumumanDetail apiDetail;

    public PengumumanList() {
        this.data = new ArrayList<>();
    }

    public PengumumanList(PengumumanPresenter presenter, Context context) {
        this.data = new ArrayList<>();
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();

        this.apiGet = new APIPengumumanGet();
        this.apiDelete = new APIPengumumanDelete();
        this.apiAdd = new APIPengumumanAdd();
        this.apiDetail = new APIPengumumanDetail();
    }

    public String getCursor() {
        return this.metadata.getCursor();
    }

    public int getSize() {
        return this.data.size();
    }

    public Pengumuman getPengumuman(int i) {
        return this.data.get(i);
    }

    public void delete(Pengumuman p) {
        for (int i = 0; i < this.data.size(); i++) {
            Pengumuman curr = this.data.get(i);
            if (curr.getId().equals(p.getId())) {
                this.data.remove(i);
                break;
            }
        }
    }

    public void add(Pengumuman p){
        this.data.add(0, p);
    }

    public ArrayList<Pengumuman> getData() {
        return data;
    }

    public void setData(ArrayList<Pengumuman> data) {
        this.data = data;
    }

    public void addData(ArrayList<Pengumuman> pengumumanList) {
        for (int i = 0; i < pengumumanList.size(); i++) {
            this.data.add(pengumumanList.get(i));
        }
    }

    public void clearData(){
        this.data.clear();
    }

    public Cursor getMetadata() {
        return metadata;
    }

    public void setMetadata(Cursor metadata) {
        this.metadata = metadata;
    }

    private class APIPengumumanGet implements Response.Listener<String>, Response.ErrorListener {

        public void fetchAll() {
            String url = APIClient.BASE_URL + "/announcements";

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

        public void fetchAll(String title, List<String> tags, String cursor) {
            String url = APIClient.BASE_URL + "/announcements?";

            if (title != null) {
                url += "filter[title]=" + title;
            }
            for (int i = 0; i < tags.size(); i++) {
                url += "&filter[tags][]=" + tags.get(i);
            }
            if (!cursor.equals("none")) {
                url += "&cursor=" + cursor;
            }

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
            PengumumanList pengumumanList = gson.fromJson(response, PengumumanList.class);
            presenter.OnSuccessGet(pengumumanList);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PengumumanList: APIPengumumanGet: onErrorResponse(), Error=" + responseBody);
                presenter.OnErrorGet(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PengumumanList: APIPengumumanGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPengumumanDelete implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void delete(String idPengumuman) {
            String url = APIClient.BASE_URL + "/announcements" + "/" + idPengumuman;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.DELETE,
                    url,
                    null,
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
        public void onResponse(JSONObject response) {
            Pengumuman deleted = gson.fromJson(response.toString(), Pengumuman.class);
            presenter.deleteOnSuccess(deleted);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PengumumanList: APIPengumumanGet: onErrorResponse(), Error=" + responseBody);
                presenter.deleteOnError(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PengumumanList: APIPengumumanGet:onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPengumumanAdd implements Response.Listener<JSONObject>, Response.ErrorListener{

        public void add(String title, String content, String[] idTags) {
            String url = APIClient.BASE_URL + "/announcements";

            JsonObject json = new JsonObject();
            JsonArray array = new JsonArray();
            for (int i = 0; i < idTags.length; i++) {
                array.add(idTags[i]);
            }
            json.addProperty("title", title);
            json.addProperty("content", content);
            json.add("tags", array);

            JSONObject JSON = null;
            try {
                JSON = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "PengumumanList: APIPengumumanAdd: addPengumuman() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    JSON,
                    this::onResponse,
                    this::onErrorResponse
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };

            queue.add(request);
        }

        @Override
        public void onResponse(JSONObject response) {
            Pengumuman pengumuman = gson.fromJson(response.toString(), Pengumuman.class);
            presenter.AddOnSuccess(pengumuman);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PengumumanList: APIPengumumanAdd: onErrorResponse(), Error=" + responseBody);

                APIError err = gson.fromJson(responseBody, APIError.class);
                List<String> errField = err.getField();

                String errMessage = errField.get(0) + " " + err.getMessage();

                presenter.AddOnError(errMessage);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PengumumanList: APIPengumumanAdd: onErrorResponse() catch UnsupportedEncodingException");
            }
        }

    }

    private class APIPengumumanDetail implements Response.Listener<String>, Response.ErrorListener {
        private PengumumanList.Pengumuman pengumuman;

        public void getDetail(PengumumanList.Pengumuman pengumuman) {
            this.pengumuman = pengumuman;
            String url = APIClient.BASE_URL + "/announcements/" + pengumuman.getId();

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
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
        public void onResponse(String response) {
            this.pengumuman = gson.fromJson(response, Pengumuman.class);
            presenter.GetDetailOnSuccess(this.pengumuman);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PengumumanList: APIPengumumanDetail: onErrorResponse(), Error=" + responseBody);
                presenter.GetDetailOnError(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PengumumanList: APIPengumumanDetail: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void getPengumumanAll() {
        apiGet.fetchAll();
    }

    public static void getPengumumanAll(String title, List<String> tags, String cursor) {
        apiGet.fetchAll(title, tags, cursor);
    }

    public static void deletePengumuman(String idPengumuman) {
        apiDelete.delete(idPengumuman);
    }

    public static void addPengumuman(String title, String content, String[] idTags){
        apiAdd.add(title, content, idTags);
    }

    public static void getDetailPengumuman(Pengumuman p){
        apiDetail.getDetail(p);
    }
}

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
import com.example.p3b_tubes_2.CustomJsonRequest;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PertemuanList {

    public class Pertemuan {
        private String id;//uuidv4
        private String title;//length:1-256
        private String description;//length:1-1000
        private String start_datetime;//2022-04-12 19:00+0500
        private String end_datetime;//2022-04-12 19:00+0500
        private String organizer_name;
        private String organizer_id;
        private ArrayList<String> partisipan;

        public Pertemuan(String id, String title, String description, String startTime, String endTime, String organizer_name) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.start_datetime = startTime;
            this.end_datetime = endTime;
            this.organizer_name = organizer_name;
            this.partisipan = new ArrayList<>();
        }

        public void setPartisipan(ArrayList<String> partisipan) {
            this.partisipan = partisipan;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getStartTime() {
            SimpleDateFormat inputformatter = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

            String startTime = "";
            try {
                startTime = timeFormatter.format(inputformatter.parse(this.start_datetime));
            } catch (ParseException e) {
                Log.d("DEBUG", "PertemuanList, getStartTime() catch ParseException");
            }

            return startTime;
        }

        public String getEndTime() {
            SimpleDateFormat inputformatter = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH.mm");

            String endTime = "";
            try {
                endTime = timeFormatter.format(inputformatter.parse(this.end_datetime));
            } catch (ParseException e) {
                Log.d("DEBUG", "PertemuanList, getEndTime() catch ParseException");
            }

            return endTime;
        }

        public String getId() {
            return id;
        }

        public String getOrganizerName() {
            return organizer_name;
        }

        public String getOrganizer_id() {
            return organizer_id;
        }

        public String getDate() {
            SimpleDateFormat inputformatter = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy");

            String date = "";
            try {
                date = dateFormatter.format(inputformatter.parse(this.start_datetime));
            } catch (ParseException e) {
                Log.d("DEBUG", "PertemuanList, getDate() catch ParseException");
            }

            return date;
        }

        public String getPartisipan() {
            if(this.partisipan==null) return null;
            String result = "";

            for (int i = 0; i < partisipan.size(); i++) {
                if (i != 0) {
                    result += "\n";
                }
                result += partisipan.get(i);
            }
            return result;
        }
    }

    private ArrayList<Pertemuan> arr;
    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private static APIPertemuanGet apiGet;
    private static APIPertemuanAdd apiAdd;
    private static APIPertemuanDelete apiDelete;
    private static APIPertemuanEdit apiEdit;
    private static APIPertemuanGetParticipants apiGetParticipants;
    private static APIPertemuanAddParticipants apiAddParticipants;
    private static APIPertemuanDeleteParticipants apiDeleteParticipants;

    public PertemuanList(PertemuanPresenter presenter, Context context) {
        this.arr = new ArrayList<>();
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();

        this.apiGet = new APIPertemuanGet();
        this.apiAdd = new APIPertemuanAdd();
        this.apiDelete = new APIPertemuanDelete();
        this.apiEdit = new APIPertemuanEdit();
        this.apiGetParticipants = new APIPertemuanGetParticipants();
        this.apiAddParticipants = new APIPertemuanAddParticipants();
        this.apiDeleteParticipants = new APIPertemuanDeleteParticipants();
    }

    public PertemuanList() {
        this.arr = new ArrayList<>();
    }

    public void addPertemuan(Pertemuan pertemuan) {
        this.arr.add(pertemuan);
    }

    public int getSize() {
        return this.arr.size();
    }

    public Pertemuan getPertemuan(int i) {
        return this.arr.get(i);
    }


    private class APIPertemuanGet implements Response.Listener<String>, Response.ErrorListener{

        public void get(String startDate, String endDate) {
            String url = APIClient.BASE_URL + "/appointments" + "/start-date" + "/" + startDate + "/end-date" + "/" + endDate;

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
            Type listType = new TypeToken<ArrayList<Pertemuan>>() {}.getType();
            arr = gson.fromJson(response, listType);
            presenter.onSuccessGetDibuat(PertemuanList.this);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanGet: onErrorResponse(), Error=" + responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPertemuanAdd implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void add(String title, String description, String startTime, String endTime) {
            String url = APIClient.BASE_URL + "/appointments";

            JsonObject json = new JsonObject();
            json.addProperty("title", title);
            json.addProperty("description", description);
            json.addProperty("start_datetime", startTime);
            json.addProperty("end_datetime", endTime);
            JSONObject JSON = null;
            try {
                JSON = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "PertemuanList: APIAddPertemuan: add() catch JSONException");
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
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };

            queue.add(request);
        }

        @Override
        public void onResponse(JSONObject response) {
            PertemuanList.Pertemuan newPertemuan = gson.fromJson(response.toString(), PertemuanList.Pertemuan.class);
            presenter.onSuccessAdd(newPertemuan);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIAddPertemuan: onErrorResponse(), Error=" + responseBody);

                String msg;
                APIError err = gson.fromJson(responseBody, APIError.class);
                if(err.getErrcode().equals("E_OVERLAPPING_SCHEDULE")){
                    msg = "Anda sudah memiliki pertemuan di jam tersebut";
                }else{
                    msg = responseBody;
                }

                presenter.onErrorAdd(msg);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIAddPertemuan: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPertemuanDelete implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void delete(String idPertemuan) {
            String url = APIClient.BASE_URL + "/appointments" + "/" + idPertemuan;
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
            presenter.onSuccessDelete();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanDelete: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorDelete(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanDelete: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public class APIPertemuanEdit implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void edit(Pertemuan p){
            String url = APIClient.BASE_URL + "/appointments/" + p.getId();

            JSONObject JSON = null;
            try {
                JSON = new JSONObject(gson.toJson(p));
            } catch (JSONException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanEdit: edit() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PATCH,
                    url,
                    JSON,
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
            PertemuanList.Pertemuan newPertemuan = gson.fromJson(response.toString(), PertemuanList.Pertemuan.class);
            presenter.onSuccessChange();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanEdit: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorChange(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanEdit: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPertemuanGetParticipants implements Response.Listener<String>, Response.ErrorListener {

        class Partisipan {
            private String id;
            private String name;
            private boolean attending;
        }

        private PertemuanList.Pertemuan pertemuan;

        public void getParticipants(PertemuanList.Pertemuan pertemuan) {
            this.pertemuan = pertemuan;
            String url = APIClient.BASE_URL + "/appointments/" + pertemuan.getId() + "/participants";

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
            Type listType = new TypeToken<ArrayList<Partisipan>>() {}.getType();
            ArrayList<Partisipan> partisipans = gson.fromJson(response, listType);

            ArrayList<String> attending = new ArrayList<>();
            for (int i = 0; i < partisipans.size(); i++) {
                if (partisipans.get(i).attending) {
                    attending.add(partisipans.get(i).name);
                }
            }

            this.pertemuan.setPartisipan(attending);
            presenter.onSuccessGetPartisipanDibuat(this.pertemuan);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanGetPartisipan: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorGetPartisipanDibuat(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanGetPartisipan: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPertemuanAddParticipants implements Response.Listener<JSONArray>, Response.ErrorListener {
        private User[] queryUsers;

        public void addParticipants(User[] participants, String idPertemuan) {
            this.queryUsers = participants;
            String url = APIClient.BASE_URL + "/appointments" + "/" + idPertemuan + "/participants";

            JsonObject json = new JsonObject();
            JsonArray array = new JsonArray();
            for (int i = 0; i < participants.length; i++) {
                array.add(participants[i].getId());
            }
            json.add("participants", array);
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanAddParticipants: addParticipants() catch JSONException");
            }

            CustomJsonRequest request = new CustomJsonRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
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
        public void onResponse(JSONArray response) {
            presenter.onSuccessAddParticipants(this.queryUsers);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanAddParticipants: onErrorResponse(), Error=" + responseBody);

                String msg;
                APIError err = gson.fromJson(responseBody, APIError.class);
                if(err.getErrcode().equals("E_OVERLAPPING_SCHEDULE")){
                    msg = "Orang tersebut sudah memiliki pertemuan di jam tersebut";
                }else{
                    msg = responseBody;
                }

                presenter.onErrorAddParticipants(msg);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanAddParticipants: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIPertemuanDeleteParticipants implements Response.Listener<JSONArray>, Response.ErrorListener {

        public void deleteParticipants(User[] users, String idPertemuan) {
            String url = APIClient.BASE_URL + "/appointments" + "/" +idPertemuan+ "/participants" + "/delete";

            JsonObject json = new JsonObject();
            JsonArray array = new JsonArray();
            for (int i = 0; i < users.length; i++) {
                array.add(users[i].getId());
            }
            json.add("participants", array);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanDeleteParticipants: deleteParticipants() catch JSONException");
            }

            CustomJsonRequest request = new CustomJsonRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
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
        public void onResponse(JSONArray response) {
            presenter.onSuccessDeleteParticipants();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "PertemuanList: APIPertemuanDeleteParticipants: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorDeleteParticipants(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "PertemuanList: APIPertemuanDeleteParticipants: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void fetch(String startDate, String endDate){
        apiGet.get(startDate, endDate);
    }

    public static void addPertemuan(String title, String description, String startTime, String endTime){
        apiAdd.add(title, description, startTime, endTime);
    }

    public static void deletePertemuan(String id){
        apiDelete.delete(id);
    }

    public static void editPertemuan(Pertemuan p){
        apiEdit.edit(p);
    }

    public static void getParticipants(Pertemuan p){
        apiGetParticipants.getParticipants(p);
    }

    public static void addParticipants(User[] participants, String idPertemuan){
        apiAddParticipants.addParticipants(participants, idPertemuan);
    }

    public static void deleteParticipants(User[] users, String idPertemuan){
        apiDeleteParticipants.deleteParticipants(users, idPertemuan);
    }

}

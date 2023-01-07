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
import com.example.p3b_tubes_2.APIError;
import com.example.p3b_tubes_2.CustomJsonRequest;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.google.gson.Gson;
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

public class InviteList {

    public class Invites {
        String appointment_id;
        String title;
        String description;
        String start_datetime;
        String end_datetime;
        String oraganizer_id;
        String organizer_name;
        boolean attending;
        String created_at;
        String updated_at;

        public String getAppointment_id() {
            return appointment_id;
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
                Log.d("DEBUG", "InviteList, getStartTime() catch ParseException");
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
                Log.d("DEBUG", "InviteList, getStartTime() catch ParseException");
            }

            return endTime;
        }

        public String getDate() {
            SimpleDateFormat inputformatter = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy");

            String date = "";
            try {
                date = dateFormatter.format(inputformatter.parse(this.start_datetime));
            } catch (ParseException e) {
                Log.d("DEBUG", "InviteList, getDate() catch ParseException");
            }

            return date;
        }

        public String getOrganizerName() {
            return organizer_name;
        }

        public boolean isAttending() {
            return attending;
        }
    }

    private PertemuanPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private ArrayList<InviteList.Invites> listInvites;
    private static APIInvitesGet apiGet;
    private static APIInvitesAccept apiAccept;

    public InviteList(PertemuanPresenter presenter, Context context) {
        this.presenter = presenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();

        this.apiGet = new APIInvitesGet();
        this.apiAccept = new APIInvitesAccept();
    }

    public InviteList() {
        this.listInvites = new ArrayList<>();
    }

    public int getListSize() {
        return this.listInvites.size();
    }

    public Invites getInvitation(int i) {
        return this.listInvites.get(i);
    }

    private class APIInvitesGet implements Response.Listener<JSONArray>, Response.ErrorListener{

        public void get() {
            String url = APIClient.BASE_URL + "/appointments/invitations";
            CustomJsonRequest request = new CustomJsonRequest(
                    Request.Method.GET,
                    url,
                    null,
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
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "InviteList: APIInvitesGet: onErrorResponse(), Error=" + responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "InviteList: APIInvitesGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }

        @Override
        public void onResponse(JSONArray response) {
            Type listType = new TypeToken<ArrayList<InviteList.Invites>>() {}.getType();
            ArrayList<InviteList.Invites> list = gson.fromJson(response.toString(), listType);
            listInvites = list;
            presenter.onSuccessGetInvites(InviteList.this);
        }
    }

    private class APIInvitesAccept implements Response.Listener<JSONObject>, Response.ErrorListener{

        public void accept(String appointmentId, String userId) {
            String url = APIClient.BASE_URL + "/appointments" + "/" + appointmentId + "/participants" + "/" + userId;

            JsonObject json = new JsonObject();
            json.addProperty("attending", true);
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "InviteList: APIInvitesAccept: accept() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PATCH,
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
        public void onResponse(JSONObject response) {
            presenter.onSuccessChangeInvites();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "InviteList: APIInvitesAccept: onErrorResponse(), Error=" + responseBody);

                String msg;
                APIError err = gson.fromJson(responseBody, APIError.class);
                if(err.getErrcode().equals("E_OVERLAPPING_SCHEDULE")){
                    msg = "Anda sudah memiliki pertemuan di jam tersebut";
                }else{
                    msg = responseBody;
                }

                presenter.onErrorChangeInvites(msg);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "InviteList: APIInvitesAccept: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void getInvites(){
        apiGet.get();
    }

    public static void acceptInvite(String appointmentId, String userId){
        apiAccept.accept(appointmentId, userId);
    }

}

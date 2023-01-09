package com.example.p3b_tubes_2.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Presenter.UserPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String id;
    private String name;
    private String email;
    private String archived_at;
    private String[] roles;

    private UserPresenter presenter;
    private RequestQueue queue;
    private Gson gson;
    private static APIUsersGet apiGetAll;
    private static APIUserGet apiGet;
    private static APIAuthorization apiAuth;

    public User(UserPresenter loginPresenter, Context context) {
        this.presenter = loginPresenter;
        this.queue = Volley.newRequestQueue(context);
        this.gson = new Gson();

        this.apiGetAll = new APIUsersGet();
        this.apiGet = new APIUserGet();
        this.apiAuth = new APIAuthorization();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoles() {
        String res = "";
        for (int i = 0; i < roles.length; i++) {
            if (i != 0) {
                res += ", ";
            }
            res += roles[i];
        }
        return res;
    }

    public boolean isDosen() {
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals("lecturer"))
                return true;
        }
        return false;
    }

    private class APIUsersGet implements Response.Listener<String>, Response.ErrorListener {

        public void getUsers(String filter) {
            String url = APIClient.BASE_URL + "/users?filter[name]=" + filter;

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    this::onResponse,
                    this::onErrorResponse
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };

            queue.add(request);
        }

        @Override
        public void onResponse(String response) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(response, listType);
            presenter.onSuccessGet(users);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "User: APIUsersGet: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorGet(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "User: APIUsersGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIUserGet implements Response.Listener<String>, Response.ErrorListener {

        public void get() {
            String url = APIClient.BASE_URL + "/users/self";

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    this::onResponse,
                    this::onErrorResponse
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", APIClient.token);
                    return params;
                }
            };

            queue.add(request);
        }

        @Override
        public void onResponse(String response) {
            User user = gson.fromJson(response, User.class);
            APIClient.loggedInId = user.getId();
            presenter.onSuccessGetCurrentUser(user);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "User: APIUserGet: onErrorResponse(), Error=" + responseBody);
                presenter.onErrorGetCurrentUser(responseBody);
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "User: APIUserGet: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    private class APIAuthorization implements Response.Listener<JSONObject>, Response.ErrorListener {

        public void auth(String email, String password, String role) {
            String url = APIClient.BASE_URL + "/authenticate";

            JsonObject json = new JsonObject();
//            json.addProperty("email", email);
//            json.addProperty("password", password);
//            json.addProperty("role", role);
//        json.addProperty("email", "default.admin@domain.local");
//        json.addProperty("password", "mu8XyUogLi6Dk7");
//        json.addProperty("role", "admin");
//        role = "admin";
        json.addProperty("email", "halodearen@mail.com");
        json.addProperty("password", "halodearen");
        json.addProperty("role", "lecturer");
        role = "lecturer";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.d("DEBUG", "User: APIAuthorization: auth() catch JSONException");
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    this::onResponse,
                    this::onErrorResponse
            );

            APIClient.role = role;

            queue.add(request);
        }

        @Override
        public void onResponse(JSONObject response) {
            String token;
            try {
                token = "Bearer " + response.getString("token");
                APIClient.token = token;
                presenter.onSuccessLogin();
            } catch (JSONException e) {
                Log.d("DEBUG", "User: APIAuthorization: onResponse() catch JSONException");
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.d("DEBUG", "User: APIAuthorization: onErrorResponse(), Error=" + responseBody);
                presenter.onFailedLogin();
            } catch (UnsupportedEncodingException e) {
                Log.d("DEBUG", "User: APIAuthorization: onErrorResponse() catch UnsupportedEncodingException");
            }
        }
    }

    public static void getUsers() {
        apiGetAll.getUsers("");
    }

    public static void getFilteredUsers(String filter){
        apiGetAll.getUsers(filter);
    }

    public static void getUser() {
        apiGet.get();
    }

    public static void login(String email, String password, String role) {
        apiAuth.auth(email, password, role);
    }
}

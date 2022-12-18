package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

public class MainPresenter {

    private PertemuanPresenter pertemuanPresenter;
    private PengumumanPresenter pengumumanPresenter;
    private FRSPresenter frsPresenter;
    private APIClient apiClient;

    public MainPresenter(Context context){
        this.pengumumanPresenter = new PengumumanPresenter();
        this.pertemuanPresenter = new PertemuanPresenter();
        this.frsPresenter = new FRSPresenter();
        this.apiClient = new APIClient(this, this.pengumumanPresenter, this.pertemuanPresenter, this.frsPresenter, context);
    }

    public void login(String email, String password, String role) throws JSONException {
        this.apiClient.login(email, password, role);
    }
}

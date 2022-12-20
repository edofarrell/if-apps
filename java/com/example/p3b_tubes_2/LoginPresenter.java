package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import javax.security.auth.login.LoginException;

public class LoginPresenter implements LoginContract{

    private APIClient apiClient;
    private LoginContract.View loginUI;

    public LoginPresenter(LoginContract.View loginUI){
        this.apiClient = new APIClient(this, this.pengumumanPresenter, this.pertemuanPresenter, this.frsPresenter, context);
        this.loginUI = loginUI;
    }

    public void login(String email, String password, String role) throws JSONException {
        this.apiClient.login(email, password, role);
    }

    @Override
    public void onSuccessLogin() {
        loginUI.updateLoginView(true);
    }

    @Override
    public void onFailedLogin() {
        loginUI.updateLoginView(false);
    }
}

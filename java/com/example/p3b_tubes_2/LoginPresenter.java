package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import javax.security.auth.login.LoginException;

public class LoginPresenter implements LoginContract{

    private MainPresenter mainPresenter;
    private User user;
    private LoginContract.View loginUI;

    public LoginPresenter(LoginContract.View loginUI, MainPresenter mainPresenter){
        this.user = new User();
        this.loginUI = loginUI;
        this.mainPresenter = mainPresenter;
    }

    public void login(String email, String password, String role) throws JSONException {
        this.user.login(email, password, role);
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

package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.util.List;

public class UserPresenter implements LoginContract, UserContract.Model.GetOnSucessListener{

    private MainPresenter mainPresenter;
    private User user;
    private APIAuthorization auth;
    private LoginContract.View loginUI;

    public UserPresenter(LoginContract.View loginUI, Context context, MainPresenter mainPresenter){
        this.user = new User(this, context);
        this.auth = new APIAuthorization(this, context);
        this.loginUI = loginUI;
        this.mainPresenter = mainPresenter;
    }


    public void login(String email, String password, String role) throws JSONException {
        this.auth.login(email, password, role);
    }

    @Override
    public void onSuccessLogin() {
        loginUI.updateLoginView(true);
    }

    @Override
    public void onFailedLogin() {
        loginUI.updateLoginView(false);
    }


    public void getUsers(){
        this.user.getUsers();
    }

    @Override
    public void onSuccessGet(List<User> data) {
        this.mainPresenter.onSuccessGet(data);
    }

    @Override
    public void onErrorGet() {

    }


}

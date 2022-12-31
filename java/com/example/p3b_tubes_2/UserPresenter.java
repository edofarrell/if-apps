package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.util.List;

public class UserPresenter implements
        LoginContract,
        UserContract.Model.GetOnSucessListener,
        UserContract.Model.GetOnGetCurrentUserListener
{

    private MainPresenter mainPresenter;
    private User user;
    private APIAuthorization auth;
    private LoginContract.View loginUI;
    private APIGetCurrentUser apiGetCurrentUser;

    public UserPresenter(LoginContract.View loginUI, Context context, MainPresenter mainPresenter){
        this.user = new User(this, context);
        this.auth = new APIAuthorization(this, context);
        this.loginUI = loginUI;
        this.mainPresenter = mainPresenter;
        this.apiGetCurrentUser = new APIGetCurrentUser(this, context);
    }


    public void login(String email, String password, String role) throws JSONException {
        this.auth.login(email, password, role);
    }

    @Override
    public void onSuccessLogin() {
        loginUI.updateLoginView(true);
        this.getCurrentUser();
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


    public void getCurrentUser(){
        this.apiGetCurrentUser.getCurrentUser();
    }

    @Override
    public void onSuccessGetCurrentUser(User user) {
        this.user = user;
    }

    @Override
    public void onErrorGetCurrentUser() {

    }
}

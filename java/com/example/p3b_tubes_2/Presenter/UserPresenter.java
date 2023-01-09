package com.example.p3b_tubes_2.Presenter;

import android.content.Context;

import com.example.p3b_tubes_2.LoginContract;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.UserContract;

import org.json.JSONException;

import java.util.List;

public class UserPresenter implements
        LoginContract,
        UserContract.Model.GetOnSucessListener,
        UserContract.Model.GetOnGetCurrentUserListener
{

    private MainPresenter mainPresenter;
    private User user;
    private LoginContract.View loginUI;
    private UserContract.View.Profile profileUI;

    public UserPresenter(LoginContract.View loginUI, UserContract.View.Profile profile, Context context, MainPresenter mainPresenter){
        this.user = new User(this, context);
        this.loginUI = loginUI;
        this.profileUI = profile;
        this.mainPresenter = mainPresenter;
    }

    //Login
    public void login(String email, String password, String role) throws JSONException {
        User.login(email, password, role);
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

    //Get users
    public void getUsers(){
        User.getUsers();
    }

    public void getUsers(String filter){
        User.getFilteredUsers(filter);
    }

    @Override
    public void onSuccessGet(List<User> data) {
        this.mainPresenter.onSuccessGet(data);
    }

    @Override
    public void onErrorGet(String msg) {

    }

    //Get logged in user
    public void getCurrentUser(){
       User.getUser();
    }

    @Override
    public void onSuccessGetCurrentUser(User user) {
        this.profileUI.updateProfile(user);
    }

    @Override
    public void onErrorGetCurrentUser(String msg) {

    }
}

package com.example.p3b_tubes_2;

public class MainPresenter {

    private UserPresenter userPresenter;

    public MainPresenter(){}

    public void getAllUser(){
        this.userPresenter.getUsers();
    }

}

package com.example.p3b_tubes_2;

import java.util.List;

public class MainPresenter implements UserContract.Model.GetOnSucessListener{

    private UserPresenter userPresenter;
    private PertemuanPresenter pertemuanPresenter;
    private UserContract.View ui;

    public MainPresenter(){}

    public void setUserPresenter(UserPresenter userPresenter) {
        this.userPresenter = userPresenter;
    }

    public void setPertemuanPresenter(PertemuanPresenter pertemuanPresenter) {
        this.pertemuanPresenter = pertemuanPresenter;
    }

    public void getAllUser(UserContract.View ui){
        this.ui = ui;
        this.userPresenter.getUsers();
    }

    @Override
    public void onSuccessGet(List<User> data) {
        this.ui.update(data);
    }

    @Override
    public void onErrorGet() {

    }
}

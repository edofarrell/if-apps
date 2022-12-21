package com.example.p3b_tubes_2;

public class PertemuanPresenter implements PertemuanContract.Model.OnFinishedListener {

    //private model
    private PertemuanContract.View ui;
    private MainPresenter mainPresenter;

    public PertemuanPresenter(PertemuanContract.View ui, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
    }

    public void getPertemuan(){
        //model.getPertemuanList
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onError() {

    }
}



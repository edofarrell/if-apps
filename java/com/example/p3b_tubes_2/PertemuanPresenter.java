package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

public class PertemuanPresenter implements PertemuanContract.Model.OnFinishedListener {

    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private MainPresenter mainPresenter;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
//        Log.d("DEBUG",mainPresenter.getToken());
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pertemuan = new PertemuanList(this,context,mainPresenter.getToken());
    }

    public void getPertemuan(){
        //model.getPertemuanList

    }

    public void addPertemuan(String title, String description, String startTime, String endTime)throws JSONException {
        this.pertemuan.addPertemuan(title,description,startTime,endTime);
    }

    @Override
    public void onFinished(String id) {
        ui.update(id);
    }

    @Override
    public void onError() {

    }
}



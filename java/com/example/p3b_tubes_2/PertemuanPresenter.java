package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

public class PertemuanPresenter implements PertemuanContract.Model.AddOnSuccessListener, PertemuanContract.Model.ChangeOnSuccessListener {

    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private MainPresenter mainPresenter;
    private APITambahPertemuan apiAddPertemuan;
    private APIUbahPertemuan apiUbahPertemuan;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pertemuan = new PertemuanList(this,context);
        this.apiAddPertemuan = new APITambahPertemuan(this, context);
        this.apiUbahPertemuan = new APIUbahPertemuan(this, context);
    }

    public void getPertemuan(){
        //model.getPertemuanList
    }

    public void addPertemuan(String title, String description, String startTime, String endTime) throws JSONException {
        this.apiAddPertemuan.tambahPertemuan(title, description, startTime, endTime);
    }

    @Override
    public void onSuccessAdd(PertemuanList.Pertemuan pertemuan) {
        this.ui.update(pertemuan.getId());
    }

    @Override
    public void onErrorAdd() {

    }

    public void ubahPertemuan(PertemuanList.Pertemuan pertemuan) throws JSONException {
        this.apiUbahPertemuan.ubahPertemuan(pertemuan);
    }

    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onErrorChange() {

    }
}



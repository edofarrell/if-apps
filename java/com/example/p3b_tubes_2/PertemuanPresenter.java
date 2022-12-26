package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PertemuanPresenter implements
        PertemuanContract.Model.AddOnSuccessListener,
        PertemuanContract.Model.ChangeOnSuccessListener,
        PertemuanContract.Model.GetOnSuccessListener,
        PertemuanContract.Model.GetPartisipanSuccessListener
{

    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private MainPresenter mainPresenter;
    private APITambahPertemuan apiAddPertemuan;
    private APIUbahPertemuan apiUbahPertemuan;
    private APIGetPartisipan apiGetPartisipan;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pertemuan = new PertemuanList(this, context);
        this.apiAddPertemuan = new APITambahPertemuan(this, context);
        this.apiUbahPertemuan = new APIUbahPertemuan(this, context);
        this.apiGetPartisipan = new APIGetPartisipan(this, context);
    }

    public void getPertemuan() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);

        this.pertemuan.getPertemuan(formatter.format(new Date()), formatter.format(calendar.getTime()));
    }

    public void getPertemuan(String startDate, String endDate) {
        this.pertemuan.getPertemuan(startDate, endDate);
    }

    @Override
    public void onSuccessGet(ArrayList<PertemuanList.Pertemuan> pertemuanList) {
        this.ui.update(pertemuanList);
    }

    @Override
    public void onErrorGet() {

    }


    public void getPartisipan(PertemuanList.Pertemuan pertemuan){
        this.apiGetPartisipan.getPartisipan(pertemuan);
    }

    @Override
    public void onSuccessGetPartisipan(PertemuanList.Pertemuan pertemuan) {
        this.ui.openDetail(pertemuan);
    }

    @Override
    public void onErrorGetPartisipan() {

    }


    public void addPertemuan(String title, String description, String startTime, String endTime) throws JSONException {
        this.apiAddPertemuan.tambahPertemuan(title, description, startTime, endTime);
    }

    @Override
    public void onSuccessAdd(PertemuanList.Pertemuan pertemuan) {
//        this.ui.update(pertemuan.getId());
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



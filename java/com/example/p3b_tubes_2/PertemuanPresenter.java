package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PertemuanPresenter implements
        PertemuanContract.Model.AddOnSuccessListener,
        PertemuanContract.Model.ChangeOnSuccessListener,
        PertemuanContract.Model.GetOnSuccessListener,
        PertemuanContract.Model.GetPartisipanSuccessListener,
        PertemuanContract.Model.AddParticipantsPertemuanOnSuccessListener,
        PertemuanContract.Model.DeleteParticipantsPertemuanOnSuccessListener,
        PertemuanContract.Model.DeleteOnSuccessListener
{
    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private PertemuanContract.View.PertemuanDibuat uiDibuat;
    private PertemuanContract.View.PertemuanDiundang uiDiundang;
    private MainPresenter mainPresenter;
    private APIPertemuanAdd apiPertemuanAdd;
    private APIPertemuanChange apiPertemuanChange;
    private APIPertemuanGetPartisipan apiPertemuanGetPartisipan;
    private APIPertemuanAddParticipants apiAddParticipantsPertemuan;
    private APIPertemuanDeleteParticipants apiDeleteParticipantsPertemuan;
    private APIPertemuanDelete deletePertemuan;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.pertemuan = new PertemuanList(this, context);
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.apiPertemuanAdd = new APIPertemuanAdd(this, context);
        this.apiPertemuanChange = new APIPertemuanChange(this, context);
        this.apiPertemuanGetPartisipan = new APIPertemuanGetPartisipan(this, context);
        this.apiAddParticipantsPertemuan = new APIPertemuanAddParticipants(this,context);
        this.apiDeleteParticipantsPertemuan = new APIPertemuanDeleteParticipants(this,context);
        this.deletePertemuan = new APIPertemuanDelete(this,context);
    }

    public void setUiDibuat(PertemuanContract.View.PertemuanDibuat ui){
        this.uiDibuat = ui;
    }

    public void setUiDiundang(PertemuanContract.View.PertemuanDiundang ui){
        this.uiDiundang = ui;
    }


    public void getPertemuanDibuat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);

        this.pertemuan.getPertemuan(formatter.format(new Date()), formatter.format(calendar.getTime()));
    }

    public void getPertemuanDibuat(String startDate, String endDate) {
        this.pertemuan.getPertemuan(startDate, endDate);
    }

    @Override
    public void onSuccessGetDibuat(PertemuanList pertemuanList) {
        this.uiDibuat.updatePertemuanDibuat(pertemuanList);
    }

    @Override
    public void onErrorGetDibuat() {

    }


    public void getPartisipanDibuat(PertemuanList.Pertemuan pertemuan){
        this.apiPertemuanGetPartisipan.getPartisipan(pertemuan);
    }

    @Override
    public void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openDetailPertemuanDibuat(pertemuan);
    }

    @Override
    public void onErrorGetPartisipanDibuat() {

    }


    public void addPertemuan(String title, String description, String startTime, String endTime) throws JSONException {
        this.apiPertemuanAdd.tambahPertemuan(title, description, startTime, endTime);
    }

    @Override
    public void onSuccessAdd(PertemuanList.Pertemuan pertemuan) {
        this.pertemuan.addPertemuan(pertemuan);
        this.ui.update(this.pertemuan);
    }

    @Override
    public void onErrorAdd() {

    }


    public void ubahPertemuan(PertemuanList.Pertemuan pertemuan) throws JSONException {
        this.apiPertemuanChange.ubahPertemuan(pertemuan);
    }

    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onErrorChange() {

    }


    public void addParticipantsPertemuan(String[] arr) throws JSONException {
        this.apiAddParticipantsPertemuan.addParticipants(arr);
    }

    @Override
    public void onSuccessAddParticipants(String hasil) {
//        this.ui.update(hasil);
    }

    @Override
    public void onErrorAddParticipants() {

    }


    public void deleteParticipantsPertemuan(String[] arr) throws JSONException {
        this.apiDeleteParticipantsPertemuan.deleteParticipants(arr);
    }

    @Override
    public void onSuccessDeleteParticipants(String hasil) {
//        this.ui.update(hasil);
    }

    @Override
    public void onErrorDeleteParticipants() {

    }


    public void deletePertemuan(String idPertemuan){
        this.deletePertemuan.deletePertemuan(idPertemuan);
    }

    @Override
    public void onSuccessDelete(String hasil) {
//        this.ui.update(hasil);
    }

    @Override
    public void onErrorDelete() {

    }
}



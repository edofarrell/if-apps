package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

public class PertemuanPresenter implements PertemuanContract.Model.AddOnSuccessListener, PertemuanContract.Model.ChangeOnSuccessListener,
        PertemuanContract.Model.AddParticipantsPertemuanOnSuccessListener,
        PertemuanContract.Model.DeleteParticipantsPertemuanOnSuccessListener ,PertemuanContract.Model.DeleteOnSuccessListener{

    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private MainPresenter mainPresenter;
    private APITambahPertemuan apiAddPertemuan;
    private APIUbahPertemuan apiUbahPertemuan;
    private APIAddParticipantsPertemuan addParticipantsPertemuan;
    private APIDeleteParticipantsPertemuan deleteParticipantsPertemuan;
    private APIDeletePertemuan deletePertemuan;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pertemuan = new PertemuanList(this,context);
        this.apiAddPertemuan = new APITambahPertemuan(this, context);
        this.apiUbahPertemuan = new APIUbahPertemuan(this, context);
        this.addParticipantsPertemuan = new APIAddParticipantsPertemuan(this,context);
        this.deleteParticipantsPertemuan = new APIDeleteParticipantsPertemuan(this,context);
        this.deletePertemuan = new APIDeletePertemuan(this,context);
    }


    public void getPertemuan(String id, String startDate, String endDate){
        this.pertemuan.getPertemuan(id,startDate,endDate);
    }

    public void addPertemuan(String title, String description, String startTime, String endTime) throws JSONException {
        this.apiAddPertemuan.tambahPertemuan(title, description, startTime, endTime);
    }

    public void deletePertemuan(String idPertemuan){
        this.deletePertemuan.deletePertemuan(idPertemuan);
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

    public void addParticipantsPertemuan(String[] arr) throws JSONException {
        this.addParticipantsPertemuan.addParticipants(arr);
    }

    public void deleteParticipantsPertemuan(String[] arr) throws JSONException {
        this.deleteParticipantsPertemuan.deleteParticipants(arr);
    }

    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onErrorChange() {

    }

    @Override
    public void onSuccessAddParticipants(String hasil) {
        this.ui.update(hasil);
    }

    @Override
    public void onErrorAddParticipants() {

    }

    @Override
    public void onSuccessDeleteParticipants(String hasil) {
        this.ui.update(hasil);
    }

    @Override
    public void onErrorDeleteParticipants() {

    }

    @Override
    public void onSuccessDelete(String hasil) {
        this.ui.update(hasil);
    }

    @Override
    public void onErrorDelete() {

    }
}



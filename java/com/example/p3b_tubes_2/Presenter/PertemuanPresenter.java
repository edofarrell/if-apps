package com.example.p3b_tubes_2.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.PertemuanContract;

import org.json.JSONException;

import java.text.ParseException;
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
        PertemuanContract.Model.DeleteOnSuccessListener,
        PertemuanContract.Model.GetTimeSlotOnSuccessListener,
        PertemuanContract.Model.AddTimeSlotOnSuccessListener,
        PertemuanContract.Model.GetInvitesOnSuccessListener,
        PertemuanContract.Model.ChangeInvitesOnSuccessListener{
    private PertemuanList pertemuan;
    private PertemuanContract.View ui;
    private PertemuanContract.View.PertemuanDibuat uiDibuat;
    private PertemuanContract.View.PertemuanDiundang uiDiundang;
    private InviteList inviteList;
    private TimeslotList timeslotList;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.pertemuan = new PertemuanList(this, context);
        this.inviteList = new InviteList(this, context);
        this.timeslotList = new TimeslotList(this, context);
        this.ui = ui;
    }

    public void setUiDibuat(PertemuanContract.View.PertemuanDibuat ui) {
        this.uiDibuat = ui;
    }

    public void setUiDiundang(PertemuanContract.View.PertemuanDiundang ui) {
        this.uiDiundang = ui;
    }

    //Get pertemuan
    public void getPertemuanDibuat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);

       PertemuanList.fetch(formatter.format(new Date()), formatter.format(calendar.getTime()));
//        this.pertemuan.getPertemuan(formatter.format(calendar.getTime()), formatter.format(new Date()));
    }

    public void getPertemuanDibuat(String startDate, String endDate) {
        PertemuanList.fetch(startDate, endDate);
    }

    @Override
    public void onSuccessGetDibuat(PertemuanList pertemuanList) {
        this.uiDibuat.updatePertemuanDibuat(pertemuanList);
    }

    @Override
    public void onErrorGetDibuat(String msg) {

    }

    //Add pertemuan
    public void addPertemuan(String title, String description, String startTime, String endTime) {
        PertemuanList.addPertemuan(title, description, startTime, endTime);
    }

    @Override
    public void onSuccessAdd(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openAddPartisipan(pertemuan.getId());
        this.getPertemuanDibuat();
    }

    @Override
    public void onErrorAdd(String msg) {
        this.uiDibuat.showErrorAddPertemuan(msg);
    }

    //Change pertemuan
    public void ubahPertemuan(PertemuanList.Pertemuan pertemuan) throws JSONException {
        PertemuanList.editPertemuan(pertemuan);
    }

    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onErrorChange(String msg) {

    }

    //Delete pertemuan
    public void deletePertemuan(String idPertemuan) {
        PertemuanList.deletePertemuan(idPertemuan);
    }

    @Override
    public void onSuccessDelete() {
        this.getPertemuanDibuat();
    }

    @Override
    public void onErrorDelete(String msg) {

    }


    //Get partisipan
    public void getPartisipanDibuat(PertemuanList.Pertemuan pertemuan) {
        PertemuanList.getParticipants(pertemuan);
    }

    @Override
    public void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openDetailPertemuanDibuat(pertemuan);
    }

    @Override
    public void onErrorGetPartisipanDibuat(String msg) {

    }


    //Add participants
    public void addUserToPertemuan(User[] users, String idPertemuan) {
        PertemuanList.addParticipants(users, idPertemuan);
    }

    @Override
    public void onSuccessAddParticipants(User[] users) {
        this.uiDibuat.addSelectedUserOnTambahPertemuan(users);
    }

    @Override
    public void onErrorAddParticipants(String msg) {

    }

    //Delete participants
    public void deleteParticipantsPertemuan(User[] users, String idPertemuan) {
        PertemuanList.deleteParticipants(users, idPertemuan);
    }

    @Override
    public void onSuccessDeleteParticipants() {

    }

    @Override
    public void onErrorDeleteParticipants(String msg) {

    }


    public void getTimeSlot(String lecturerId) {
        TimeslotList.fetch(lecturerId);
    }

    @Override
    public void onSuccessGetTimeSlot(TimeslotList timeslotList) {
        this.ui.updateTimeSlot(timeslotList);
    }

    @Override
    public void onErrorGetTimeSlot(String msg) {

    }


    public void addTimeSlot(String day, String startTime, String endTime) {
        String dayFormatted;
        switch (day){
            case "Senin": dayFormatted = "mon"; break;
            case "Selasa": dayFormatted = "tue"; break;
            case "Rabu": dayFormatted = "wed"; break;
            case "Kamis": dayFormatted= "thu"; break;
            case "Jumat": dayFormatted = "fri"; break;
            case "Sabtu": dayFormatted = "sat"; break;
            default: dayFormatted = "sun"; break;
        }

        SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mmZ");

        String startTimeFormatted = "";
        String endTimeFormatted = "";
        try {
            startTimeFormatted = timeFormatter.format(inputFormatter.parse(startTime));
            endTimeFormatted = timeFormatter.format(inputFormatter.parse(endTime));
        } catch (ParseException e) {
            Log.d("DEBUG", "PertemuanPresenter, addTimeSlot() catch ParseException");
        }

        TimeslotList.addTimeslot(dayFormatted, startTimeFormatted, endTimeFormatted);
    }

    @Override
    public void onSuccessAddTimeSlot() {
        this.uiDibuat.closeAddPage();
    }

    @Override
    public void onErrorAddTimeSlot(String msg) {
        this.uiDibuat.showErrorAddTimeslot(msg);
    }


    public void openDetail(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openDetailPertemuanDibuat(pertemuan);
    }

    
    public void getInvites() {
        this.inviteList.getInvites();
    }

    @Override
    public void onSuccessGetInvites(InviteList invites) {
        this.uiDiundang.updatePertemuanDiundang(invites);
    }

    @Override
    public void onErrorGetInvites(String msg) {

    }

    public void openDetailUndangan(InviteList.Invites invite){
        this.uiDiundang.openDetailPertemuanDiundang(invite);
    }


    public void acceptInvitation(String appointmentId){
        InviteList.acceptInvite(appointmentId, APIClient.loggedInId);
    }

    @Override
    public void onSuccessChangeInvites() {
        this.uiDiundang.closeDetail();
    }

    @Override
    public void onErrorChangeInvites(String msg) {
        this.uiDiundang.showErrorAcceptInvite(msg);
    }
}



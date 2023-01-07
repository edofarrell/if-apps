package com.example.p3b_tubes_2.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.APIPertemuanAcceptInvitation;
import com.example.p3b_tubes_2.APIPertemuanAdd;
import com.example.p3b_tubes_2.APIPertemuanAddParticipants;
import com.example.p3b_tubes_2.APIPertemuanChange;
import com.example.p3b_tubes_2.APIPertemuanDelete;
import com.example.p3b_tubes_2.APIPertemuanDeleteParticipants;
import com.example.p3b_tubes_2.APIPertemuanGetPartisipan;
import com.example.p3b_tubes_2.APIPertemuanGetTimeSlot;
import com.example.p3b_tubes_2.APIPertemuanTambahTimeSlot;
import com.example.p3b_tubes_2.MainPresenter;
import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeSlot;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.PertemuanContract;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private MainPresenter mainPresenter;
    private APIPertemuanAdd apiPertemuanAdd;
    private APIPertemuanChange apiPertemuanChange;
    private APIPertemuanGetPartisipan apiPertemuanGetPartisipan;
    private APIPertemuanAddParticipants apiAddParticipantsPertemuan;
    private APIPertemuanDeleteParticipants apiDeleteParticipantsPertemuan;
    private APIPertemuanDelete deletePertemuan;
    private APIPertemuanGetTimeSlot getTimeSlot;
    private APIPertemuanTambahTimeSlot tambahTimeSlot;
    private APIPertemuanAcceptInvitation acceptInvitation;
    private InviteList getInvites;

    public PertemuanPresenter(PertemuanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.pertemuan = new PertemuanList(this, context);
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.apiPertemuanAdd = new APIPertemuanAdd(this, context);
        this.apiPertemuanChange = new APIPertemuanChange(this, context);
        this.apiPertemuanGetPartisipan = new APIPertemuanGetPartisipan(this, context);
        this.apiAddParticipantsPertemuan = new APIPertemuanAddParticipants(this, context);
        this.apiDeleteParticipantsPertemuan = new APIPertemuanDeleteParticipants(this, context);
        this.deletePertemuan = new APIPertemuanDelete(this, context);
        this.getTimeSlot = new APIPertemuanGetTimeSlot(this, context);
        this.tambahTimeSlot = new APIPertemuanTambahTimeSlot(this, context);
        this.getInvites = new InviteList(this, context);
        this.acceptInvitation = new APIPertemuanAcceptInvitation(this, context);
    }

    public void setUiDibuat(PertemuanContract.View.PertemuanDibuat ui) {
        this.uiDibuat = ui;
    }

    public void setUiDiundang(PertemuanContract.View.PertemuanDiundang ui) {
        this.uiDiundang = ui;
    }


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
    public void onErrorGetDibuat() {

    }


    public void getPartisipanDibuat(PertemuanList.Pertemuan pertemuan) {
        this.apiPertemuanGetPartisipan.getPartisipan(pertemuan);
    }

    @Override
    public void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openDetailPertemuanDibuat(pertemuan);
    }

    @Override
    public void onErrorGetPartisipanDibuat() {

    }


    public void addPertemuan(String title, String description, String startTime, String endTime) {
        this.apiPertemuanAdd.tambahPertemuan(title, description, startTime, endTime);
    }

    @Override
    public void onSuccessAdd(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openAddPartisipan(pertemuan.getId());
        this.getPertemuanDibuat();
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


    public void deleteParticipantsPertemuan(User[] users, String idPertemuan) {
        this.apiDeleteParticipantsPertemuan.deleteParticipants(users, idPertemuan);
    }

    @Override
    public void onSuccessDeleteParticipants() {
//
    }

    @Override
    public void onErrorDeleteParticipants() {

    }


    public void deletePertemuan(String idPertemuan) {
        this.deletePertemuan.deletePertemuan(idPertemuan);
    }

    @Override
    public void onSuccessDelete() {
        this.getPertemuanDibuat();
    }

    @Override
    public void onErrorDelete() {

    }


    public void addUserToPertemuan(User[] users, String idPertemuan) {
        this.apiAddParticipantsPertemuan.addParticipants(users, idPertemuan);
    }

    @Override
    public void onSuccessAddParticipants(User[] users) {
        this.uiDibuat.addSelectedUserOnTambahPertemuan(users);
    }

    @Override
    public void onErrorAddParticipants() {

    }


    public void getTimeSlot(String lecturerId) {
        this.getTimeSlot.getTimeSlot(lecturerId);
    }

    @Override
    public void onSuccessGetTimeSlot(List<TimeSlot> timeSlot) {
        this.ui.updateTimeSlot(timeSlot);
    }

    @Override
    public void onErrorGetTimeSlot() {

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

        this.tambahTimeSlot.addTimeSlot(dayFormatted, startTimeFormatted, endTimeFormatted);
    }

    @Override
    public void onSuccessAddTimeSlot() {
        this.uiDibuat.closeAddPage();
    }

    @Override
    public void onErrorAddTimeSlot() {

    }


    public void openDetail(PertemuanList.Pertemuan pertemuan) {
        this.uiDibuat.openDetailPertemuanDibuat(pertemuan);
    }

    
    public void getInvites() {
        this.getInvites.getInvites();
    }

    @Override
    public void onSuccessGetInvites(InviteList invites) {
        this.uiDiundang.updatePertemuanDiundang(invites);
    }

    @Override
    public void onErrorGetInvites() {

    }

    public void openDetailUndangan(InviteList.Invites invite){
        this.uiDiundang.openDetailPertemuanDiundang(invite);
    }


    public void acceptInvitation(String appointmentId){
        this.acceptInvitation.acceptInvitation(appointmentId, APIClient.loggedInId);
    }

    @Override
    public void onSuccessChangeInvites() {
        this.uiDiundang.closeDetail();
    }

    @Override
    public void onErrorChangeInvites() {

    }
}



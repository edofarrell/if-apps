package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;

public class FRSPresenter implements FRSContract.Model.GetOnSuccessListener,
FRSContract.Model.GetDetailOnSuccessListener,
FRSContract.Model.GetSearchMataKuliahOnSuccessListener{

    private FRSContract.View ui;
    private MainPresenter mainPresenter;
    private MataKuliahList mataKuliahList;
    private TahunAjaran tahunAjaran;
    private APIFRSEnrolmentStudent enrolmentStudent;
    //TahunAjaran tahunAjaranList;

    public FRSPresenter(FRSContract.View ui, Context context, MainPresenter mainPresenter){
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.tahunAjaran = new TahunAjaran(this,context);
        this.mataKuliahList = new MataKuliahList(this,context);
        this.enrolmentStudent = new APIFRSEnrolmentStudent(this,context);
    }

    public void getMataKuliah(String text){
        this.mataKuliahList.getMataKuliah(text);
    }

    public void openFragment(TahunAjaran.TahunAjar tahunAjar){
        this.ui.openDetail(tahunAjar);
    }

    public void addToSelectedMataKuliah(MataKuliahList.MataKuliah matkul){
        this.ui.addToSelectedMataKuliah(matkul);
    }

    public void enrolStudent(String id, String academicYear) throws JSONException {
        this.enrolmentStudent.enrolStudent(id,academicYear);
    }

    public void getAcademicYears(){
        this.tahunAjaran.getAcademicYears();
    }

    @Override
    public void OnSuccessGet(TahunAjaran tahunAjaran) {
        this.ui.update(tahunAjaran);
    }

    @Override
    public void OnErrorGet() {

    }

    @Override
    public void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah) {
        //this.ui.openDetail(listMataKuliah);
    }

    @Override
    public void OnErrorGetDetail() {

    }

    @Override
    public void OnSuccessGetSearchMataKuliah(ArrayList<MataKuliahList.MataKuliah> listMataKuliah) {
        this.ui.updateSearch(listMataKuliah);
    }

    @Override
    public void OnErrorGetSearchMataKuliah() {

    }
}

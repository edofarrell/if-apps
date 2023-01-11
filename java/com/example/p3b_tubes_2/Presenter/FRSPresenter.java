package com.example.p3b_tubes_2.Presenter;

import android.content.Context;

import com.example.p3b_tubes_2.FRSContract;
import com.example.p3b_tubes_2.Model.FRSList;
import com.example.p3b_tubes_2.Model.MataKuliahList;
import com.example.p3b_tubes_2.Model.TahunAjaran;

import org.json.JSONException;

import java.util.ArrayList;

public class FRSPresenter implements
        FRSContract.Model.GetOnSuccessListener,
        FRSContract.Model.GetDetailOnSuccessListener,
        FRSContract.Model.GetSearchMataKuliahOnSuccessListener,
        FRSContract.Model.GetMataKuliahEnrolmentOnSuccessListener,
        FRSContract.Model.EnrolStudentOnSuccessListener {

    private FRSContract.View ui;
    private MataKuliahList mataKuliahList;
    private TahunAjaran tahunAjaran;
    private FRSList frs;

    public FRSPresenter(FRSContract.View ui, Context context) {
        this.ui = ui;
        this.tahunAjaran = new TahunAjaran(this, context);
        this.mataKuliahList = new MataKuliahList(this, context);
        this.frs = new FRSList(this, context);
    }

    //Get Mata Kuliah Search
    public void getMataKuliah(String text) {
        MataKuliahList.getCourse(text);
    }

    @Override
    public void OnSuccessGetSearchMataKuliah(ArrayList<MataKuliahList.MataKuliah> listMataKuliah) {
        this.ui.updateSearch(listMataKuliah);
    }

    @Override
    public void OnErrorGetSearchMataKuliah() {

    }

    //Enroll Student
    public void enrolStudent(String id, String academicYear) throws JSONException {
        FRSList.enrollStudent(id, academicYear);
    }

    @Override
    public void OnSuccessEnrolStudent() {
        this.ui.showSuccessMataKuliahEnrol();
    }

    @Override
    public void OnErrorEnrolStudent(String nama, String kode) {
        this.ui.showErrorMataKuliahEnrol(nama, kode);
    }


    //Get Mata Kuliah yang sudah di enroll oleh student
    public void getMataKuliahEnrolment(String academicYear, boolean thisYear) throws JSONException {
        FRSList.getEnrollmentStudent(academicYear, thisYear);
    }

    @Override
    public void OnSuccessGetMataKuliahEnrolment(ArrayList<MataKuliahList.MataKuliah> listNamaMatkul, boolean thisYear) {
        this.ui.updateMataKuliahEnrolment(listNamaMatkul, thisYear);
    }

    @Override
    public void OnErrorGetMataKuliahEnrolment() {

    }


    //Get Tahun Ajaran
    public void getAcademicYears() {
        TahunAjaran.getTahunAjar();
    }

    @Override
    public void OnSuccessGet(TahunAjaran tahunAjaran) {
        this.ui.update(tahunAjaran);
    }

    @Override
    public void OnErrorGet() {

    }


    public void openFragment(TahunAjaran.TahunAjar tahunAjar) {
        this.ui.openDetail(tahunAjar);
    }

    public void addToSelectedMataKuliah(MataKuliahList.MataKuliah matkul) {
        this.ui.addToSelectedMataKuliah(matkul);
    }


    //Get detail mata kuliah (belum diimplementasi)
    @Override
    public void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah) {

    }

    @Override
    public void OnErrorGetDetail() {

    }
}

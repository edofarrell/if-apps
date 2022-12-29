package com.example.p3b_tubes_2;

import android.content.Context;

import java.util.ArrayList;

public class FRSPresenter implements FRSContract.Model.GetOnSuccessListener,
FRSContract.Model.GetDetailOnSuccessListener{

    private FRSContract.View ui;
    private MainPresenter mainPresenter;
    private MataKuliahList mataKuliahList;
    private TahunAjaran tahunAjaran;
    //TahunAjaran tahunAjaranList;

    public FRSPresenter(FRSContract.View ui, Context context, MainPresenter mainPresenter){
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.tahunAjaran = new TahunAjaran(this,context);
        this.mataKuliahList = new MataKuliahList(this,context);
    }

    public void getMataKuliah(int semester, String tahunAjar){
        this.mataKuliahList.getMataKuliah(semester,tahunAjar);
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
    public void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah, String tahunAjar) {
        this.ui.openDetail(tahunAjar,listMataKuliah);
    }

    @Override
    public void OnErrorGetDetail() {

    }
}

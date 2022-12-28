package com.example.p3b_tubes_2;

import android.content.Context;

import java.util.ArrayList;

public class FRSPresenter implements FRSContract.Model.GetOnSuccessListener{

    private FRSContract.View ui;
    private MainPresenter mainPresenter;
    private ArrayList<MataKuliah> mataKuliahList;
    private TahunAjaran tahunAjaran;
    //TahunAjaran tahunAjaranList;

    public FRSPresenter(FRSContract.View ui, Context context, MainPresenter mainPresenter){
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.tahunAjaran = new TahunAjaran(this,context);
    }

    public void getAcademicYears(){
        this.tahunAjaran.getAcademicYears();
    }

    @Override
    public void OnSuccessGet(ArrayList<TahunAjaran.TahunAjar> tahunAjaran) {
        this.ui.update(tahunAjaran);
    }

    @Override
    public void OnErrorGet() {

    }
}

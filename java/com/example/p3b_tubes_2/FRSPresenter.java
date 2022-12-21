package com.example.p3b_tubes_2;

import java.util.ArrayList;

public class FRSPresenter {

    private FRSContract.View ui;
    private MainPresenter mainPresenter;
    private ArrayList<MataKuliah> mataKuliahList;
    //TahunAjaran tahunAjaranList;

    public FRSPresenter(FRSContract.View ui, MainPresenter mainPresenter){
        this.ui = ui;
        this.mainPresenter = mainPresenter;
    }
}

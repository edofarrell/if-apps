package com.example.p3b_tubes_2;

import android.content.Context;

public class PengumumanPresenter implements PengumumanContract.Model.GetOnSuccessListener{

    private PengumumanContract.View ui;
    private MainPresenter mainPresenter;
    private PengumumanList pengumuman;

    public PengumumanPresenter(PengumumanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pengumuman = new PengumumanList(this, context);
    }

    public void getPengumuman(){
        this.pengumuman.getPengumuman();
    }

    @Override
    public void OnSuccessGet(PengumumanList pengumumanList) {
        this.ui.update(pengumumanList);
    }

    @Override
    public void OnErrorGet() {

    }
}

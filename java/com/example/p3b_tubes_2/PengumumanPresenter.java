package com.example.p3b_tubes_2;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;

public class PengumumanPresenter implements PengumumanContract.Model.GetOnSuccessListener,
PengumumanContract.Model.GetTagOnSuccessListener{

    private PengumumanContract.View ui;
    private MainPresenter mainPresenter;
    private PengumumanList pengumuman;
    private TagList tag;
    private APITambahPengumuman tambahPengumuman;
    private APIDeletePengumuman deletePengumuman;

    public PengumumanPresenter(PengumumanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pengumuman = new PengumumanList(this, context);
        this.tag = new TagList(this,context);
        this.tambahPengumuman = new APITambahPengumuman(this,context);
        this.deletePengumuman = new APIDeletePengumuman(this,context);
    }

    public void getTag(){this.tag.getTag();}

    public void getPengumuman(){
        this.pengumuman.getPengumuman();
    }

    public void deletePengumuman(String id){
        deletePengumuman.deletePengumuman(id);
    }

    public void addPengumuman() throws JSONException {
        String[] arr = {"d78227d2-053e-4d57-8ef7-ba1560f412de"};
        this.tambahPengumuman.addPengumuman("Hello Dearen","Pengumumanya Dearen",arr);
    }

    @Override
    public void OnSuccessGet(PengumumanList pengumumanList) {
        this.ui.update(pengumumanList);
    }

    @Override
    public void OnErrorGet() {

    }

    @Override
    public void GetTagOnSuccess(ArrayList<TagList.Tag> listTag) {
        this.ui.updateListTag(listTag);
    }

    @Override
    public void GetTagOnError() {

    }
}

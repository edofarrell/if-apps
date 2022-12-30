package com.example.p3b_tubes_2;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PengumumanPresenter implements
        PengumumanContract.Model.GetOnSuccessListener,
        PengumumanContract.Model.GetTagOnSuccessListener,
        PengumumanContract.Model.GetDetailOnSuccessListener,
        PengumumanContract.Model.AddOnSuccessListener,
        PengumumanContract.Model.DeleteOnSuccessListener {

    private PengumumanContract.View ui;
    private MainPresenter mainPresenter;
    private PengumumanList pengumuman;
    private TagList tag;
    private APIPengumumanAdd tambahPengumuman;
    private APIPengumumanDelete deletePengumuman;
    private APIPengumumanDetail detailPengumuman;

    public PengumumanPresenter(PengumumanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
        this.pengumuman = new PengumumanList(this, context);
        this.tag = new TagList(this, context);
        this.tambahPengumuman = new APIPengumumanAdd(this, context);
        this.deletePengumuman = new APIPengumumanDelete(this, context);
        this.detailPengumuman = new APIPengumumanDetail(this, context);
    }


    public void addPengumuman(String title, String content, String[] tags) {
        this.tambahPengumuman.addPengumuman(title, content, tags);
    }

    @Override
    public void AddOnSuccess(PengumumanList.Pengumuman pengumuman) {
        this.ui.closeAddPage();
        this.getPengumuman();
    }

    @Override
    public void AddOnError(String msg) {
        this.ui.showErrorAddPengumuman(msg);
    }


    public void deletePengumuman(String id) {
        deletePengumuman.deletePengumuman(id);
    }

    @Override
    public void deleteOnSuccess() {
        this.getPengumuman();
    }

    @Override
    public void deleteOnError() {

    }


    public void getPengumuman() {
        this.pengumuman.getPengumumanAll();
    }

    public void getPengumuman(String title, List<String> tags, boolean next) {
        if (!next) {
            this.pengumuman.getPengumumanAll(title, tags, "none");
        } else {
            if (!this.pengumuman.getCursor().equals("none"))
                this.pengumuman.getPengumumanAll(title, tags, this.pengumuman.getCursor());
        }
    }

    @Override
    public void OnSuccessGet(PengumumanList pengumumanList) {
        this.pengumuman.setData(pengumumanList.getData());
        this.pengumuman.setMetadata(pengumumanList.getMetadata());
        this.ui.updatePengumumanList(this.pengumuman);
    }

    @Override
    public void OnErrorGet() {

    }


    public void getTag() {
        this.tag.getTag();
    }

    @Override
    public void GetTagOnSuccess(ArrayList<TagList.Tag> listTag) {
        this.ui.updateListTag(listTag);
    }

    @Override
    public void GetTagOnError() {

    }


    public void getPengumumanDetail(PengumumanList.Pengumuman pengumuman) {
        this.detailPengumuman.getDetail(pengumuman);
    }

    @Override
    public void GetDetailOnSuccess(PengumumanList.Pengumuman pengumuman) {
        this.ui.openDetail(pengumuman);
    }

    @Override
    public void GetDetailOnError() {

    }


    public List<String> getTagsId(List<String> tagName) {
        return this.tag.getActiveTagFilter(tagName);
    }

}

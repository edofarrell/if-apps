package com.example.p3b_tubes_2.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.p3b_tubes_2.MainPresenter;
import com.example.p3b_tubes_2.Model.PengumumanList;
import com.example.p3b_tubes_2.Model.TagList;
import com.example.p3b_tubes_2.PengumumanContract;

import java.util.ArrayList;
import java.util.List;

public class PengumumanPresenter implements
        PengumumanContract.Model.GetOnSuccessListener,
        PengumumanContract.Model.GetTagOnSuccessListener,
        PengumumanContract.Model.GetDetailOnSuccessListener,
        PengumumanContract.Model.AddOnSuccessListener,
        PengumumanContract.Model.DeleteOnSuccessListener,
        PengumumanContract.Model.AddTagOnSuccessListener
{

    private PengumumanContract.View ui;
    private PengumumanList pengumuman;
    private TagList tag;

    public PengumumanPresenter(PengumumanContract.View ui, Context context, MainPresenter mainPresenter) {
        this.ui = ui;
        this.pengumuman = new PengumumanList(this, context);
        this.tag = new TagList(this, context);
    }

    //Get pengumuman
    public void getPengumuman() {
        PengumumanList.getPengumumanAll();
    }

    public void getPengumuman(String title, List<String> tags, boolean next) {
        if (!next) {
            PengumumanList.getPengumumanAll(title, tags, "none");
        } else {
            if (!this.pengumuman.getCursor().equals("none"))
                PengumumanList.getPengumumanAll(title, tags, this.pengumuman.getCursor());
        }
    }

    @Override
    public void OnSuccessGet(PengumumanList pengumumanList) {
        this.pengumuman.addData(pengumumanList.getData());
        this.pengumuman.setMetadata(pengumumanList.getMetadata());
        this.ui.updatePengumumanList(this.pengumuman);
    }

    @Override
    public void OnErrorGet(String msg) {

    }

    //Add pengumuman
    public void addPengumuman(String title, String content, String[] tags) {
//        PengumumanList.addPengumuman(title, content, tags);
    }

    @Override
    public void AddOnSuccess(PengumumanList.Pengumuman pengumuman) {
        this.ui.closeAddPage();
        this.pengumuman.add(pengumuman);
        this.ui.updatePengumumanList(this.pengumuman);
    }

    @Override
    public void AddOnError(String msg) {
        this.ui.showErrorAddPengumuman(msg);
    }

    //Delete pengumuman
    public void deletePengumuman(String id) {
        PengumumanList.deletePengumuman(id);
    }

    @Override
    public void deleteOnSuccess(PengumumanList.Pengumuman p) {
        this.pengumuman.delete(p);
        this.ui.updatePengumumanList(this.pengumuman);
    }

    @Override
    public void deleteOnError(String msg) {

    }

    //Get detail pengumuman
    public void getPengumumanDetail(PengumumanList.Pengumuman pengumuman) {
        PengumumanList.getDetailPengumuman(pengumuman);
    }

    @Override
    public void GetDetailOnSuccess(PengumumanList.Pengumuman pengumuman) {
        this.ui.openDetail(pengumuman);
    }

    @Override
    public void GetDetailOnError(String msg) {

    }

    //Get Tag
    public void getTag() {
        TagList.fetch();
    }

    @Override
    public void GetTagOnSuccess(ArrayList<TagList.Tag> listTag) {
        this.ui.updateListTag(listTag);
    }

    @Override
    public void GetTagOnError(String msg) {

    }

    //Add tag
    public void addTag(String tag){
        TagList.addTag(tag);
    }

    @Override
    public void AddTagOnSuccess() {

    }

    @Override
    public void AddTagOnError(String msg) {

    }


    public List<String> getTagsId(List<String> tagName) {
        return this.tag.getActiveTagFilter(tagName);
    }
}

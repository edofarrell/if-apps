package com.example.p3b_tubes_2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListFrsBinding;

import java.sql.Array;
import java.util.ArrayList;

public class FRSListAdapter extends BaseAdapter {
    private FRSPresenter presenter;
    private TahunAjaran tahunAjaran;
    private class ViewHolder{
        protected int i;
        private TextView academicYears;

        public ViewHolder(ItemListFrsBinding binding,int i){
            this.i = i;
            this.academicYears = binding.tvAcademicYear;
            this.academicYears.setOnClickListener(this::openDetail);
        }

        private void updateView(int i) {
            this.academicYears.setText(tahunAjaran.getListAcademicYears().get(i).toString());
        }

        private void openDetail(View view){
            Log.d("DEBUG",getSemesterNow()+"");
            presenter.getMataKuliah(getSemesterNow(),academicYears.getText().toString());
        }

        private int getSemesterNow(){
            String semesterSkrg = tahunAjaran.getListAcademicYears().get(i).getSemester();
            int tahunSkrg = Integer.parseInt(tahunAjaran.getListAcademicYears().get(i).getTahun());
            int tahunMasuk = Integer.parseInt(tahunAjaran.getListAcademicYears().get(0).getTahun());
            if(semesterSkrg.equals("Genap")){
                if(tahunSkrg-tahunMasuk==0){
                    return 2;
                }
                else if(tahunSkrg-tahunMasuk==1){
                    return 4;
                }
                else if(tahunSkrg-tahunMasuk==2){
                    return 6;
                }
                else{
                    return 8;
                }
            }
            else{
                if(tahunSkrg-tahunMasuk==0){
                    return 1;
                }
                else if(tahunSkrg-tahunMasuk==1){
                    return 3;
                }
                else if(tahunSkrg-tahunMasuk==2){
                    return 5;
                }
                else{
                    return 7;
                }
            }
        }
    }

    public FRSListAdapter(FRSPresenter presenter){
        this.presenter = presenter;
        this.tahunAjaran = new TahunAjaran();
    }

    @Override
    public int getCount() {
        return tahunAjaran.getListAcademicYears().size();
    }

    @Override
    public Object getItem(int i) {
        return tahunAjaran.getListAcademicYears().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemListFrsBinding binding = ItemListFrsBinding.inflate(inflater);
        ViewHolder viewHolder;
        if (view == null) {
            view = binding.getRoot();
            viewHolder = new FRSListAdapter.ViewHolder(binding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FRSListAdapter.ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }

    public void update(TahunAjaran tahunAjaran){
        this.tahunAjaran = tahunAjaran;
        notifyDataSetChanged();
    }
}

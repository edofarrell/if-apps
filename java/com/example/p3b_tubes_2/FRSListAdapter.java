package com.example.p3b_tubes_2;

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
    private ArrayList<TahunAjaran.TahunAjar> tahunAjaran;
    private class ViewHolder{
        protected int i;
        private TextView academicYears;

        public ViewHolder(ItemListFrsBinding binding,int i){
            this.i = i;
            this.academicYears = binding.tvAcademicYear;
        }

        private void updateView(int i) {
            String tahun = "Tahun "+tahunAjaran.get(i).getTahun();
            String semester = "Semester "+tahunAjaran.get(i).getSemester();
            this.academicYears.setText(tahun+" "+semester);
        }
    }

    public FRSListAdapter(FRSPresenter presenter){
        this.presenter = presenter;
        this.tahunAjaran = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return tahunAjaran.size();
    }

    @Override
    public Object getItem(int i) {
        return tahunAjaran.get(i);
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

    public void update(ArrayList<TahunAjaran.TahunAjar> tahunAjaran){
        this.tahunAjaran = tahunAjaran;
        notifyDataSetChanged();
    }
}

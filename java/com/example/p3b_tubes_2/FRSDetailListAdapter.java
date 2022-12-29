package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListDetailFrsBinding;
import com.example.p3b_tubes_2.databinding.ItemListFrsBinding;

import java.util.ArrayList;

public class FRSDetailListAdapter extends BaseAdapter {
    private FRSPresenter presenter;
    private ArrayList<MataKuliahList.MataKuliah> listMataKuliah;
    private class ViewHolder{
        protected int i;
        private TextView matkul;

        public ViewHolder(ItemListDetailFrsBinding binding, int i){
            this.i = i;
            this.matkul = binding.tvMatkul;
        }

        private void updateView(int i) {
            this.matkul.setText(listMataKuliah.get(i).getName());
        }
    }

    public FRSDetailListAdapter(FRSPresenter presenter){
        this.presenter = presenter;
        this.listMataKuliah = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listMataKuliah.size();
    }

    @Override
    public Object getItem(int i) {
        return listMataKuliah.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemListDetailFrsBinding binding = ItemListDetailFrsBinding.inflate(inflater);
        FRSDetailListAdapter.ViewHolder viewHolder;
        if (view == null) {
            view = binding.getRoot();
            viewHolder = new FRSDetailListAdapter.ViewHolder(binding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FRSDetailListAdapter.ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }

    public void update(ArrayList<MataKuliahList.MataKuliah> mataKuliah){
        this.listMataKuliah = mataKuliah;
        notifyDataSetChanged();
    }
}

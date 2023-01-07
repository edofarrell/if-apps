package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.MataKuliahList;
import com.example.p3b_tubes_2.Presenter.FRSPresenter;
import com.example.p3b_tubes_2.databinding.ItemListDetailFrsViewBinding;

import java.util.ArrayList;

public class FRSDetailListAdapterView extends BaseAdapter {
    private FRSPresenter presenter;
    private ArrayList<MataKuliahList.MataKuliah> listMataKuliah;
    private class ViewHolder{
        protected int i;
        private TextView matkul;

        public ViewHolder(ItemListDetailFrsViewBinding binding, int i){
            this.i = i;
            this.matkul = binding.tvMatkul;
            binding.llMatkulView.setOnClickListener(this::onClick);
            binding.btnDelete.setOnClickListener(this::delete);
        }

        private void delete(View view) {
            listMataKuliah.remove(i);
            notifyDataSetChanged();
        }

        private void updateView(int i) {
            this.matkul.setText(listMataKuliah.get(i).getName());
        }

        private void onClick(View view){

        }
    }

    public FRSDetailListAdapterView(FRSPresenter presenter){
        this.presenter = presenter;
        this.listMataKuliah = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.listMataKuliah.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listMataKuliah.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemListDetailFrsViewBinding binding = ItemListDetailFrsViewBinding.inflate(inflater);
        FRSDetailListAdapterView.ViewHolder viewHolder;
        if (view == null) {
            view = binding.getRoot();
            viewHolder = new FRSDetailListAdapterView.ViewHolder(binding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FRSDetailListAdapterView.ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);
        return view;
    }

    public MataKuliahList.MataKuliah getMataKuliahEnrol(int i){
        return this.listMataKuliah.get(i);
    }

    public void update(MataKuliahList.MataKuliah mataKuliah){
//        Log.d("DEBUG",mataKuliah.get(0).getName());
//        Log.d("DEBUG",mataKuliah.get(1).getName());
        if(!listMataKuliah.contains(mataKuliah)){
            listMataKuliah.add(mataKuliah);
        }
        notifyDataSetChanged();
    }
}

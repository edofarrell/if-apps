package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.MataKuliahList;
import com.example.p3b_tubes_2.Presenter.FRSPresenter;
import com.example.p3b_tubes_2.databinding.ItemListDetailFrsBinding;

import java.util.ArrayList;

public class FRSDetailListAdapterSearch extends BaseAdapter {
    private FRSPresenter presenter;
    private ArrayList<MataKuliahList.MataKuliah> listMataKuliah;
    private class ViewHolder{
        protected int i;
        private TextView matkul;

        public ViewHolder(ItemListDetailFrsBinding binding, int i){
            this.i = i;
            this.matkul = binding.tvMatkul;
            binding.getRoot().setOnClickListener(this::addToSelectedMataKuliah);
        }

        private void addToSelectedMataKuliah(View view) {
            MataKuliahList.MataKuliah matkul = listMataKuliah.get(i);
            presenter.addToSelectedMataKuliah(matkul);
        }

        private void updateView(int i) {
            this.matkul.setText(listMataKuliah.get(i).getName());
        }
    }

    public FRSDetailListAdapterSearch(FRSPresenter presenter){
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
        ItemListDetailFrsBinding binding = ItemListDetailFrsBinding.inflate(inflater);
        ViewHolder viewHolder;
        if (view == null) {
            view = binding.getRoot();
            viewHolder = new ViewHolder(binding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);
        return view;
    }

    public void update(ArrayList<MataKuliahList.MataKuliah> mataKuliah){
        this.listMataKuliah = mataKuliah;
        notifyDataSetChanged();
    }
}

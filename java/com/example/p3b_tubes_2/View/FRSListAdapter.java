package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.TahunAjaran;
import com.example.p3b_tubes_2.Presenter.FRSPresenter;
import com.example.p3b_tubes_2.databinding.ItemListFrsBinding;

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
            this.i = i;
            this.academicYears.setText(tahunAjaran.getListAcademicYears().get(i).toString());
        }

        private void openDetail(View view){
            presenter.openFragment(tahunAjaran.getListAcademicYears().get(i));
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
            viewHolder = new ViewHolder(binding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }

    public void update(TahunAjaran tahunAjaran){
        this.tahunAjaran = tahunAjaran;
        notifyDataSetChanged();
    }
}

package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListPertemuanDibuatBinding;

import java.util.ArrayList;

public class PertemuanListAdapter extends BaseAdapter {
    ArrayList<Pertemuan> pertemuanArrayList;

    private class ViewHolder {
        protected int i;
        protected TextView tvtitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;
        protected Button btnSeeDetails;

        public ViewHolder(int i, ItemListPertemuanDibuatBinding binding) {
            this.i = i;
            this.tvtitle = binding.tvTitle;
            this.tvDate = binding.tvDate;
            this.tvStartTime = binding.tvStartTime;
            this.tvEndTime = binding.tvEndTime;
            this.btnSeeDetails = binding.btnSeeDetails;
        }

        private void updateView(int i) {
            Pertemuan appointment = pertemuanArrayList.get(i);
            this.tvStartTime.setText(appointment.getStartTime());
            this.tvEndTime.setText(appointment.getEndTime());
        }
    }

    public PertemuanListAdapter() {
        this.pertemuanArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.pertemuanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pertemuanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemListPertemuanDibuatBinding binding = ItemListPertemuanDibuatBinding.inflate(inflater);

        ViewHolder viewHolder;
        if(view == null) {
            view = binding.getRoot();
            viewHolder = new ViewHolder(i, binding);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }
}

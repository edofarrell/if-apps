package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListPertemuanBinding;

import java.text.SimpleDateFormat;

public class PertemuanListAdapter extends BaseAdapter {

    private PertemuanList pertemuanList;

    private class ViewHolder{
        protected int i;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;

        public ViewHolder(ItemListPertemuanBinding binding, int i){
            this.i = i;
            this.tvTitle = binding.title;
            this.tvDate = binding.date;
            this.tvStartTime = binding.startTime;
            this.tvEndTime = binding.endTime;
        }

        private void updateView(int i){
            PertemuanList.Pertemuan pertemuan = pertemuanList.getPertemuan(i);
            this.tvTitle.setText(pertemuan.getTitle());
            this.tvDate.setText(new SimpleDateFormat("dd MMM yyy").format(pertemuan.getEndTime()));
            this.tvStartTime.setText(pertemuan.getStartTime());
            this.tvEndTime.setText(pertemuan.getEndTime());
        }
    }

    @Override
    public int getCount() {
        return this.pertemuanList.getSize();
    }

    @Override
    public Object getItem(int position) {
        return this.pertemuanList.getPertemuan(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListPertemuanBinding itemListPertemuanBinding = ItemListPertemuanBinding.inflate(inflater);
        ViewHolder viewHolder;
        if (view == null) {
            view = itemListPertemuanBinding.getRoot();
            viewHolder = new ViewHolder(itemListPertemuanBinding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }
}

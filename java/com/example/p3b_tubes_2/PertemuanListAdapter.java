package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.databinding.ItemListPertemuanBinding;

import java.util.ArrayList;

public class PertemuanListAdapter extends BaseAdapter {

    private ArrayList<PertemuanList.Pertemuan> pertemuanList;
    private PertemuanPresenter presenter;

    private class ViewHolder {
        protected int i;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;

        public ViewHolder(ItemListPertemuanBinding binding, int i) {
            this.i = i;
            this.tvTitle = binding.title;
            this.tvDate = binding.date;
            this.tvStartTime = binding.startTime;
            this.tvEndTime = binding.endTime;

            binding.llPertemuan.setOnClickListener(this::openDetail);
        }

        private void openDetail(View view) {
            PertemuanList.Pertemuan pertemuan = pertemuanList.get(i);
            presenter.getPartisipan(pertemuan);
        }

        private void updateView(int i) {
            PertemuanList.Pertemuan pertemuan = pertemuanList.get(i);
            this.tvTitle.setText(pertemuan.getTitle());
            this.tvDate.setText(pertemuan.getStartTime().substring(0, 10));
            this.tvStartTime.setText(pertemuan.getStartTime());
            this.tvEndTime.setText(pertemuan.getEndTime());
        }
    }

    public PertemuanListAdapter(PertemuanPresenter presenter) {
        this.pertemuanList = new ArrayList<>();
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return this.pertemuanList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.pertemuanList.get(position);
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

    public void update(ArrayList<PertemuanList.Pertemuan> pertemuanList) {
        this.pertemuanList = pertemuanList;
        notifyDataSetChanged();
    }
}

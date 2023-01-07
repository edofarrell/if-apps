package com.example.p3b_tubes_2.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.databinding.ItemListPertemuanDibuatBinding;

public class PertemuanDibuatListAdapter extends BaseAdapter {

    private PertemuanList pertemuanList;
    private PertemuanPresenter presenter;

    private class ViewHolder {
        protected int i;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;
        protected TextView tvDescription;
        protected TextView tvOrganizer;
        protected Button btnSeeDetails;
        protected Button btnDelete;

        public ViewHolder(ItemListPertemuanDibuatBinding binding, int i) {
            this.i = i;
            this.tvTitle = binding.tvTitle;
            this.tvDate = binding.tvDate;
            this.tvStartTime = binding.tvStartTime;
            this.tvEndTime = binding.tvEndTime;
            this.btnSeeDetails = binding.btnSeeDetails;
            this.btnDelete = binding.btnDelete;

            this.btnSeeDetails.setOnClickListener(this::openDetail);
            this.btnDelete.setOnClickListener(this::deletePertemuan);
        }

        private void deletePertemuan(View view) {
            PertemuanList.Pertemuan pertemuan = pertemuanList.getPertemuan(i);
            presenter.deletePertemuan(pertemuan.getId());
        }

        private void openDetail(View view) {
            PertemuanList.Pertemuan pertemuan = pertemuanList.getPertemuan(i);
            if(APIClient.loggedInId.equals(pertemuan.getOrganizer_id())){
                Log.d("DEBUG", "oragnizer");
                presenter.getPartisipanDibuat(pertemuan);
            }else{
                Log.d("DEBUG", "not organizer");
                presenter.openDetail(pertemuan);
            }

        }

        private void updateView(int i) {
            this.i = i;
            PertemuanList.Pertemuan pertemuan = pertemuanList.getPertemuan(i);
            this.tvTitle.setText(pertemuan.getTitle());
            this.tvDate.setText(pertemuan.getDate());
            this.tvStartTime.setText(pertemuan.getStartTime());
            this.tvEndTime.setText(pertemuan.getEndTime());
            if(!APIClient.loggedInId.equals(pertemuan.getOrganizer_id())){
                this.btnDelete.setVisibility(View.GONE);
            }
        }
    }

    public PertemuanDibuatListAdapter(PertemuanPresenter presenter) {
        this.pertemuanList = new PertemuanList();
        this.presenter = presenter;
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
        ItemListPertemuanDibuatBinding binding = ItemListPertemuanDibuatBinding.inflate(inflater);
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

    public void update(PertemuanList pertemuanList) {
        this.pertemuanList = pertemuanList;
        notifyDataSetChanged();
    }
}

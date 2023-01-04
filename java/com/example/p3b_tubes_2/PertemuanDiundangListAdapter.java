package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListPertemuanDibuatBinding;
import com.example.p3b_tubes_2.databinding.ItemListPertemuanDiundangBinding;

public class PertemuanDiundangListAdapter extends BaseAdapter {

    private APIPertemuanGetInvites listInvites;
    private PertemuanPresenter presenter;

    private class ViewHolder {
        protected int i;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;
        protected Button btnSeeDetails;

        public ViewHolder(ItemListPertemuanDiundangBinding binding, int i) {
            this.i = i;
            this.tvTitle = binding.tvTitle;
            this.tvDate = binding.tvDate;
            this.tvStartTime = binding.tvStartTime;
            this.tvEndTime = binding.tvEndTime;
            this.btnSeeDetails = binding.btnSeeDetails;

            this.btnSeeDetails.setOnClickListener(this::openDetail);
        }

        private void openDetail(View view) {
            APIPertemuanGetInvites.Invites invites = listInvites.getInvitation(i);
//            presenter.getPartisipanPertemuanDiundang(pertemuan);
        }

        private void updateView(int i) {
            this.i = i;
            APIPertemuanGetInvites.Invites invites = listInvites.getInvitation(i);
            this.tvTitle.setText(invites.title);
            this.tvDate.setText(invites.description);
            //this.tvStartTime.setText(pertemuan.getStartTime());
            //this.tvEndTime.setText(pertemuan.getEndTime());
        }
    }

    public PertemuanDiundangListAdapter(PertemuanPresenter presenter) {
        this.listInvites = new APIPertemuanGetInvites();
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return this.listInvites.getListSize();
    }

    @Override
    public Object getItem(int position) {
        return this.listInvites.getInvitation(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListPertemuanDiundangBinding binding = ItemListPertemuanDiundangBinding.inflate(inflater);
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

    public void update(APIPertemuanGetInvites listInvites) {
        this.listInvites = listInvites;
        notifyDataSetChanged();
    }
}


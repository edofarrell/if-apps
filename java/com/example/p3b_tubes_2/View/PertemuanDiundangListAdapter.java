package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.databinding.ItemListPertemuanDiundangBinding;

public class PertemuanDiundangListAdapter extends BaseAdapter {

    private InviteList listInvites;
    private PertemuanPresenter presenter;

    private class ViewHolder {
        protected int i;
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvStartTime;
        protected TextView tvEndTime;
        protected TextView tvAccepted;
        protected Button btnSeeDetails;

        public ViewHolder(ItemListPertemuanDiundangBinding binding, int i) {
            this.i = i;
            this.tvTitle = binding.tvTitle;
            this.tvDate = binding.tvDate;
            this.tvStartTime = binding.tvStartTime;
            this.tvEndTime = binding.tvEndTime;
            this.btnSeeDetails = binding.btnSeeDetails;
            this.tvAccepted = binding.tvAccepted;

            this.btnSeeDetails.setOnClickListener(this::openDetail);
        }

        private void openDetail(View view) {
            InviteList.Invites invite = listInvites.getInvitation(i);
            presenter.openDetailUndangan(invite);
        }

        private void updateView(int i) {
            this.i = i;
            InviteList.Invites invite = listInvites.getInvitation(i);
            this.tvTitle.setText(invite.getTitle());
            this.tvDate.setText(invite.getDate());
            this.tvStartTime.setText(invite.getStartTime());
            this.tvEndTime.setText(invite.getEndTime());
            if(invite.isAttending()){
                tvAccepted.setVisibility(View.VISIBLE);
                btnSeeDetails.setVisibility(View.GONE);
            }else{
                tvAccepted.setVisibility(View.GONE);
                btnSeeDetails.setVisibility(View.VISIBLE);
            }
        }
    }

    public PertemuanDiundangListAdapter(PertemuanPresenter presenter) {
        this.listInvites = new InviteList();
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

    public void update(InviteList listInvites) {
        this.listInvites = listInvites;
        notifyDataSetChanged();
    }
}


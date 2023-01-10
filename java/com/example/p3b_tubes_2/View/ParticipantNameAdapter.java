package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.databinding.ItemListNamaBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ParticipantNameAdapter extends BaseAdapter {
    List<User> userArrayList;
    MainPresenter presenter;

    private class ViewHolder {
        private int i;
        private LinearLayout cardLayout;
        private TextView tvNama;

        public ViewHolder(int i, ItemListNamaBinding binding) {
            this.i = i;
            cardLayout = binding.cardLayout;
            tvNama = binding.tvNama;

            cardLayout.setOnClickListener(this::onClick);
        }

        private void onClick(View view) {
            User user = userArrayList.get(i);
            presenter.selectUser(user);
        }

        private void updateView(int i) {
            this.i = i;
            User user = userArrayList.get(i);
            this.tvNama.setText(user.getName());
        }
    }

    public ParticipantNameAdapter(MainPresenter presenter) {
        this.userArrayList = new ArrayList<>();
        this.presenter= presenter;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListNamaBinding binding = ItemListNamaBinding.inflate(inflater);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = binding.getRoot();
            viewHolder = new ViewHolder(position, binding);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.updateView(position);

        return convertView;
    }

    public void update(List<User> users){
        this.userArrayList = users;
        notifyDataSetChanged();
    }
}

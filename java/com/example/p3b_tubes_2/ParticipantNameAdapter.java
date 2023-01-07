package com.example.p3b_tubes_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.databinding.ItemListFrsBinding;
import com.example.p3b_tubes_2.databinding.ItemListNamaBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ParticipantNameAdapter extends BaseAdapter {
    ArrayList<User> userArrayList;

    private class ViewHolder {
        private int i;
        private MaterialCardView cardLayout;
        private TextView tvNama;

        public ViewHolder(int i, ItemListNamaBinding binding) {
            this.i = i;
            cardLayout = binding.cardLayout;
            tvNama = binding.tvNama;

            cardLayout.setOnClickListener(this::onClick);
        }

        private void onClick(View view) {
        }

        private void updateView(int i) {
            User user = userArrayList.get(i);
            this.tvNama.setText(user.getName());
        }
    }

    public ParticipantNameAdapter() {
        this.userArrayList = new ArrayList<>();
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
}

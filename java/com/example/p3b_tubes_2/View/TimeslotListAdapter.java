package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.databinding.ItemListTimeslotBinding;

public class TimeslotListAdapter extends BaseAdapter {
    private TimeslotList timeslotList;

    public TimeslotListAdapter() {
        this.timeslotList = new TimeslotList();
    }

    private class ViewHolder {
        protected int i;
        protected LinearLayout llContainer;
        protected TextView tvDay;
        protected TextView tvTimeSlot;

        public ViewHolder(ItemListTimeslotBinding binding, int i) {
            this.llContainer = binding.llContainer;
            this.tvDay = binding.tvDay;
            this.tvTimeSlot = binding.tvTimeSlots;
        }

        private void updateView(int i) {
            this.i = i;
            TimeslotList.Timeslot timeslot = timeslotList.get(i);
            String hari = timeslot.getDay();
            String waktu = timeslot.getStartTime() + "-" + timeslot.getEndTime();
            this.tvDay.setText(hari);
            this.tvTimeSlot.setText(waktu);
        }
    }

    @Override
    public int getCount() {
        return this.timeslotList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.timeslotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListTimeslotBinding binding = ItemListTimeslotBinding.inflate(inflater);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = binding.getRoot();
            viewHolder = new ViewHolder(binding, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.updateView(position);

        return convertView;
    }

    public void update(TimeslotList timeslotList) {
        this.timeslotList = timeslotList;
        notifyDataSetChanged();
    }
}

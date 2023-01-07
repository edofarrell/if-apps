package com.example.p3b_tubes_2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p3b_tubes_2.Model.TagList;
import com.example.p3b_tubes_2.databinding.ItemListTagBinding;

public class TagListAdapter extends BaseAdapter {
    private TagList tagList;

    public TagListAdapter(){
        this.tagList = new TagList();
    }

    private class ViewHolder {
        protected int i;
        protected TextView tvTag;

        public ViewHolder(ItemListTagBinding binding, int i, ViewGroup parent) {
            this.i = i;
            this.tvTag = binding.tvTag;
        }

        private void updateView(int i) {
            this.i = i;
            TagList.Tag tag = tagList.get(i);
            this.tvTag.setText(tag.getName());
        }
    }

    @Override
    public int getCount() {
        return this.tagList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.tagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListTagBinding itemListTagBinding = ItemListTagBinding.inflate(inflater);
        ViewHolder viewHolder;
        if (view == null) {
            view = itemListTagBinding.getRoot();
            viewHolder = new ViewHolder(itemListTagBinding, i, parent);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }

    public void update(TagList listTag){
        this.tagList = listTag;
        notifyDataSetChanged();
    }
}

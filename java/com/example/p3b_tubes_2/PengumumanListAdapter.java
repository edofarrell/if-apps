package com.example.p3b_tubes_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p3b_tubes_2.databinding.ItemListPengumumanBinding;

public class PengumumanListAdapter extends BaseAdapter {
    private PengumumanList pengumumanList;
    private PengumumanPresenter presenter;

    private class ViewHolder {
        protected int i;
        protected boolean isRead;
        protected TextView tvJudul;
        protected TextView tvTag;
        protected LinearLayout llPengumuman;

        public ViewHolder(ItemListPengumumanBinding binding, int i) {
            this.i = i;
            this.tvJudul = binding.tvJudul;
            this.tvTag = binding.tvTag;
            this.llPengumuman = binding.llPengumuman;

            binding.btnDetail.setOnClickListener(this::openDetail);
            binding.btnDelete.setOnClickListener(this::onClickDelete);
        }

        private void onClickDelete(View view) {
            PengumumanList.Pengumuman pengumuman = pengumumanList.getPengumuman(i);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Hapus Pengumuman \n\"" + pengumuman.getTitle() + "\" ?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    presenter.deletePengumuman(pengumuman.getId());
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog deleteAlert = builder.create();
            deleteAlert.show();
        }

        private void openDetail(View view) {
//            this.isRead = true;
            this.llPengumuman.setBackgroundColor(Color.WHITE);
            PengumumanList.Pengumuman pengumuman = pengumumanList.getPengumuman(i);
            presenter.getPengumumanDetail(pengumuman);
        }

        private void updateView(int i) {
//            if(this.isRead == true){
//                this.llPengumuman.setBackgroundColor(Color.WHITE);
//            }
            PengumumanList.Pengumuman pengumuman = pengumumanList.getPengumuman(i);
            this.tvJudul.setText(pengumuman.getTitle());
            this.tvTag.setText(pengumuman.getTags());
        }
    }

    public PengumumanListAdapter(PengumumanPresenter presenter) {
        this.pengumumanList = new PengumumanList();
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return this.pengumumanList.getSize();
    }

    @Override
    public Object getItem(int i) {
        return this.pengumumanList.getPengumuman(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListPengumumanBinding itemListPengumumanBinding = ItemListPengumumanBinding.inflate(inflater);
        ViewHolder viewHolder;
        if (view == null) {
            view = itemListPengumumanBinding.getRoot();
            viewHolder = new ViewHolder(itemListPengumumanBinding, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);

        return view;
    }

    public void update(PengumumanList pengumumanList) {
        this.pengumumanList = pengumumanList;
        notifyDataSetChanged();
    }
}

package com.example.p3b_tubes_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.p3b_tubes_2.databinding.ItemListPengumumanBinding;

public class PengumumanListAdapter extends BaseAdapter {
    private PengumumanList pengumumanList;
    private PengumumanPresenter presenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private class ViewHolder {
        protected int i;
        protected TextView tvJudul;
        protected TextView tvTag;
        protected LinearLayout llPengumuman;
        protected TextView tvLihatDetail;
        protected Context context;

        public ViewHolder(ItemListPengumumanBinding binding, int i, ViewGroup parent) {
            this.i = i;
            this.tvJudul = binding.tvJudul;
            this.tvTag = binding.tvTag;
            this.llPengumuman = binding.llPengumuman;
            this.tvLihatDetail = binding.btnDetail;
            this.context = parent.getContext();

            binding.btnDetail.setOnClickListener(this::openDetail);
            binding.btnDelete.setOnClickListener(this::onClickDelete);

            if(APIClient.role.equals("student")){
                binding.btnDelete.setVisibility(View.GONE);
            }

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
            PengumumanList.Pengumuman pengumuman = pengumumanList.getPengumuman(i);
            if(!sp.getBoolean(pengumuman.getId(), false)){
                editor.putBoolean(pengumuman.getId(), true);
                editor.commit();

                this.llPengumuman.setBackground(ContextCompat.getDrawable(context, R.drawable.border_grey));
                this.tvLihatDetail.setTextColor(ContextCompat.getColor(this.context, R.color.primary));
            }
            presenter.getPengumumanDetail(pengumuman);
        }

        private void updateView(int i) {
            PengumumanList.Pengumuman pengumuman = pengumumanList.getPengumuman(i);
            if(sp.getBoolean(pengumuman.getId(), false)){
                this.llPengumuman.setBackground(ContextCompat.getDrawable(context, R.drawable.border_grey));
                this.tvLihatDetail.setTextColor(ContextCompat.getColor(this.context, R.color.primary));
            }
            else{
                this.llPengumuman.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_pengumuman));
            }

            this.tvJudul.setText(pengumuman.getTitle());
            this.tvTag.setText(pengumuman.getTags());
        }
    }

    public PengumumanListAdapter(PengumumanPresenter presenter, SharedPreferences sp) {
        this.pengumumanList = new PengumumanList();
        this.presenter = presenter;
        this.sp = sp;
        this.editor = this.sp.edit();
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
            viewHolder = new ViewHolder(itemListPengumumanBinding, i, parent);
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

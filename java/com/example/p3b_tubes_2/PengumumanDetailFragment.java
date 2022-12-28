package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.databinding.FragmentDetailPengumumanBinding;

public class PengumumanDetailFragment extends DialogFragment {

    private FragmentDetailPengumumanBinding binding;
    private PengumumanList.Pengumuman pengumuman;

    private PengumumanDetailFragment() {}

    public static PengumumanDetailFragment newInstance(FragmentManager fm, PengumumanList.Pengumuman pengumuman) {
        PengumumanDetailFragment fragment = new PengumumanDetailFragment();
        fragment.pengumuman = pengumuman;

        fragment.show(fm, "openPengumumanDetail");

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = this.getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentDetailPengumumanBinding.inflate(inflater);

        this.binding.tvJudul.setText(this.pengumuman.getTitle());
        this.binding.tvTags.setText(this.pengumuman.getTags());
        this.binding.tvContent.setText(this.pengumuman.getContent());

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
    }

    private void onClickKembali(View view) {
        this.dismiss();
    }
}

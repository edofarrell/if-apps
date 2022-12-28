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

import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanBinding;

public class PertemuanDetailFragment extends DialogFragment {

    private FragmentDetailPertemuanBinding binding;
    private PertemuanList.Pertemuan pertemuan;

    public PertemuanDetailFragment() {}

    public static PertemuanDetailFragment newInstance(FragmentManager fm, PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragment fragment = new PertemuanDetailFragment();
        fragment.pertemuan = pertemuan;
        fragment.show(fm, "openAppointmentDetail");

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.binding = FragmentDetailPertemuanBinding.inflate(inflater);

        this.binding.tvTitle.setText(this.pertemuan.getTitle());
        this.binding.tvOrganizer.setText(this.pertemuan.getOrganizerName());
//        this.binding.tvTanggal.setText(this.pertemuan.getStartTime());
//        this.binding.tvWaktu.setText(this.pertemuan.getStartTime());
//        this.binding.tvPartisipan.setText();
        this.binding.tvPartisipan.setText(this.pertemuan.getPartisipan());
        this.binding.tvDeskripsi.setText(this.pertemuan.getDescription());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
    }

    private void onClickKembali(View view) {
        dismiss();
    }
}

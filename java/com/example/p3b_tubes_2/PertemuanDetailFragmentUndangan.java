package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanBinding;
import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanUndanganBinding;

public class PertemuanDetailFragmentUndangan extends DialogFragment {

    private FragmentDetailPertemuanUndanganBinding binding;
    private PertemuanList.Pertemuan pertemuan;

    public PertemuanDetailFragmentUndangan() {
    }

    public static PertemuanDetailFragmentUndangan newInstance(FragmentManager fm, PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragmentUndangan fragment = new PertemuanDetailFragmentUndangan();
        fragment.pertemuan = pertemuan;
        fragment.show(fm, "openAppointmentInvitationDetail");

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

        this.binding = FragmentDetailPertemuanUndanganBinding.inflate(inflater);

        this.binding.tvTitle.setText(this.pertemuan.getTitle());
        this.binding.tvOrganizer.setText(this.pertemuan.getOrganizerName());
        this.binding.tvTanggal.setText(this.pertemuan.getDate());
        this.binding.tvWaktuMulai.setText(this.pertemuan.getStartTime());
        this.binding.tvWaktuSelesai.setText(this.pertemuan.getEndTime());
        this.binding.tvPartisipan.setText(this.pertemuan.getPartisipan());
        this.binding.tvDeskripsi.setText(this.pertemuan.getDescription());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
        this.binding.btnAccept.setOnClickListener(this::acceptInvitation);
    }

    private void acceptInvitation(View view) {
    }

    private void onClickKembali(View view) {
        dismiss();
    }
}

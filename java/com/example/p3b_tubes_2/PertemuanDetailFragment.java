package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanBinding;

public class PertemuanDetailFragment extends Fragment {

    private FragmentDetailPertemuanBinding binding;
    private PertemuanList.Pertemuan pertemuan;

    public PertemuanDetailFragment() {}

    public static PertemuanDetailFragment newInstance(PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragment fragment = new PertemuanDetailFragment();
        fragment.pertemuan = pertemuan;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentDetailPertemuanBinding.inflate(inflater);

        this.binding.tvTitle.setText(this.pertemuan.getTitle());
        this.binding.tvOrganizer.setText(this.pertemuan.getOrganizerName());
//        this.binding.tvTanggal.setText(this.pertemuan.getStartTime());
//        this.binding.tvWaktu.setText(this.pertemuan.getStartTime());
//        this.binding.tvPartisipan.setText();
        this.binding.tvPartisipan.setText(this.pertemuan.getPartisipan());
        this.binding.tvDeskripsi.setText(this.pertemuan.getDescription());
        this.binding.btnKembali.setOnClickListener(this::onClickKembali);

        return binding.getRoot();
    }

    private void onClickKembali(View view) {
        Bundle result = new Bundle();
        result.putString("page", "pertemuan");
        getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

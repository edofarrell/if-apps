package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentDetailPengumumanBinding;

public class DetailPengumumanFragment extends Fragment {

    private FragmentDetailPengumumanBinding binding;
    private PengumumanList.Pengumuman pengumuman;

    private DetailPengumumanFragment(){}

    public static DetailPengumumanFragment newInstance(PengumumanList.Pengumuman pengumuman) {
        Bundle args = new Bundle();
        DetailPengumumanFragment fragment = new DetailPengumumanFragment();
        fragment.pengumuman = pengumuman;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentDetailPengumumanBinding.inflate(inflater);

        this.binding.tvJudul.setText(this.pengumuman.getTitle());
        this.binding.tvTags.setText(this.pengumuman.getTags());
        this.binding.tvContent.setText(this.pengumuman.getContent());
        this.binding.btnKembali.setOnClickListener(this::onClickKembali);

        return this.binding.getRoot();
    }

    private void onClickKembali(View view) {
        Bundle result = new Bundle();
        result.putString("page", "pengumuman");
        getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

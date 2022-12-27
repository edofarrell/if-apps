package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentTambahPengumumanBinding;

public class PengumumanTambahFragment extends Fragment {

    private FragmentTambahPengumumanBinding binding;
    private PengumumanPresenter presenter;

    private PengumumanTambahFragment() {}

    public static PengumumanTambahFragment newInstance(PengumumanPresenter presenter) {
        PengumumanTambahFragment fragment = new PengumumanTambahFragment();
        fragment.presenter = presenter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentTambahPengumumanBinding.inflate(inflater);

        this.binding.btnSimpan.setOnClickListener(this::OnClickSimpan);

        return this.binding.getRoot();
    }

    private void OnClickSimpan(View view) {
        String title = this.binding.etJudul.getText().toString();
        String content = this.binding.etDeskripsi.getText().toString();
        String[] tags = {"d78227d2-053e-4d57-8ef7-ba1560f412de"};
        this.presenter.addPengumuman(title, content, tags);
    }
}

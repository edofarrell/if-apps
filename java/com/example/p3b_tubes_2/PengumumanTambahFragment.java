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

import com.example.p3b_tubes_2.databinding.FragmentTambahPengumumanBinding;

public class PengumumanTambahFragment extends DialogFragment {

    private FragmentTambahPengumumanBinding binding;
    private PengumumanPresenter presenter;

    private PengumumanTambahFragment() {}

    public static PengumumanTambahFragment newInstance(FragmentManager fm, PengumumanPresenter presenter) {
        PengumumanTambahFragment fragment = new PengumumanTambahFragment();
        fragment.presenter = presenter;
        fragment.show(fm, "tambahPengumuman");
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
    }

    private void onClickKembali(View view) {
        this.dismiss();
    }
}

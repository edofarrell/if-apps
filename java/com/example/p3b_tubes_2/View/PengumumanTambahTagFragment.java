package com.example.p3b_tubes_2.View;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.databinding.FragmentTambahTagBinding;

public class PengumumanTambahTagFragment extends DialogFragment {
    FragmentTambahTagBinding binding;
    public static PengumumanTambahTagFragment newInstance(FragmentManager fm){
        Bundle args = new Bundle();
        PengumumanTambahTagFragment fragment = new PengumumanTambahTagFragment();
        fragment.setArguments(args);
        fragment.show(fm, "tambahTag");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentTambahTagBinding.inflate(inflater);
        return binding.getRoot();
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

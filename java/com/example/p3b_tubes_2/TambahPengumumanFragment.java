package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentTambahPengumumanBinding;

public class TambahPengumumanFragment extends Fragment {

    private FragmentTambahPengumumanBinding binding;

    private TambahPengumumanFragment () {}

    public static TambahPengumumanFragment newInstance() {
        Bundle args = new Bundle();
        TambahPengumumanFragment fragment = new TambahPengumumanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentTambahPengumumanBinding.inflate(inflater);
        return this.binding.getRoot();
    }
}

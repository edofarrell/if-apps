package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentTambahPertemuanBinding;

public class PertemuanTambahFragment extends Fragment {
    private FragmentTambahPertemuanBinding binding;
    public PertemuanTambahFragment(){}

    public static PertemuanTambahFragment newInstance(){
        PertemuanTambahFragment fragment = new PertemuanTambahFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTambahPertemuanBinding.inflate(inflater);
        return binding.getRoot();
    }
}

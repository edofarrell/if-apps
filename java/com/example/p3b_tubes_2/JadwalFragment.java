package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentJadwalBinding;

public class JadwalFragment extends Fragment {
    FragmentJadwalBinding binding;
    public JadwalFragment(){}

    public static JadwalFragment newInstance(){
        JadwalFragment fragment = new JadwalFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJadwalBinding.inflate(inflater);
        return binding.getRoot();
    }
}

package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentJadwalBinding;
import com.example.p3b_tubes_2.databinding.FragmentLeftBinding;

public class LeftFragment extends Fragment {
    FragmentLeftBinding binding;

    public static LeftFragment newInstance() {

        Bundle args = new Bundle();

        LeftFragment fragment = new LeftFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLeftBinding.inflate(inflater);
        return binding.getRoot();
    }
}

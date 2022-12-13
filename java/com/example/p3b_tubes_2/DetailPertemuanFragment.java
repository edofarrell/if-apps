package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanBinding;

public class DetailPertemuanFragment extends Fragment {
    FragmentDetailPertemuanBinding binding;
    public DetailPertemuanFragment(){}

    public static DetailPertemuanFragment newInstance(){
        DetailPertemuanFragment fragment = new DetailPertemuanFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailPertemuanBinding.inflate(inflater);
        return binding.getRoot();
    }
}

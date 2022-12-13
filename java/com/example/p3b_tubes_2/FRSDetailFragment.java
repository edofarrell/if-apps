package com.example.p3b_tubes_2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p3b_tubes_2.databinding.FragmentFrsDetailBinding;
import com.example.p3b_tubes_2.databinding.FragmentFrsTambahBinding;

public class FRSDetailFragment extends Fragment {
    FragmentFrsDetailBinding binding;
    public static FRSDetailFragment newInstance() {

        Bundle args = new Bundle();

        FRSDetailFragment fragment = new FRSDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentFrsDetailBinding.inflate(inflater);
        return binding.getRoot();
    }
}

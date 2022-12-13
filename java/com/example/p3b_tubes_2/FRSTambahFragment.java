package com.example.p3b_tubes_2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p3b_tubes_2.databinding.FragmentFrsTambahBinding;

public class FRSTambahFragment extends Fragment {
    FragmentFrsTambahBinding binding;
    public static FRSTambahFragment newInstance() {

        Bundle args = new Bundle();

        FRSTambahFragment fragment = new FRSTambahFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentFrsTambahBinding.inflate(inflater);
        return binding.getRoot();
    }

}

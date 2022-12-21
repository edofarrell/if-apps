package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;

public class PertemuanFragment extends Fragment implements PertemuanContract.View{
    private FragmentPertemuanBinding binding;
    private PertemuanPresenter presenter;

    private PertemuanFragment(){}

    public static PertemuanFragment newInstance(MainPresenter mainPresenter){
        Bundle args = new Bundle();
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = new PertemuanPresenter(fragment, mainPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPertemuanBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void update() {

    }
}

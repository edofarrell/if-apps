package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanDibuatBinding;
import com.example.p3b_tubes_2.databinding.FragmentPertemuanDiundangBinding;

public class PertemuanDiundangFragment extends Fragment {
    private FragmentPertemuanDiundangBinding binding;
    private PertemuanListAdapter adapter;

    public static PertemuanDiundangFragment newInstance() {
        PertemuanDiundangFragment fragment = new PertemuanDiundangFragment();
        fragment.adapter = new PertemuanListAdapter();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDiundangBinding.inflate(inflater);

        this.binding.lstAppointments.setAdapter(this.adapter);

        return this.binding.getRoot();
    }
}

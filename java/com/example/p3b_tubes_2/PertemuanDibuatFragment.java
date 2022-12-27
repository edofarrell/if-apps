package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanDibuatBinding;

public class PertemuanDibuatFragment extends Fragment {
    private FragmentPertemuanDibuatBinding binding;
    private PertemuanListAdapter adapter;

    public PertemuanDibuatFragment(){};

    public static PertemuanDibuatFragment newInstance() {
        PertemuanDibuatFragment fragment = new PertemuanDibuatFragment();
        fragment.adapter = new PertemuanListAdapter();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDibuatBinding.inflate(inflater);

        this.binding.lstAppointments.setAdapter(this.adapter);
        this.binding.btnAddAppointment.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    private void onClick(View view) {
        TambahAppointmentFragment.display(getChildFragmentManager());
    }
}

package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanDibuatBinding;

public class PertemuanDibuatFragment extends Fragment implements PertemuanContract.View.PertemuanDibuat {
    private FragmentPertemuanDibuatBinding binding;
    private PertemuanDibuatListAdapter adapter;
    private PertemuanPresenter presenter;
    private FrameLayout frameLayout;

    public PertemuanDibuatFragment(){};

    public static PertemuanDibuatFragment newInstance(PertemuanPresenter presenter, FrameLayout frameLayout) {
        PertemuanDibuatFragment fragment = new PertemuanDibuatFragment();
        fragment.adapter = new PertemuanDibuatListAdapter(presenter);
        fragment.presenter = presenter;
        fragment.frameLayout = frameLayout;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDibuatBinding.inflate(inflater);

        this.presenter.getPertemuanDibuat();

        this.binding.lstAppointments.setAdapter(this.adapter);
        this.binding.btnAddAppointment.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    private void onClick(View view) {
        TambahAppointmentFragment.newInstance(getParentFragmentManager());
    }

    @Override
    public void updatePertemuanDibuat(PertemuanList pertemuanList) {
        this.adapter.update(pertemuanList);
    }

    @Override
    public void openDetailPertemuanDibuat(PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragment.newInstance(this.getParentFragmentManager(), pertemuan);
    }
}

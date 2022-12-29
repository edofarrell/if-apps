package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanDibuatBinding;

public class PertemuanDibuatFragment extends Fragment implements PertemuanContract.View.PertemuanDibuat {
    private FragmentPertemuanDibuatBinding binding;
    private PertemuanDibuatListAdapter adapter;
    private PertemuanPresenter pertemuanPresenter;
    private MainPresenter mainPresenter;
    private TambahPertemuanFragment tambahPertemuanFragment;

    public PertemuanDibuatFragment(){};

    public static PertemuanDibuatFragment newInstance(PertemuanPresenter pertemuanPresenter,  MainPresenter mainPresenter) {
        PertemuanDibuatFragment fragment = new PertemuanDibuatFragment();
        fragment.adapter = new PertemuanDibuatListAdapter(pertemuanPresenter);
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.mainPresenter = mainPresenter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDibuatBinding.inflate(inflater);

        this.pertemuanPresenter.getPertemuanDibuat();

        this.binding.lstAppointments.setAdapter(this.adapter);
        this.binding.btnAddAppointment.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    private void onClick(View view) {
        this.tambahPertemuanFragment = TambahPertemuanFragment.newInstance(getParentFragmentManager(), this.pertemuanPresenter, this.mainPresenter);
    }

    @Override
    public void updatePertemuanDibuat(PertemuanList pertemuanList) {
        this.adapter.update(pertemuanList);
    }

    @Override
    public void openDetailPertemuanDibuat(PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragment.newInstance(this.getParentFragmentManager(), pertemuan);
    }

    @Override
    public void addSelectedUserOnTambahPertemuan(User user) {
        this.tambahPertemuanFragment.addSelecteduser(user);
    }
}

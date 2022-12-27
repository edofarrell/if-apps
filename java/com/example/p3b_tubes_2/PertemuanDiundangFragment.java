package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanDiundangBinding;

public class PertemuanDiundangFragment extends Fragment implements PertemuanContract.View.PertemuanDiundang{
    private FragmentPertemuanDiundangBinding binding;
    private PertemuanDiundangListAdapter adapter;
    private PertemuanPresenter presenter;
    private FrameLayout frameLayout;

    public static PertemuanDiundangFragment newInstance(PertemuanPresenter presenter, FrameLayout frameLayout) {
        PertemuanDiundangFragment fragment = new PertemuanDiundangFragment();
        fragment.adapter = new PertemuanDiundangListAdapter(presenter);
        fragment.presenter = presenter;
        fragment.frameLayout = frameLayout;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDiundangBinding.inflate(inflater);

        this.binding.lstAppointments.setAdapter(this.adapter);

        return this.binding.getRoot();
    }

    @Override
    public void updatePertemuanDiundang(PertemuanList pertemuanList) {
        this.adapter.update(pertemuanList);
    }

    @Override
    public void openDetailPertemuanDiundang(PertemuanList.Pertemuan pertemuan) {
        PertemuanDetailFragment pertemuanDetailFragment = PertemuanDetailFragment.newInstance(pertemuan);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), pertemuanDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}

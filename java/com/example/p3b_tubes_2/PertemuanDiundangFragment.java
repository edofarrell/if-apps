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
        binding.getRoot().setOnClickListener(this::test);

        return this.binding.getRoot();
    }

    private void test(View view) {
        presenter.getInvites();
    }

    @Override
    public void updatePertemuanDiundang(APIPertemuanGetInvites listInvites) {
        this.adapter.update(listInvites);
    }

    @Override
    public void openDetailPertemuanDiundang(APIPertemuanGetInvites.Invites invites) {
        /*PertemuanDetailFragment pertemuanDetailFragment = PertemuanDetailFragment.newInstance(getParentFragmentManager(), pertemuan);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), pertemuanDetailFragment)
                .addToBackStack(null)
                .commit();*/
    }
}

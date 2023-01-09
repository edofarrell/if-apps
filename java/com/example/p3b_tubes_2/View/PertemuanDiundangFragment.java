package com.example.p3b_tubes_2.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.PertemuanContract;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.databinding.FragmentPertemuanDiundangBinding;

public class PertemuanDiundangFragment extends Fragment implements PertemuanContract.View.PertemuanDiundang {
    private FragmentPertemuanDiundangBinding binding;
    private PertemuanDiundangListAdapter adapter;
    private PertemuanPresenter presenter;
    private PertemuanDetailUndanganFragment pertemuanDetailUndanganFragment;

    public static PertemuanDiundangFragment newInstance(PertemuanPresenter presenter) {
        PertemuanDiundangFragment fragment = new PertemuanDiundangFragment();
        fragment.adapter = new PertemuanDiundangListAdapter(presenter);
        fragment.presenter = presenter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDiundangBinding.inflate(inflater);

        this.binding.lstAppointments.setAdapter(this.adapter);
        this.presenter.getInvites();

        return this.binding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            this.presenter.getInvites();
        }
    }

    @Override
    public void updatePertemuanDiundang(InviteList listInvites) {
        this.adapter.update(listInvites);
    }

    @Override
    public void openDetailPertemuanDiundang(InviteList.Invites invite) {
        this.pertemuanDetailUndanganFragment = PertemuanDetailUndanganFragment.newInstance(getParentFragmentManager(), invite, this.presenter);
    }

    @Override
    public void closeDetail() {
        this.pertemuanDetailUndanganFragment.dismiss();
        this.presenter.getInvites();
    }

    @Override
    public void showErrorAcceptInvite(String msg) {
        this.pertemuanDetailUndanganFragment.showError(msg);
    }
}

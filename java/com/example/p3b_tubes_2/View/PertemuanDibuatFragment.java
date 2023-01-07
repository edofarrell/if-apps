package com.example.p3b_tubes_2.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.PertemuanContract;
import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.databinding.FragmentPertemuanDibuatBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PertemuanDibuatFragment extends Fragment implements PertemuanContract.View.PertemuanDibuat {
    private FragmentPertemuanDibuatBinding binding;
    private PertemuanDibuatListAdapter adapter;
    private PertemuanPresenter pertemuanPresenter;
    private MainPresenter mainPresenter;
    private TambahPertemuanFragment tambahPertemuanFragment;
    private TambahPartisipanPertemuanFragment tambahPartisipanPertemuanFragment;
    private TambahSlotWaktuFragment tambahSlotWaktuFragment;

    private boolean isFabsVisible;

    public PertemuanDibuatFragment(){};

    public static PertemuanDibuatFragment newInstance(PertemuanPresenter pertemuanPresenter,  MainPresenter mainPresenter) {
        PertemuanDibuatFragment fragment = new PertemuanDibuatFragment();
        fragment.adapter = new PertemuanDibuatListAdapter(pertemuanPresenter);
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.mainPresenter = mainPresenter;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanDibuatBinding.inflate(inflater);

        this.pertemuanPresenter.getPertemuanDibuat();

        hideExpandableFAB();

        this.binding.lstAppointments.setAdapter(this.adapter);
        this.binding.btnAddAppointment.setOnClickListener(this::onClick);
        this.binding.btnAddInvitasiPertemuan.setOnClickListener(this::openAddPertemuan);
        this.binding.btnAddTimeSlot.setOnClickListener(this::openAddTimeSlot);

        return binding.getRoot();
    }

    private void onClick(View view) {
        FloatingActionButton floatingActionButton = this.binding.btnAddTimeSlot;
        if(!isFabsVisible) {
            if(APIClient.role.equals("lecturer")) {
                showExpandableFAB();
            }else{
                this.openAddPertemuan(view);
            }
        } else {
            hideExpandableFAB();
        }
    }

    private void openAddPertemuan(View view){
        this.tambahPertemuanFragment = TambahPertemuanFragment.newInstance(getParentFragmentManager(), this.pertemuanPresenter, this.mainPresenter);
    }

    private void openAddTimeSlot(View view) {
        this.tambahSlotWaktuFragment = TambahSlotWaktuFragment.newInstance(this.getParentFragmentManager(), this.pertemuanPresenter);
    }

    private void hideExpandableFAB() {
        this.binding.llExpandableFab.setVisibility(View.GONE);

        this.binding.tvLabelAddInvitasiPertemuan.setVisibility(View.GONE);
        this.binding.tvLabelAddTimeSlot.setVisibility(View.GONE);

        this.binding.btnAddInvitasiPertemuan.hide();
        this.binding.btnAddInvitasiPertemuan.setVisibility(View.GONE);
        this.binding.btnAddTimeSlot.hide();
        this.binding.btnAddTimeSlot.setVisibility(View.GONE);

        this.isFabsVisible = false;
    }

    private void showExpandableFAB() {
        this.binding.llExpandableFab.setVisibility(View.VISIBLE);

        this.binding.tvLabelAddInvitasiPertemuan.setVisibility(View.VISIBLE);
        this.binding.tvLabelAddTimeSlot.setVisibility(View.VISIBLE);

        this.binding.btnAddInvitasiPertemuan.show();
        this.binding.btnAddInvitasiPertemuan.setVisibility(View.VISIBLE);
        this.binding.btnAddTimeSlot.show();
        this.binding.btnAddTimeSlot.setVisibility(View.VISIBLE);

        this.isFabsVisible = true;
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
    public void addSelectedUserOnTambahPertemuan(User[] users) {
        this.tambahPertemuanFragment.addSelecteduser(users);
    }

    @Override
    public void updateTimeSlot(TimeslotList timeslotList) {
        if(this.tambahPertemuanFragment != null) {
            this.tambahPertemuanFragment.updateTimeSlot(timeslotList);
        }
        if(this.tambahSlotWaktuFragment != null) {
            this.tambahSlotWaktuFragment.updateTimeSlot(timeslotList);
        }
    }

    @Override
    public void openAddPartisipan(String idPertemuan) {
        this.tambahPertemuanFragment.openAddPartisipan(idPertemuan);
    }

    @Override
    public void closeAddPage() {
        this.tambahSlotWaktuFragment.dismiss();
    }

    @Override
    public void showErrorAddPertemuan(String msg) {
        this.tambahPertemuanFragment.showError(msg);
    }
}

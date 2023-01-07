package com.example.p3b_tubes_2.View;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.databinding.FragmentDetailPertemuanUndanganBinding;

public class PertemuanDetailUndanganFragment extends DialogFragment {

    private FragmentDetailPertemuanUndanganBinding binding;
    private InviteList.Invites invite;
    private PertemuanPresenter presenter;

    public PertemuanDetailUndanganFragment() {
    }

    public static PertemuanDetailUndanganFragment newInstance(FragmentManager fm, InviteList.Invites invite, PertemuanPresenter presenter) {
        PertemuanDetailUndanganFragment fragment = new PertemuanDetailUndanganFragment();
        fragment.invite = invite;
        fragment.presenter = presenter;
        fragment.show(fm, "openAppointmentInvitationDetail");

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.binding = FragmentDetailPertemuanUndanganBinding.inflate(inflater);

        this.binding.tvTitle.setText(this.invite.getTitle());
        this.binding.tvOrganizer.setText(this.invite.getOrganizerName());
        this.binding.tvTanggal.setText(this.invite.getDate());
        this.binding.tvWaktuMulai.setText(this.invite.getStartTime());
        this.binding.tvWaktuSelesai.setText(this.invite.getEndTime());
        this.binding.tvDeskripsi.setText(this.invite.getDescription());

        this.binding.btnAccept.setOnClickListener(this::acceptInvitation);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
        this.binding.btnAccept.setOnClickListener(this::acceptInvitation);
    }

    private void acceptInvitation(View view) {
        this.presenter.acceptInvitation(this.invite.getAppointment_id());
    }

    private void onClickKembali(View view) {
        dismiss();
    }

    public void showError(String msg) {
        this.binding.tvError.setText(msg);
    }
}

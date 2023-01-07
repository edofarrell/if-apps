package com.example.p3b_tubes_2.View;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.databinding.FragmentAddPertemuanBinding;
import com.google.android.material.chip.ChipGroup;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


public class TambahPertemuanFragment extends DialogFragment {
    private FragmentAddPertemuanBinding binding;
    private PertemuanPresenter pertemuanPresenter;
    private MainPresenter mainPresenter;
    private TambahPartisipanFragment tambahPartisipanFragment;
    private TambahDetailPertemuanFragment tambahDetailPertemuanFragment;
    private ChipGroup chipGroup;
    private ArrayList<User> arrChipGroup;

    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;

    public static TambahPertemuanFragment newInstance(FragmentManager fm, PertemuanPresenter pertemuanPresenter, MainPresenter mainPresenter) {
        TambahPertemuanFragment fragment = new TambahPertemuanFragment();
        fragment.show(fm, "tambahPertemuan");
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.mainPresenter = mainPresenter;

        fragment.fragments = new HashMap<>();
        fragment.tambahDetailPertemuanFragment = TambahDetailPertemuanFragment.newInstance(mainPresenter, pertemuanPresenter);
        fragment.fragments.put("isiDetail", fragment.tambahDetailPertemuanFragment);
//        fragment.fragments.put("pilihPartisipan", TambahPartisipanPertemuanFragment.newInstance(mainPresenter, pertemuanPresenter));
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

        binding = FragmentAddPertemuanBinding.inflate(inflater);

        this.fm = getChildFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(this.binding.fragmentContainer.getId(), this.fragments.get("isiDetail"))
                .commit();

        this.fm.setFragmentResultListener("closeDialog", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.d("DEBUG", "close dialog");
                dismiss();
            }
        });

        this.fm.setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String page = result.getString("page");
                changePage(page);
            }
        });

        return binding.getRoot();
    }

    private void changePage(String page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment intendedFragment = fragments.get(page);
        if (intendedFragment.isAdded()) {
            ft.show(intendedFragment);
        } else {
            ft.add(this.binding.fragmentContainer.getId(), intendedFragment);
        }

        String key;
        Fragment unintendedFragment;
        for (Map.Entry<String, Fragment> set : this.fragments.entrySet()) {
            key = set.getKey();
            if (!key.equals(page)) {
                unintendedFragment = this.fragments.get(key);
                if (unintendedFragment.isAdded()) {
                    ft.hide(unintendedFragment);
                }
            }
        }
        ft.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appbar.setNavigationOnClickListener(this::closeDialogFragment);
    }

    private void closeDialogFragment(View view) {
        dismiss();
    }

    public void addSelecteduser(User[] users) {
        TambahPartisipanPertemuanFragment fragment = (TambahPartisipanPertemuanFragment) this.fragments.get("pilihPartisipan");
        fragment.addSelectedUser(users);
    }

    public void updateTimeSlot(TimeslotList timeslotList) {
        TambahPartisipanPertemuanFragment fragment = (TambahPartisipanPertemuanFragment) this.fragments.get("pilihPartisipan");
        fragment.updateTimeSlot(timeslotList);
    }

    public void openAddPartisipan(String idPertemuan){
        TambahPartisipanPertemuanFragment fragment = TambahPartisipanPertemuanFragment.newInstance(mainPresenter, pertemuanPresenter, idPertemuan);
        this.fragments.put("pilihPartisipan", fragment);
        this.changePage("pilihPartisipan");
    }

    public void showError(String msg) {
        this.tambahDetailPertemuanFragment.showError(msg);
    }
}

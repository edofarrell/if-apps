package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PertemuanFragment extends Fragment implements
        PertemuanContract.View
{
    private FragmentPertemuanBinding binding;
    private PertemuanPresenter presenter;
    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;
    private PertemuanDibuatFragment pertemuanDibuatFragment;
    private PertemuanDiundangFragment pertemuanDiundangFragment;

    private PertemuanFragment() {}

    public static PertemuanFragment newInstance(MainPresenter mainPresenter, Context context, FrameLayout frameLayout) {
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = new PertemuanPresenter(fragment, context, mainPresenter);
        fragment.fragments = new HashMap<>();
        fragment.pertemuanDibuatFragment = PertemuanDibuatFragment.newInstance(fragment.presenter, mainPresenter);
        fragment.pertemuanDiundangFragment = PertemuanDiundangFragment.newInstance(fragment.presenter, frameLayout);
        fragment.fragments.put("pertemuanDibuat", fragment.pertemuanDibuatFragment);
        fragment.fragments.put("pertemuanDiundang", fragment.pertemuanDiundangFragment);
        fragment.presenter.setUiDibuat(fragment.pertemuanDibuatFragment);
        fragment.presenter.setUiDiundang(fragment.pertemuanDiundangFragment);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPertemuanBinding.inflate(inflater);

        this.fm = getChildFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(this.binding.fragmentContainer.getId(), this.fragments.get("pertemuanDibuat"))
                .commit();

        this.binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                String page = "";
                switch (tabPosition) {
                    case 0:
                        page = "pertemuanDibuat";
                        break;
                    case 1:
                        page = "pertemuanDiundang";
                        break;
                }
                Log.d("DEBUG", page);

                changePage(page);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        this.fm.setFragmentResultListener("backPertemuan", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String page = result.getString("page");
                changePage(page);
            }
        });

        return this.binding.getRoot();
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
    public void updateDibuat(PertemuanList pertemuanList) {
        this.pertemuanDibuatFragment.updatePertemuanDibuat(pertemuanList);
    }

    @Override
    public void updateDiundang(InviteList listInvites) {
        this.pertemuanDiundangFragment.updatePertemuanDiundang(listInvites);
    }

    @Override
    public void updateTimeSlot(List<TimeSlot> timeSlot) {
        this.pertemuanDibuatFragment.updateTimeSlot(timeSlot);
    }
}

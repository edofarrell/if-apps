package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;

public class PertemuanFragment extends Fragment {
    private FragmentPertemuanBinding binding;
    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;

    public PertemuanFragment() {
    }

    public static PertemuanFragment newInstance() {
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.fragments = new HashMap<>();
        fragment.fragments.put("pertemuanDibuat", PertemuanDibuatFragment.newInstance());
        fragment.fragments.put("pertemuanDiundang", PertemuanDiundangFragment.newInstance());
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
}

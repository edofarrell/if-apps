package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentHomeBinding;
import com.example.p3b_tubes_2.databinding.FragmentLoginBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);

        this.fragmentHomeBinding.btnHome.setOnClickListener(this::onClick);
        this.fragmentHomeBinding.btnFrs.setOnClickListener(this::onClick);
        this.fragmentHomeBinding.btnPertemuan.setOnClickListener(this::onClick);
        this.fragmentHomeBinding.btnPengumuman.setOnClickListener(this::onClick);
        this.fragmentHomeBinding.btnKeluar.setOnClickListener(this::onClick);

        return fragmentHomeBinding.getRoot();
    }

    private void onClick(View view) {
        Bundle result = new Bundle();
        String page;
        if(view == this.fragmentHomeBinding.btnHome) {
            page = "home";
        } else if (view == this.fragmentHomeBinding.btnFrs) {
            page = "frs";
        } else if (view == this.fragmentHomeBinding.btnPertemuan) {
            page = "pertemuan";
        } else if (view == this.fragmentHomeBinding.btnPengumuman) {
            page = "pengumuman";
        } else {
            page = "exit";
        }
        result.putString("page",page);
        getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

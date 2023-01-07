package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentLeftBinding;

public class LeftFragment extends Fragment {
    FragmentLeftBinding binding;

    public static LeftFragment newInstance() {

        Bundle args = new Bundle();

        LeftFragment fragment = new LeftFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLeftBinding.inflate(inflater);
        binding.llHome.setOnClickListener(this::onClickHome);
        binding.llPengumuman.setOnClickListener(this::onClickPengumuman);
        binding.llPertemuan.setOnClickListener(this::onCLickPertemuan);
        binding.llFrs.setOnClickListener(this::onClickFRS);
        binding.llLogout.setOnClickListener(this::onClickLogOut);


        return binding.getRoot();
    }

    public void onClickHome(View view){
        Bundle result = new Bundle();
        result.putString("page", "home");
        this.getParentFragmentManager().setFragmentResult("changePage", result);

    }

    public void onClickPengumuman(View view){
        Bundle result = new Bundle();
        result.putString("page", "pengumuman");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }

    public void onCLickPertemuan(View view){
        Bundle result = new Bundle();
        result.putString("page", "pertemuan");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }

    public void onClickFRS(View view){
        Bundle result = new Bundle();
        result.putString("page", "frs");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }

    public void onClickLogOut(View view){
        Bundle result = new Bundle();
        result.putString("page", "login");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }


}

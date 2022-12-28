package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentFrsBinding;
import com.example.p3b_tubes_2.databinding.FragmentFrsDetailBinding;

public class FRSFragment extends Fragment implements FRSContract.View{
    private FragmentFrsBinding binding;
    private FRSPresenter presenter;
    private FRSFragment(){}
    public static FRSFragment newInstance(MainPresenter mainPresenter,Context context) {
        Bundle args = new Bundle();
        FRSFragment fragment = new FRSFragment();
        fragment.presenter = new FRSPresenter(fragment,context, mainPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentFrsBinding.inflate(inflater);
        return binding.getRoot();
    }

    private void test(View view) {
        this.presenter.getAcademicYears();
    }
}

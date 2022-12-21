package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;

import org.json.JSONException;

public class PertemuanFragment extends Fragment implements PertemuanContract.View{
    private FragmentPertemuanBinding binding;
    private PertemuanPresenter presenter;

    private PertemuanFragment(){}

    public static PertemuanFragment newInstance(MainPresenter mainPresenter, Context context){
        Bundle args = new Bundle();
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = new PertemuanPresenter(fragment,context,mainPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPertemuanBinding.inflate(inflater);
        binding.btnTest.setOnClickListener(this::tambah);
        return binding.getRoot();
    }

    private void tambah(View view) {
        try {
            presenter.addPertemuan("Dearen Test Api","Test Api Android Studio",
                    "2022-12-24 22:00+0700","2022-12-24 22:00+0700");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String id) {
        binding.tvTitle.setText(id);
    }
}

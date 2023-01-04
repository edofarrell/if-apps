package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.databinding.FragmentFrsDetailBinding;

import java.util.ArrayList;

public class FRSDetailFragment extends DialogFragment {
    private FragmentFrsDetailBinding binding;
    private String tahunAjar;
    private ArrayList<MataKuliahList.MataKuliah> listMataKuliah;
    private FRSDetailListAdapterSearch adapterSearch;
    private FRSPresenter presenter;
    private FRSTambahFragment frsTambahFragmentfragment;
    private String searchText;

    public static FRSDetailFragment newInstance(FragmentManager fm, String tahunAjar,
                                                ArrayList<MataKuliahList.MataKuliah> listMataKuliah,
                                                FRSPresenter presenter) {

        Bundle args = new Bundle();

        FRSDetailFragment fragment = new FRSDetailFragment();
        fragment.setArguments(args);
        fragment.show(fm, "detailFRS");
        fragment.tahunAjar = tahunAjar;
        fragment.listMataKuliah = listMataKuliah;
        fragment.presenter = presenter;
        fragment.adapterSearch = new FRSDetailListAdapterSearch(presenter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentFrsDetailBinding.inflate(inflater);
        this.binding.appbar.setTitle(tahunAjar);
        this.binding.tvLvMatkul.setAdapter(adapterSearch);
        //this.adapter.update(listMataKuliah);
        this.binding.btnAddMatkul.setOnClickListener(this::onClickAddMatkul);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = this.getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SearchView searchView = this.binding.searchBar;
        searchView.setActivated(true);
        searchView.setOnClickListener(this::onClickSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                //filter();
                return false;
            }
        });
    }

    private void onClickSearch(View view) {
        this.binding.searchBar.onActionViewExpanded();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.appbar.setNavigationOnClickListener(this::onClickKembali);
    }

    private void onClickKembali(View view) {
        this.dismiss();
    }

    private void onClickAddMatkul(View view) {
        this.frsTambahFragmentfragment = FRSTambahFragment.newInstance(getParentFragmentManager());
    }
}

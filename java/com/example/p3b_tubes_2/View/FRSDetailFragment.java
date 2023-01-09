package com.example.p3b_tubes_2.View;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.Model.MataKuliahList;
import com.example.p3b_tubes_2.Model.TahunAjaran;
import com.example.p3b_tubes_2.Presenter.FRSPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.databinding.FragmentFrsDetailBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class FRSDetailFragment extends DialogFragment{
    private FragmentFrsDetailBinding binding;
    private TahunAjaran.TahunAjar tahunAjar;
    private TahunAjaran.TahunAjar activeYear;
    private ArrayList<MataKuliahList.MataKuliah> listMataKuliah;
    private FRSDetailListAdapterSearch adapterSearch;
    private FRSDetailListAdapterView adapterView;
    private FRSPresenter presenter;
    private FRSTambahFragment frsTambahFragmentfragment;
    private String searchText;
    private LayoutInflater inflater;

    public static FRSDetailFragment newInstance(FragmentManager fm,
                                                FRSPresenter presenter,
                                                TahunAjaran.TahunAjar tahunAjar,
                                                TahunAjaran.TahunAjar activeYear) {

        Bundle args = new Bundle();

        FRSDetailFragment fragment = new FRSDetailFragment();
        fragment.setArguments(args);
        fragment.show(fm, "detailFRS");
        fragment.presenter = presenter;
        fragment.adapterSearch = new FRSDetailListAdapterSearch(presenter);
        fragment.adapterView = new FRSDetailListAdapterView(presenter);
        fragment.tahunAjar = tahunAjar;
        fragment.activeYear = activeYear;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentFrsDetailBinding.inflate(inflater);
        this.binding.appbar.setTitle(tahunAjar.toString());
        this.binding.tvLvMatkul.setAdapter(adapterSearch);
        this.binding.tvLvMatkuldipilih.setAdapter(adapterView);
        this.inflater = inflater;
        this.binding.btnAddMatkul.setOnClickListener(this::onClickAddMatkul);
        this.binding.tvTitleError.setVisibility(View.GONE);
        if(!this.tahunAjar.toStringFormatAPI().equals(this.activeYear.toStringFormatAPI())){
            binding.searchBar.setVisibility(View.GONE);
            binding.btnAddMatkul.setVisibility(View.GONE);
            binding.tvHasilPencarianMatkul.setText("Mata Kuliah yang dipilih");
            binding.tvMatkulTerpilih.setVisibility(View.GONE);
            binding.tvLvMatkuldipilih.setVisibility(View.GONE);
            binding.tvInfoPenting.setVisibility(View.GONE);
            getMataKuliahEnrolment();
        }
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
                getMataKuliah(newText);
                return false;
            }
        });
    }

    public void showErrorMataKuliahEnrolment(String nama, String kode){
        this.binding.tvTitleError.setVisibility(View.VISIBLE);
        this.binding.tvNamaMatkulError.setText(nama);
        this.binding.tvKodeMatkulError.setText(kode);
    }

    public void showToastSuccessStudentEnrolment(){
        Toast.makeText(getActivity(),"Enrolment Berhasil",Toast.LENGTH_SHORT).show();
    }

    public void getMataKuliah(String text){
        this.presenter.getMataKuliah(text);
    }

    private void onClickSearch(View view) {
        this.binding.searchBar.onActionViewExpanded();
    }

    public void setListMataKuliah(ArrayList<MataKuliahList.MataKuliah> listMataKuliah) {
        this.listMataKuliah = listMataKuliah;
        this.adapterSearch.update(listMataKuliah);
    }

    public void setSelectedMataKuliah(MataKuliahList.MataKuliah matkul){
        this.adapterView.update(matkul);
    }

    public void setMataKuliahEnrolment(ArrayList<MataKuliahList.MataKuliah> listNamaMatkul){
        this.adapterSearch.update(listNamaMatkul);
    }

    public void getMataKuliahEnrolment(){
        try {
            presenter.getMataKuliahEnrolment(this.tahunAjar.toStringFormatAPI());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        for(int i = 0;i<this.adapterView.getCount();i++){
            try {
                this.presenter.enrolStudent(this.adapterView.getMataKuliahEnrol(i).getId(),this.tahunAjar.toStringFormatAPI());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

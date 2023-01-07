package com.example.p3b_tubes_2;



import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

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
        //fragment.listMataKuliah = listMataKuliah;
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
        if(!this.tahunAjar.toStringFormatAPI().equals(this.activeYear.toStringFormatAPI())){
            binding.searchBar.setVisibility(View.GONE);
            binding.btnAddMatkul.setVisibility(View.GONE);
            binding.tvLvMatkuldipilih.setVisibility(View.GONE);
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

    public void setMataKuliahEnrolment(ArrayList<String> listNamaMatkul){
        ArrayList<MataKuliahList.MataKuliah> listMatkul = new ArrayList<>();
        for(int i = 0;i<listNamaMatkul.size();i++){
            listMatkul.get(i).setName(listNamaMatkul.get(i));
        }
        this.adapterSearch.update(listMatkul);
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
        //this.frsTambahFragmentfragment = FRSTambahFragment.newInstance(getParentFragmentManager());
        for(int i = 0;i<this.adapterView.getCount();i++){
            try {
                this.presenter.enrolStudent(this.adapterView.getMataKuliahEnrol(i).getId(),this.tahunAjar.toStringFormatAPI());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        View popupView = this.inflater.inflate(R.layout.popup_prasyarat_matkul, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


}

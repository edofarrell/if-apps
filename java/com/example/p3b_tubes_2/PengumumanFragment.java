package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPengumumanBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class PengumumanFragment extends Fragment implements PengumumanContract.View {

    private FragmentPengumumanBinding binding;
    private PengumumanPresenter presenter;
    private PengumumanListAdapter adapter;
    private FrameLayout frameLayout;
    private List<String> arrChipGroup;
    private ChipGroup chipGroup;
    private String searchText;
    private PengumumanTambahFragment tambahFragment;

    private boolean isFabsVisible;

    private PengumumanFragment() {
    }

    public static PengumumanFragment newInstance(MainPresenter mainPresenter, Context context, FrameLayout frameLayout) {
        PengumumanFragment fragment = new PengumumanFragment();
        fragment.presenter = new PengumumanPresenter(fragment, context, mainPresenter);
        fragment.adapter = new PengumumanListAdapter(fragment.presenter, context.getSharedPreferences("sp_read_status", 0));
        fragment.frameLayout = frameLayout;
        fragment.arrChipGroup = new ArrayList<>();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPengumumanBinding.inflate(inflater);

        hideExpandableFAB();

        this.binding.lvPengumuman.setAdapter(this.adapter);
        this.presenter.getPengumuman();

        this.binding.btnAddPengumuman.setOnClickListener(this::onClick);
        this.binding.btnAddPengumumanExpandable.setOnClickListener(this::addPengumuman);
        this.binding.btnAddTagPengumuman.setOnClickListener(this::addTag);

        this.binding.ivFilter.setOnClickListener(this::openFilter);

        this.chipGroup = this.binding.chipGrup;

        this.searchText = "";
        this.binding.btnNext.setOnClickListener(this::onClickNext);
        this.binding.btnBack.setOnClickListener(this::onClickBack);

        if(!APIClient.role.equals("admin")) {
            this.binding.btnAddPengumuman.setVisibility(View.GONE);
        }

        return this.binding.getRoot();
    }

    private void addTag(View view) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                filter();
                return false;
            }
        });
    }

    public void onClickSearch(View view){
        this.binding.searchBar.onActionViewExpanded();
    }


    private void onClickBack(View view) {
        filter();
    }

    private void onClickNext(View view) {
        List<String> tag = this.presenter.getTagsId(this.arrChipGroup);
        this.presenter.getPengumuman(this.searchText, tag, true);
    }

    private void onClick(View view) {
        if(!this.isFabsVisible) {
            showExpandableFAB();
        } else {
            hideExpandableFAB();
        }
    }

    private void addPengumuman(View view) {
        this.tambahFragment = PengumumanTambahFragment.newInstance(getParentFragmentManager(), this.presenter);
    }

    private void hideExpandableFAB() {
        this.binding.llExpandableFab.setVisibility(View.GONE);

        this.binding.tvLabelAddPengumuman.setVisibility(View.GONE);
        this.binding.tvLabelAddTagPengumuman.setVisibility(View.GONE);

        this.binding.btnAddPengumumanExpandable.hide();
        this.binding.btnAddPengumumanExpandable.setVisibility(View.GONE);
        this.binding.btnAddTagPengumuman.hide();
        this.binding.btnAddTagPengumuman.setVisibility(View.GONE);

        this.isFabsVisible = false;
    }

    private void showExpandableFAB() {
        this.binding.llExpandableFab.setVisibility(View.VISIBLE);

        this.binding.tvLabelAddPengumuman.setVisibility(View.VISIBLE);
        this.binding.tvLabelAddTagPengumuman.setVisibility(View.VISIBLE);

        this.binding.btnAddPengumumanExpandable.show();
        this.binding.btnAddPengumumanExpandable.setVisibility(View.VISIBLE);
        this.binding.btnAddTagPengumuman.show();
        this.binding.btnAddTagPengumuman.setVisibility(View.VISIBLE);

        this.isFabsVisible = true;
    }

    public void openFilter(View view) {
        presenter.getTag();
    }

    public void filter() {
        List<String> tag = this.presenter.getTagsId(this.arrChipGroup);
        this.presenter.getPengumuman(this.searchText, tag, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = (String) item.getTitle();
        Chip chip = new Chip(getContext());
        chip.setText(text);

        chip.setCloseIconVisible(true);
        chip.setTextColor(getResources().getColor(R.color.black));
        chip.setOnCloseIconClickListener(this::OnCloseIconClick);

        boolean check = false;
        for (int i = 0; i < arrChipGroup.size(); i++) {
            if (arrChipGroup.contains(item.getTitle().toString())) {
                check = true;
                break;
            }
        }
        if (!check) {
            this.chipGroup.addView(chip);
        }
        this.arrChipGroup.add(item.getTitle().toString());
        filter();
        return super.onOptionsItemSelected(item);
    }

    public boolean OnCloseIconClick(View view) {
        Chip chip = (Chip) view;
        this.chipGroup.removeView(view);
        this.arrChipGroup.remove(chip.getText().toString());
        filter();
        return true;
    }

    @Override
    public void updatePengumumanList(PengumumanList pengumumanList) {
        this.adapter.update(pengumumanList);
    }

    @Override
    public void updateListTag(ArrayList<TagList.Tag> listTag) {

        PopupMenu popupMenu = new PopupMenu(getContext(), this.binding.ivFilter);

        if (this.tambahFragment != null && this.tambahFragment.isVisible()) {
            this.tambahFragment.updateTag(listTag);
        } else {

            for (int i = 0; i < listTag.size(); i++) {
                popupMenu.getMenu().add(1, i + 1, i + 1, listTag.get(i).getName());
            }

            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(this::onOptionsItemSelected);
        }
    }

    @Override
    public void openDetail(PengumumanList.Pengumuman pengumuman) {
        PengumumanDetailFragment.newInstance(getParentFragmentManager(), pengumuman);
    }

    @Override
    public void closeAddPage() {
        this.tambahFragment.dismiss();
    }

    @Override
    public void showErrorAddPengumuman(String msg) {
        this.tambahFragment.showError(msg);
    }
}

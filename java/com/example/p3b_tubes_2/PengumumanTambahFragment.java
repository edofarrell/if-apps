package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentTambahPengumumanBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class PengumumanTambahFragment extends Fragment {

    private FragmentTambahPengumumanBinding binding;
    private PengumumanPresenter presenter;
    private ChipGroup chipGroup;
    private ArrayList<String> arrChipGroup;

    private PengumumanTambahFragment() {}

    public static PengumumanTambahFragment newInstance(PengumumanPresenter presenter) {
        PengumumanTambahFragment fragment = new PengumumanTambahFragment();
        fragment.presenter = presenter;
        fragment.arrChipGroup = new ArrayList<>();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentTambahPengumumanBinding.inflate(inflater);
        this.chipGroup = binding.chipGrup;

        this.binding.btnSimpan.setOnClickListener(this::OnClickSimpan);
        this.binding.btnPilih.setOnClickListener(this::tags);

        return this.binding.getRoot();
    }

    private void tags(View view) {
        presenter.getTag();
    }

    private void OnClickSimpan(View view) {
        String title = this.binding.etJudul.getText().toString();
        String content = this.binding.etDeskripsi.getText().toString();
        List<String> listTag = presenter.getTagsId(arrChipGroup);
        String[] tags = new String[arrChipGroup.size()];
        for(int i = 0;i<listTag.size();i++){
            tags[i] = listTag.get(i);
        }
        this.presenter.addPengumuman(title, content, tags);
    }



    public void updateTag(ArrayList<TagList.Tag> listTag) {
        Log.d("DEBUG","updatelisttag");
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.btnPilih);
        //Log.d("DEBUG","updatelisttag");
        //nambah pop up menu (nanti diisi dari yg api tag tag nya)
        for (int i = 0; i < listTag.size(); i++) {
            popupMenu.getMenu().add(1, i + 1, i + 1, listTag.get(i).getName());
        }

        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(this::onOptionsItemSelected);
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
            chipGroup.addView(chip);
        }
        arrChipGroup.add(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    private boolean OnCloseIconClick(View view) {
        Chip chip = (Chip) view;
        chipGroup.removeView(view);
        arrChipGroup.remove(chip.getText().toString());
        return true;
    }
}

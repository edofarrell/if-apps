package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentPengumumanBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class PengumumanFragment extends Fragment implements PengumumanContract.View {

    private FragmentPengumumanBinding binding;
    private PengumumanPresenter presenter;
    private PengumumanListAdapter adapter;
    private ChipGroup chipGroup;

    private PengumumanFragment() {
    }

    public static PengumumanFragment newInstance(MainPresenter mainPresenter, Context context) {
        Bundle args = new Bundle();
        PengumumanFragment fragment = new PengumumanFragment();
        fragment.presenter = new PengumumanPresenter(fragment, context, mainPresenter);
        fragment.adapter = new PengumumanListAdapter(fragment.presenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPengumumanBinding.inflate(inflater);

        this.binding.lvPengumuman.setAdapter(this.adapter);
        this.presenter.getPengumuman();

        binding.ivFilter.setOnClickListener(this::onClick);
        chipGroup = binding.chipGrup;
        return this.binding.getRoot();
    }


    public void onClick(View view) {
        if (view == binding.ivFilter) {
            PopupMenu popupMenu = new PopupMenu(getContext(), binding.ivFilter);

            //nambah pop up menu (nanti diisi dari yg api tag tag nya)
            popupMenu.getMenu().add(1, 1, 1, "slot1");
            popupMenu.getMenu().add(1, 2, 2, "slot2");
            popupMenu.getMenu().add(1, 3, 3, "slot3");
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(this::onOptionsItemSelected);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        CharSequence idS = item.getTitle();
        String text = (String) item.getTitle();
        Chip chip = new Chip(getContext());
        chip.setText(text);

        chip.setCloseIconVisible(true);
        chip.setTextColor(getResources().getColor(R.color.black));
        chip.setOnCloseIconClickListener(this::OnCloseIconClick);


        chipGroup.addView(chip);
        return super.onOptionsItemSelected(item);
    }

    public boolean OnCloseIconClick(View view){
        Chip chip = (Chip) view;
        chipGroup.removeView(view);
        return true;
    }

    @Override
    public void update(PengumumanList pengumumanList) {
        this.adapter.update(pengumumanList);
    }
}

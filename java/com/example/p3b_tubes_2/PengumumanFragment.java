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
    private ChipGroup chipGroup;
    private FrameLayout frameLayout;

    private PengumumanFragment() {}

    public static PengumumanFragment newInstance(MainPresenter mainPresenter, Context context, FrameLayout frameLayout) {
        Bundle args = new Bundle();
        PengumumanFragment fragment = new PengumumanFragment();
        fragment.presenter = new PengumumanPresenter(fragment, context, mainPresenter);
        fragment.adapter = new PengumumanListAdapter(fragment.presenter);
        fragment.frameLayout = frameLayout;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPengumumanBinding.inflate(inflater);

        this.binding.lvPengumuman.setAdapter(this.adapter);
        this.presenter.getPengumuman();

        this.binding.btnAddPengumuman.setOnClickListener(this::OnClickAddPengumuman);

        binding.ivFilter.setOnClickListener(this::onClick);
        binding.tvPengumuman.setOnClickListener(this::test);//dipake untuk test api saja
        chipGroup = binding.chipGrup;
        return this.binding.getRoot();
    }

    private void OnClickAddPengumuman(View view) {
        PengumumanTambahFragment pengumumanTambahFragment = PengumumanTambahFragment.newInstance(this.presenter);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), pengumumanTambahFragment)
                .addToBackStack(null)
                .commit();
    }

    private void test(View view) {
        this.presenter.deletePengumuman("b90bce6d-a8d7-4777-a49b-0a63d6bfef02");
    }


    public void onClick(View view) {
        presenter.getTag();
        /*if (view == binding.ivFilter) {
            PopupMenu popupMenu = new PopupMenu(getContext(), binding.ivFilter);

            //nambah pop up menu (nanti diisi dari yg api tag tag nya)
            popupMenu.getMenu().add(1, 1, 1, "slot1");
            popupMenu.getMenu().add(1, 2, 2, "slot2");
            popupMenu.getMenu().add(1, 3, 3, "slot3");
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(this::onOptionsItemSelected);
        }*/
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

        List<Integer> list = chipGroup.getCheckedChipIds();
        boolean check = false;
        for(int i = 0;i<list.size();i++){
            Log.d("DEBUG",list.get(i)+"");
            if(list.get(i)==id){
                check = true;
                break;
            }
        }
        if(!check){
            chipGroup.addView(chip);
        }
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

    public void updateListTag(ArrayList<TagList.Tag> listTag) {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.ivFilter);

        //nambah pop up menu (nanti diisi dari yg api tag tag nya)
        popupMenu.getMenu().add(1, 1, 1, listTag.get(0).getName());
        //popupMenu.getMenu().add(1, 2, 2, listTag.get(1).getNama());
        //popupMenu.getMenu().add(1, 3, 3, listTag.get(2).getNama());
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(this::onOptionsItemSelected);

    }

    @Override
    public void openDetail(PengumumanList.Pengumuman pengumuman) {
        PengumumanDetailFragment pengumumanDetailFragment = PengumumanDetailFragment.newInstance(pengumuman);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), pengumumanDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void closeAddPage() {
        Bundle result = new Bundle();
        result.putString("page", "pengumuman");
        getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

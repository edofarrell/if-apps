package com.example.p3b_tubes_2.View;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.AutocompleteAdapter;
import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.UserContract;
import com.example.p3b_tubes_2.databinding.FragmentTambahPartisipanPertemuanBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class TambahPartisipanPertemuanFragment extends Fragment implements UserContract.View {
    private FragmentTambahPartisipanPertemuanBinding binding;
    private MainPresenter mainPresenter;
    private PertemuanPresenter pertemuanPresenter;

    private User selectedUser;
    private TimeslotListAdapter timeSlotAdapter;
    private ChipGroup chipGroup;
    private ArrayList<User> arrChipGroup;

    private String idPertemuan;

    public static TambahPartisipanPertemuanFragment newInstance(MainPresenter mainPresenter, PertemuanPresenter pertemuanPresenter, String idPertemuan) {
        TambahPartisipanPertemuanFragment fragment = new TambahPartisipanPertemuanFragment();
        fragment.mainPresenter = mainPresenter;
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.timeSlotAdapter = new TimeslotListAdapter();
        fragment.arrChipGroup = new ArrayList<>();
        fragment.idPertemuan = idPertemuan;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentTambahPartisipanPertemuanBinding.inflate(inflater);

        this.timeSlotAdapter = new TimeslotListAdapter();
        this.binding.lstTimeSlot.setAdapter(this.timeSlotAdapter);
        this.chipGroup = binding.chipGroup;

        LinearLayout layoutJadwal = binding.llJadwalDosen;
        layoutJadwal.setVisibility(View.GONE);

        this.binding.btnSimpan.setOnClickListener(this::closePage);

        this.mainPresenter.getAllUser(this);

        return this.binding.getRoot();
    }

    private void closePage(View view) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.actvChooseParticipant.setOnItemClickListener(onItemClick());
        this.binding.btnAddParticipant.setOnClickListener(this::simpanPartisipan);
        this.binding.btnSimpan.setOnClickListener(this::saveAll);
    }

    private void saveAll(View view) {
        this.pertemuanPresenter.getPertemuanDibuat();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
                if (selectedUser.isDosen()) {
                    binding.llJadwalDosen.setVisibility(View.VISIBLE);
                    pertemuanPresenter.getTimeSlot(selectedUser.getId());
                } else {
                    binding.llJadwalDosen.setVisibility(View.GONE);
                }
            }
        };
    }

    private void simpanPartisipan(View view) {
        this.pertemuanPresenter.addUserToPertemuan(new User[]{this.selectedUser}, this.idPertemuan);
    }

    private void back(View view) {
        Bundle result = new Bundle();
        result.putString("page", "isiDetail");

        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }

    public void addSelectedUser(User[] users) {
        for (User user : users) {
            String nama = user.getName();
            Chip chip = new Chip(this.getContext());
            chip.setText(nama);

            chip.setCloseIconVisible(true);
            chip.setTextColor(getResources().getColor(R.color.black));
            chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            chip.setOnCloseIconClickListener(this::removeChip);

            if (arrChipGroup.size() == 0) {
                addChip(chip, user);
            }

            boolean isChipAdded = false;
            for (User item : arrChipGroup) {
                if (item.getName().contains(nama)) {
                    isChipAdded = true;
                    break;
                }
            }

            if (!isChipAdded) {
                addChip(chip, user);
            }
        }
    }

    private void addChip(Chip chip, User user) {
        chipGroup.addView(chip);
        arrChipGroup.add(user);
    }

    private void removeChip(View view) {
        User removedUser = null;
        Chip chip = (Chip) view;

        String nama = chip.getText().toString();
        for (User item : arrChipGroup) {
            if (item.getName().contains(nama)) {
                removedUser = item;
                arrChipGroup.remove(item);
                break;
            }
        }

        this.pertemuanPresenter.deleteParticipantsPertemuan(new User[]{removedUser}, this.idPertemuan);
        this.chipGroup.removeView(view);
    }

    public void updateTimeSlot(TimeslotList timeslotList) {
        this.timeSlotAdapter.update(timeslotList);
    }

    @Override
    public void update(List<User> data) {
        AutocompleteAdapter autocompleteAdapter = new AutocompleteAdapter(getContext(), 0, data);
        autocompleteAdapter.getFilter().filter("");
        AutoCompleteTextView autoCompleteTextView = binding.actvChooseParticipant;
        autoCompleteTextView.setAdapter(autocompleteAdapter);
    }
}

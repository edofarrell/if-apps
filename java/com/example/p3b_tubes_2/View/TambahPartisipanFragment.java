package com.example.p3b_tubes_2.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.AutocompleteAdapter;
import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.UserContract;
import com.example.p3b_tubes_2.databinding.FragmentTambahPartisipanBinding;

import java.util.List;

public class TambahPartisipanFragment extends DialogFragment implements
        UserContract.View
{
    private FragmentTambahPartisipanBinding binding;
    private PertemuanPresenter pertemuanPresenter;
    private MainPresenter mainPresenter;
    private User selectedUser;
    private TimeslotListAdapter timeSlotAdapter;

    public static TambahPartisipanFragment newInstance(FragmentManager fm, PertemuanPresenter pertemuanPresenter, MainPresenter mainPresenter) {
        TambahPartisipanFragment fragment = new TambahPartisipanFragment();
        fragment.show(fm, "tambahPartisipan");
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.mainPresenter = mainPresenter;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.binding = FragmentTambahPartisipanBinding.inflate(inflater);
        this.timeSlotAdapter = new TimeslotListAdapter();
        this.binding.lstTimeSlot.setAdapter(this.timeSlotAdapter);

        LinearLayout layoutJadwal = binding.llJadwalDosen;
        layoutJadwal.setVisibility(View.GONE);

        this.mainPresenter.getAllUser(this);

        this.binding.btnAdd.setOnClickListener(this::simpanPartisipan);

        return this.binding.getRoot();
    }

    private void simpanPartisipan(View view) {
        dismiss();
//        this.pertemuanPresenter.addUserToPertemuan(new String[]{this.selectedUser.getId()},"");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appbar.setNavigationOnClickListener(this::closeDialogFragment);
        binding.btnCancel.setOnClickListener(this::closeDialogFragment);
        binding.actvChooseParticipant.setOnItemClickListener(onItemClick());
    }

    private void closeDialogFragment(View view) {
        this.dismiss();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
                if(selectedItemIsDosen(selectedUser)){
                    binding.llJadwalDosen.setVisibility(View.VISIBLE);
                    pertemuanPresenter.getTimeSlot(selectedUser.getId());
                }else{
                    binding.llJadwalDosen.setVisibility(View.GONE);
                }
            }
        };
    }

    private boolean selectedItemIsDosen(User user) {
        return user.isDosen();
    }

    @Override
    public void update(List<User> data) {
        AutocompleteAdapter autocompleteAdapter = new AutocompleteAdapter(getContext(), 0, data);
        autocompleteAdapter.getFilter().filter("");
        AutoCompleteTextView autoCompleteTextView = binding.actvChooseParticipant;
        autoCompleteTextView.setAdapter(autocompleteAdapter);
    }

    public void updateTimeSlot(TimeslotList timeslotList){
        this.timeSlotAdapter.update(timeslotList);
    }
}

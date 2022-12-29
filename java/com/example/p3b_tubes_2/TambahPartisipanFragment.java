package com.example.p3b_tubes_2;

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

import com.example.p3b_tubes_2.databinding.FragmentTambahPartisipanBinding;

public class TambahPartisipanFragment extends DialogFragment {
    private FragmentTambahPartisipanBinding binding;
    private PertemuanPresenter pertemuanPresenter;
    private UserPresenter userPresenter;

    public static TambahPartisipanFragment newInstance(FragmentManager fm, PertemuanPresenter pertemuanPresenter, UserPresenter userPresenter) {
        TambahPartisipanFragment fragment = new TambahPartisipanFragment();
        fragment.show(fm, "tambahPartisipan");
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.userPresenter = userPresenter;

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
        PartisipanListAdapter partisipanListAdapter = new PartisipanListAdapter();
        this.binding.lstTimeSlot.setAdapter(partisipanListAdapter);

        LinearLayout layoutJadwal = binding.llJadwalDosen;
        layoutJadwal.setVisibility(View.GONE);

        AutocompleteAdapter autocompleteAdapter = new AutocompleteAdapter(getContext(), 0);
        autocompleteAdapter.getFilter().filter("");
        AutoCompleteTextView autoCompleteTextView = binding.actvChooseParticipant;
        autoCompleteTextView.setAdapter(autocompleteAdapter);

        return this.binding.getRoot();
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
                /*Toast.makeText(getContext(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG)
                        .show();*/
                if(selectedItemIsDosen(parent.getItemAtPosition(position))) {
                    binding.llJadwalDosen.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private boolean selectedItemIsDosen(Object itemAtPosition) {
        return true;
    }
}

package com.example.p3b_tubes_2.View;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.databinding.FragmentTambahSlotWaktuBinding;

public class TambahSlotWaktuFragment extends DialogFragment {
    private FragmentTambahSlotWaktuBinding binding;
    private PertemuanPresenter presenter;

    public static TambahSlotWaktuFragment newInstance(FragmentManager fm, PertemuanPresenter presenter) {
        TambahSlotWaktuFragment fragment = new TambahSlotWaktuFragment();
        fragment.show(fm, "tambahSlotWaktu");
        fragment.presenter = presenter;
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
        this.binding = FragmentTambahSlotWaktuBinding.inflate(inflater);

        String[] hari = getResources().getStringArray(R.array.dropdownHari);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.login_dropdown_item, hari);
        binding.etHari.setAdapter(arrayAdapter);

        setTime();

        return binding.getRoot();
    }

    private void setTime() {
        getParentFragmentManager().setFragmentResultListener("setTime", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String time = result.getString("time");
                String type = result.getString("type");

                if (type.equals("start")) {
                    binding.etStartTime.setText(time);
                } else {
                    binding.etEndTime.setText(time);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appbar.setNavigationOnClickListener(this::closeDialogFragment);
        binding.btnTambah.setOnClickListener(this::addTimeSlot);
        binding.etStartTime.setOnClickListener(this::showTimePicker);
        binding.etEndTime.setOnClickListener(this::showTimePicker);
    }

    private void closeDialogFragment(View view) {
        this.dismiss();
    }

    private void addTimeSlot(View view) {
        String hari = binding.etHari.getText().toString();
        String waktuMulai = binding.etStartTime.getText().toString();
        String waktuBerakhir = binding.etEndTime.getText().toString();

        if(hari.equals("")){
            this.binding.tvError.setText("Hari harus diisi");
        }else if(waktuMulai.equals("")){
            this.binding.tvError.setText("Waktu mulai harus diisi");
        }else if(waktuBerakhir.equals("")){
            this.binding.tvError.setText("Waktu selesai harus diisi");
        }else{
            this.presenter.addTimeSlot(hari, waktuMulai, waktuBerakhir);
        }
    }

    private void showTimePicker(View view) {
        String type = "start";
        if (view == binding.etEndTime) {
            type = "end";
        }
        DialogFragment timePicker = TimePickerFragment.newInstance(type);
        timePicker.show(this.getParentFragmentManager(), "timePicker");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void showError(String msg) {
        this.binding.tvError.setText(msg);
    }
}

package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.p3b_tubes_2.databinding.DialogFragmentAddAppointmentBinding;

public class TambahAppointmentFragment extends DialogFragment {
    DialogFragmentAddAppointmentBinding binding;

    public static TambahAppointmentFragment display(FragmentManager fm) {
        TambahAppointmentFragment tambahAppointmentDialogFragment = new TambahAppointmentFragment();
        tambahAppointmentDialogFragment.show(fm, "addAppointment");
        return tambahAppointmentDialogFragment;
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

        binding = DialogFragmentAddAppointmentBinding.inflate(inflater);

        setDate();
        setTime();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appbar.setNavigationOnClickListener(this::closeDialogFragment);
        binding.btnCancel.setOnClickListener(this::closeDialogFragment);

        binding.etDate.setOnClickListener(this::showDatePicker);
        binding.etStartTime.setOnClickListener(this::showTimePicker);
        binding.etEndTime.setOnClickListener(this::showTimePicker);

        binding.btnAddAppointment.setOnClickListener(this::addAppointment);
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

    private void showTimePicker(View view) {
        String type = "start";
        if(view == binding.etEndTime) {
            type = "end";
        }
        DialogFragment timePicker = TimePickerFragment.newInstance(type);
        timePicker.show(getParentFragmentManager(), "timePicker");
    }

    private void showDatePicker(View view) {
        DialogFragment datePicker = DatePickerFragment.newInstance();
        datePicker.show(getParentFragmentManager(), "datePicker");
    }

    private void setDate() {
        getParentFragmentManager().setFragmentResultListener("setDate", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                binding.etDate.setText(result.getString("date"));
            }
        });
    }

    private void setTime() {
        getParentFragmentManager().setFragmentResultListener("setTime", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String time = result.getString("time");
                String type = result.getString("type");

                if(type.equals("start")) {
                    binding.etStartTime.setText(time);
                } else {
                    binding.etEndTime.setText(time);
                }
            }
        });
    }

    private void closeDialogFragment(View view) {
        dismiss();
    }

    private void addAppointment(View view) {
    }
}

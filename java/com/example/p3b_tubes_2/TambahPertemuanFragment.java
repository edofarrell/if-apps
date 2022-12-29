package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.p3b_tubes_2.databinding.FragmentAddPertemuanBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;


public class TambahPertemuanFragment extends DialogFragment {
    private FragmentAddPertemuanBinding binding;
    private PertemuanPresenter pertemuanPresenter;
    private MainPresenter mainPresenter;
    private TambahPartisipanFragment tambahPartisipanFragment;
    private ChipGroup chipGroup;
    private ArrayList<Chip> arrChipGroup;

    public static TambahPertemuanFragment newInstance(FragmentManager fm, PertemuanPresenter pertemuanPresenter, MainPresenter mainPresenter) {
        TambahPertemuanFragment fragment = new TambahPertemuanFragment();
        fragment.show(fm, "tambahPertemuan");
        fragment.pertemuanPresenter = pertemuanPresenter;
        fragment.mainPresenter = mainPresenter;
        fragment.arrChipGroup = new ArrayList<>();
        return fragment;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentAddPertemuanBinding.inflate(inflater);
        this.chipGroup = binding.chipGroup;

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
        binding.btnTambahPartisipan.setOnClickListener(this::addParticipant);
    }

    private void addAppointment(View view) {
        String title = this.binding.etTitle.getText().toString();
        String inputDate = this.binding.etDate.getEditableText().toString();
        String startTime = this.binding.etStartTime.getEditableText().toString();
        String endTime = this.binding.etEndTime.getEditableText().toString();
        String description = this.binding.etDescription.getEditableText().toString();

        SimpleDateFormat inputformatter = new SimpleDateFormat("E,dd MMM yyyyHH:mm");
        SimpleDateFormat outputformatter = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
        String startDateTime = null;
        String endDateTime = null;
        try {
            startDateTime = outputformatter.format(inputformatter.parse(inputDate + startTime));
            endDateTime = outputformatter.format(inputformatter.parse(inputDate + endTime));
        } catch (ParseException e) {
            Log.d("DEBUG", "TambahAppointmentFragment: addAppointment() catch ParseException");
        }

//        this.presenter.addPertemuan(title, description, startDateTime, endDateTime);
    }

    private void addParticipant(View view) {
        this.tambahPartisipanFragment = TambahPartisipanFragment.newInstance(this.getParentFragmentManager(), this.pertemuanPresenter, this.mainPresenter);
    }

    public void addSelecteduser(User user) {
        String name = user.getName();
        Chip chip = new Chip(this.getContext());
        chip.setText(name);

        chip.setCloseIconVisible(true);
        chip.setTextColor(getResources().getColor(R.color.black));
        chip.setOnCloseIconClickListener(this::removeChip);
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        if (arrChipGroup.size() == 0) {
            addChip(chip);
        }

        boolean isChipAdded = false;
        for (Chip item : arrChipGroup) {
            if (item.getText().toString().contains(name)) {
                isChipAdded = true;
                break;
            }
        }

        if(!isChipAdded) {
            addChip(chip);
        }
    }

    private void addChip(Chip chip) {
        chipGroup.addView(chip);
        arrChipGroup.add(chip);
    }

    private void removeChip(View view) {
        Chip chip = (Chip) view;
        this.chipGroup.removeView(view);
        this.arrChipGroup.remove(chip);
    }

    public void updateTimeSlot(List<TimeSlot> timeSlot){
        this.tambahPartisipanFragment.updateTimeSlot(timeSlot);
    }

    private void showTimePicker(View view) {
        String type = "start";
        if (view == binding.etEndTime) {
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

                if (type.equals("start")) {
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

}

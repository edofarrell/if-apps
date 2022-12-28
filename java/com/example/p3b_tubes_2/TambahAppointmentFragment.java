package com.example.p3b_tubes_2;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.p3b_tubes_2.databinding.FragmentAddAppointmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TambahAppointmentFragment extends DialogFragment {
    private FragmentAddAppointmentBinding binding;
    private PertemuanPresenter presenter;

    public static TambahAppointmentFragment newInstance(FragmentManager fm, PertemuanPresenter presenter) {
        TambahAppointmentFragment fragment = new TambahAppointmentFragment();
        fragment.show(fm, "addAppointment");
        fragment.presenter = presenter;
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

        binding = FragmentAddAppointmentBinding.inflate(inflater);

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
        binding.btnAddParticipant.setOnClickListener(this::addParticipant);
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
            startDateTime = outputformatter.format(inputformatter.parse(inputDate+startTime));
            endDateTime = outputformatter.format(inputformatter.parse(inputDate+endTime));
        } catch (ParseException e) {
            Log.d("DEBUG", "TambahAppointmentFragment: addAppointment() catch ParseException");
        }

//        this.presenter.addPertemuan(title, description, startDateTime, endDateTime);
    }

    private void addParticipant(View view) {

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

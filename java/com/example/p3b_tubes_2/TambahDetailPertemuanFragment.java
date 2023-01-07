package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.p3b_tubes_2.Presenter.PertemuanPresenter;
import com.example.p3b_tubes_2.databinding.FragmentTambahDetailPertemuanBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TambahDetailPertemuanFragment extends Fragment {
    private FragmentTambahDetailPertemuanBinding binding;
    private MainPresenter mainPresenter;
    private PertemuanPresenter presenter;


    public static TambahDetailPertemuanFragment newInstance(MainPresenter mainPresenter, PertemuanPresenter pertemuanPresenter) {
        TambahDetailPertemuanFragment fragment = new TambahDetailPertemuanFragment();
        fragment.mainPresenter = mainPresenter;
        fragment.presenter = pertemuanPresenter;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentTambahDetailPertemuanBinding.inflate(inflater);

        setDate();
        setTime();

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.binding.etDate.setOnClickListener(this::showDatePicker);
        this.binding.etStartTime.setOnClickListener(this::showTimePicker);
        this.binding.etEndTime.setOnClickListener(this::showTimePicker);

        this.binding.btnSelanjutnya.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        this.addAppointment();
    }

    private void addAppointment() {
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

        this.presenter.addPertemuan(title, description, startDateTime, endDateTime);
    }

    private void showDatePicker(View view) {
        DialogFragment datePicker = DatePickerFragment.newInstance();
        datePicker.show(getParentFragmentManager(), "datePicker");
    }

    private void showTimePicker(View view) {
        String type = "start";
        if (view == binding.etEndTime) {
            type = "end";
        }
        DialogFragment timePicker = TimePickerFragment.newInstance(type);
        timePicker.show(getParentFragmentManager(), "timePicker");
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
}

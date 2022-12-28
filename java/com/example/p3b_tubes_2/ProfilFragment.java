package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.p3b_tubes_2.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment {
    FragmentProfilBinding binding;

    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.binding = FragmentProfilBinding.inflate(inflater);

        this.binding.tvNamePlaceholder.setText(getAccountName());
        this.binding.tvName.setText(getAccountName());
        this.binding.tvEmail.setText(getAccountEmail());
        this.binding.tvRole.setText(getAccountRole());

        return binding.getRoot();
    }

    private String getAccountRole() {
        String accountRole = "";

        return accountRole;
    }

    private String getAccountEmail() {
        String accountEmail = "";

        return accountEmail;
    }

    private String getAccountName() {
        String accountName = "";

        return accountName;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogout.setOnClickListener(this::logout);
    }

    private void logout(View view) {
        Bundle result = new Bundle();
        result.putString("page", "login");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

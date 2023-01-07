package com.example.p3b_tubes_2.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.APIClient;
import com.example.p3b_tubes_2.Model.User;
import com.example.p3b_tubes_2.UserContract;
import com.example.p3b_tubes_2.databinding.FragmentProfilBinding;

import java.util.List;

public class ProfilFragment extends Fragment implements UserContract.View.Profile {
    private FragmentProfilBinding binding;
    private User user;

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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogout.setOnClickListener(this::logout);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            updateView();
        }
    }

    public void updateView(){
        this.binding.tvNamePlaceholder.setText(user.getName());
        this.binding.tvName.setText(user.getName());
        this.binding.tvEmail.setText(user.getEmail());
        this.binding.tvRole.setText(user.getRoles());
    }

    private void logout(View view) {
        APIClient.token = "";
        Bundle result = new Bundle();
        result.putString("page", "login");
        this.getParentFragmentManager().setFragmentResult("changePage", result);
    }

    @Override
    public void updateProfile(User user) {
        this.user = user;
    }

}

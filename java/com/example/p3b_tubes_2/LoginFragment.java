package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding fragmentLoginBinding;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater);

        this.fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);

        return this.fragmentLoginBinding.getRoot();
    }

    private void onClick(View view) {
        Bundle result = new Bundle();
        result.putString("page", "home");
        getParentFragmentManager().setFragmentResult("changePage", result);
    }
}

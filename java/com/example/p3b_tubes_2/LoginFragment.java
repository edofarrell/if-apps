package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentLoginBinding;

import org.json.JSONException;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding fragmentLoginBinding;
    private MainPresenter presenter;

    public static LoginFragment newInstance(MainActivity activity) {
        Bundle args = new Bundle();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.presenter = new MainPresenter(activity);
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater);
        this.fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);
        //(getActivity()).getSupportActionBar().hide();
        ((MainActivity)getActivity()).setDrawer_locked();

        String[] roles = getResources().getStringArray(R.array.dropdownRole);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.login_dropdown_item, roles);
        fragmentLoginBinding.etRole.setAdapter(arrayAdapter);

        return this.fragmentLoginBinding.getRoot();
    }

    private void onClick(View view) {
        String email = this.fragmentLoginBinding.etEmail.getText().toString();
        String password = this.fragmentLoginBinding.etPassword.getText().toString();
        String role = this.fragmentLoginBinding.etRole.getText().toString();

        try {
            this.presenter.login(email, password, role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bundle result = new Bundle();
        result.putString("page", "home");
        getParentFragmentManager().setFragmentResult("changePage", result);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        ((MainActivity)getActivity()).setDrawer_unlocked();
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}

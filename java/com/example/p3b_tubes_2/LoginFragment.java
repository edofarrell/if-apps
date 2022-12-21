package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.databinding.FragmentLoginBinding;

import org.json.JSONException;

public class LoginFragment extends Fragment implements LoginContract.View{
    private FragmentLoginBinding fragmentLoginBinding;
    private LoginPresenter presenter;

    public static LoginFragment newInstance(MainPresenter mainPresenter) {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.presenter = new LoginPresenter(fragment, mainPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater);
        this.fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);

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
    }


    @Override
    public void updateLoginView(boolean status) {
        if(!status){
            this.fragmentLoginBinding.error.setText("Login gagal, periksa kemabli username/password/role");
        }else{
            Bundle result = new Bundle();
            result.putString("page", "home");
            getParentFragmentManager().setFragmentResult("changePage", result);
        }
    }
}

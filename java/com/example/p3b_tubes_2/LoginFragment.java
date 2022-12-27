package com.example.p3b_tubes_2;

import android.content.Context;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

public class LoginFragment extends Fragment implements LoginContract.View{
    private FragmentLoginBinding fragmentLoginBinding;
    private UserPresenter presenter;

    public static LoginFragment newInstance(MainPresenter mainPresenter, Context context) {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.presenter = new UserPresenter(fragment, context, mainPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater);
        this.fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);

        //(getActivity()).getSupportActionBar().hide();
//        ((MainActivity)getActivity()).setDrawer_locked();

        String[] roles = getResources().getStringArray(R.array.dropdownRole);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.login_dropdown_item, roles);
        fragmentLoginBinding.etRole.setAdapter(arrayAdapter);

        return this.fragmentLoginBinding.getRoot();
    }

    private void onClick(View view) {
        String email = this.fragmentLoginBinding.etEmail.getText().toString();
        String password = this.fragmentLoginBinding.etPassword.getText().toString();
        String role = this.fragmentLoginBinding.etRole.getText().toString().toLowerCase();

        try {
            this.presenter.login(email, password, role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle result = new Bundle();
        result.putString("page", "pengumuman");

        getParentFragmentManager().setFragmentResult("changePage", result);
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

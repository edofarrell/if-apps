package com.example.p3b_tubes_2.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p3b_tubes_2.LoginContract;
import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.Presenter.UserPresenter;
import com.example.p3b_tubes_2.R;
import com.example.p3b_tubes_2.UserContract;
import com.example.p3b_tubes_2.databinding.FragmentLoginBinding;

import org.json.JSONException;

public class LoginFragment extends Fragment implements LoginContract.View {
    private FragmentLoginBinding fragmentLoginBinding;
    private UserPresenter presenter;

    public static LoginFragment newInstance(MainPresenter mainPresenter, Context context, UserContract.View.Profile profilFragment) {
        LoginFragment fragment = new LoginFragment();
        fragment.presenter = new UserPresenter(fragment, profilFragment, context, mainPresenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater);

        String[] roles = getResources().getStringArray(R.array.dropdownRole);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.login_dropdown_item, roles);
        fragmentLoginBinding.etRole.setAdapter(arrayAdapter);

        return this.fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);
        this.fragmentLoginBinding.etRole.setOnClickListener(this::closeSoftKeyboard);
    }

    private void closeSoftKeyboard(View view) {
        Activity activity = this.getActivity();
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    }

    public UserPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void updateLoginView(boolean status) {
        if(!status){
            this.fragmentLoginBinding.error.setText("Login gagal, periksa kembali username/password/role");
        }else{
            Bundle result = new Bundle();
            result.putString("page", "pengumuman");
            getParentFragmentManager().setFragmentResult("changePage", result);

            result = new Bundle();
            result.putBoolean("hideMenuItem", true);
            getParentFragmentManager().setFragmentResult("hideMenuItem", result);

            this.fragmentLoginBinding.etEmail.setText("");
            this.fragmentLoginBinding.etPassword.setText("");
            this.fragmentLoginBinding.etRole.setText("");
            this.fragmentLoginBinding.error.setText("");
        }
    }

}

package com.example.p3b_tubes_2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.p3b_tubes_2.Presenter.MainPresenter;
import com.example.p3b_tubes_2.View.FRSFragment;
import com.example.p3b_tubes_2.View.LoginFragment;
import com.example.p3b_tubes_2.View.PengumumanFragment;
import com.example.p3b_tubes_2.View.PertemuanFragment;
import com.example.p3b_tubes_2.View.ProfilFragment;
import com.example.p3b_tubes_2.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        MainPresenter mainPresenter = new MainPresenter();

        this.fragments = new HashMap<>();

        this.fragments.put("pengumuman", PengumumanFragment.newInstance(mainPresenter, this, this.binding.fragmentContainer));
        this.fragments.put("pertemuan", PertemuanFragment.newInstance(mainPresenter, this, this.binding.fragmentContainer));
        this.fragments.put("frs", FRSFragment.newInstance(mainPresenter,this));
        this.fragments.put("profil", ProfilFragment.newInstance());
        this.fragments.put("login", LoginFragment.newInstance(mainPresenter, this, (ProfilFragment) this.fragments.get("profil")));
        this.fm = getSupportFragmentManager();

        LoginFragment loginFragment = (LoginFragment) this.fragments.get("login");
        mainPresenter.setUserPresenter(loginFragment.getPresenter());

        BottomNavigationView bottomNavigation = binding.bottomNavigation;
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String page = "";
                switch (item.getItemId()) {
                    case R.id.item_pengumuman:
                        page = "pengumuman";
                        break;
                    case R.id.item_pertemuan:
                        page = "pertemuan";
                        break;
                    case R.id.item_frs:
                        page = "frs";
                        break;
                    case R.id.item_profil:
                        page = "profil";
                        break;
                }
                changePage(page);
                return true;
            }
        });

        AppBarLayout topAppBar = binding.appbarTopAppBar;

//        getSupportActionBar().hide();
        topAppBar.setVisibility(View.GONE);
        bottomNavigation.setVisibility(View.GONE);

        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(binding.fragmentContainer.getId(), fragments.get("login"))
                .commit();

        this.fm.setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String page = result.getString("page");
                if (page.equals("login")) {
                    topAppBar.setVisibility(View.GONE);
                    bottomNavigation.setVisibility(View.GONE);
                } else {
                    topAppBar.setVisibility(View.VISIBLE);
                    bottomNavigation.setVisibility(View.VISIBLE);
                }
                changePage(page);
            }
        });
    }

    private void changePage(String page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        if (page.equals("exit")) {
            closeApplication();
        } else {
            changeAppBarElevation(page);

            Fragment intendedFragment = this.fragments.get(page);
            if (intendedFragment.isAdded()) {
                ft.show(intendedFragment);
            } else {
                ft.add(this.binding.fragmentContainer.getId(), intendedFragment);
            }

            String key;
            Fragment unintendedFragment;
            for (Map.Entry<String, Fragment> set : this.fragments.entrySet()) {
                key = set.getKey();
                if (!key.equals(page)) {
                    unintendedFragment = this.fragments.get(key);
                    if (unintendedFragment.isAdded()) {
                        ft.hide(unintendedFragment);
                    }
                }
            }
            ft.commit();
        }
    }

    private void changeAppBarElevation(String page) {
        if(page.equals("pertemuan")) {
            this.binding.appbarTopAppBar.setElevation(0f);
        } else {
            this.binding.appbarTopAppBar.setElevation(8.0f);
        }
    }

    private void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }
}
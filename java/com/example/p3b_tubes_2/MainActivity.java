package com.example.p3b_tubes_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.p3b_tubes_2.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        MainPresenter mainPresenter = new MainPresenter();

        this.fragments = new HashMap<>();
        this.fragments.put("home", HomeFragment.newInstance());
        this.fragments.put("left", LeftFragment.newInstance());
        this.fragments.put("login", LoginFragment.newInstance(mainPresenter, this));
        this.fragments.put("pengumuman", PengumumanFragment.newInstance(mainPresenter));
        this.fragments.put("pertemuan", PertemuanFragment.newInstance(mainPresenter));
        this.fragments.put("frs", FRSFragment.newInstance(mainPresenter));

        this.fm = getSupportFragmentManager();
        this.drawer = binding.drawerLayout;
        this.toolbar = binding.toolbar;
        setSupportActionBar(this.toolbar);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        binding.drawerLayout.addDrawerListener(abdt);
        abdt.syncState();

        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(binding.fragmentContainer.getId(), fragments.get("login"))
                .commit();

        //getSupportActionBar().hide();

        this.fm.setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String page = result.getString("page");
                if (page.equals("login")) getSupportActionBar().hide();
                else getSupportActionBar().show();
                changePage(page);
            }
        });
    }

    private void changePage(String page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        if (page.equals("exit")) {
            closeApplication();
        } else {
            ft.replace(this.binding.fragmentContainer.getId(), fragments.get(page))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }

    public void setDrawer_locked() {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);
    }

    public void setDrawer_unlocked() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);


    }
}
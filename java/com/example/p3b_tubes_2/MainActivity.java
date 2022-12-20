package com.example.p3b_tubes_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.fragments = new HashMap<>();
        this.fragments.put("login", LoginFragment.newInstance(this));
        this.fragments.put("home", HomeFragment.newInstance());
        this.fragments.put("pengumuman",PengumumanFragment.newInstance());
        this.fragments.put("pertemuan",PertemuanFragment.newInstance());
        this.fragments.put("frs",FRSFragment.newInstance());
        this.fm = getSupportFragmentManager();

        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(binding.fragmentContainer.getId(), fragments.get("login"))
                .commit();

        this.fm.setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String page = result.getString("page");
                changePage(page);
            }
        });
    }

    private void changePage(String page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        if(page.equals("exit")) {
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
}
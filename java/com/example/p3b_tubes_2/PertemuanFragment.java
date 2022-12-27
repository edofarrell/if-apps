package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class PertemuanFragment extends Fragment implements PertemuanContract.View {
    private FragmentPertemuanBinding binding;
    private PertemuanPresenter presenter;
    private PertemuanListAdapter adapter;
    private FrameLayout frameLayout;
    private HashMap<String, Fragment> fragments;
    private FragmentManager fm;

    private PertemuanFragment() {}

    public static PertemuanFragment newInstance(MainPresenter mainPresenter, Context context, FrameLayout frameLayout) {
        Bundle args = new Bundle();
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = new PertemuanPresenter(fragment, context, mainPresenter);
        fragment.adapter = new PertemuanListAdapter(fragment.presenter);
        fragment.frameLayout = frameLayout;
        fragment.fragments = new HashMap<>();
        fragment.fragments.put("pertemuanDibuat", PertemuanDibuatFragment.newInstance());
        fragment.fragments.put("pertemuanDiundang", PertemuanDiundangFragment.newInstance());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        this.binding.lstPertemuan.setAdapter(this.adapter);
        this.presenter.getPertemuan();
        this.binding = FragmentPertemuanBinding.inflate(inflater);

        this.fm = getChildFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(this.binding.fragmentContainer.getId(), this.fragments.get("pertemuanDibuat"))
                .commit();

        this.binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                String page = "";
                switch (tabPosition) {
                    case 0:
                        page = "pertemuanDibuat";
                        break;
                    case 1:
                        page = "pertemuanDiundang";
                        break;
                }
                Log.d("DEBUG", page);

                changePage(page);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return this.binding.getRoot();
    }

    private void changePage(String page) {
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment intendedFragment = fragments.get(page);
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

    private void tambah(View view) {
        //String[] arr = {"cdc143f6-3efe-4a9f-ad29-1be12e357fe9"};
        presenter.deletePertemuan("13e633be-74ff-4c3c-b8be-35e868504fe6");
        /*presenter.getPertemuan("c549e314-73db-4adf-8ef7-82c1bf89a527",
                "2022-12-22", "2022-12-29");*/

        /*try {
            presenter.addPertemuan("Dearen Test Api","Test Api Android Studio",
                    "2022-12-24 10:00+0700","2022-12-24 12:00+0700");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void update(PertemuanList pertemuanList) {
        this.adapter.update(pertemuanList);
    }

    @Override
    public void openDetail(PertemuanList.Pertemuan pertemuan){
        PertemuanDetailFragment pertemuanDetailFragment = PertemuanDetailFragment.newInstance(pertemuan);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), pertemuanDetailFragment)
                .addToBackStack(null)
                .commit();
    }

}

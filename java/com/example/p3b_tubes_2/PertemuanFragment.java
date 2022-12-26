package com.example.p3b_tubes_2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.p3b_tubes_2.databinding.FragmentPertemuanBinding;

public class PertemuanFragment extends Fragment implements PertemuanContract.View {
    private FragmentPertemuanBinding binding;
    private PertemuanPresenter presenter;
    private PertemuanListAdapter adapter;
    private FrameLayout frameLayout;

    private PertemuanFragment() {
    }

    public static PertemuanFragment newInstance(MainPresenter mainPresenter, Context context, FragmentManager fm, FrameLayout frameLayout) {
        Bundle args = new Bundle();
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = new PertemuanPresenter(fragment, context, mainPresenter);
        fragment.adapter = new PertemuanListAdapter(fragment.presenter);
        fragment.frameLayout = frameLayout;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPertemuanBinding.inflate(inflater);

        this.binding.lstPertemuan.setAdapter(this.adapter);
        this.presenter.getPertemuan();
        binding.btnTest.setOnClickListener(this::tambah);
        return binding.getRoot();
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
        DetailPertemuanFragment detailPertemuanFragment = DetailPertemuanFragment.newInstance(pertemuan);
        getParentFragmentManager().beginTransaction().replace(frameLayout.getId(), detailPertemuanFragment)
                .addToBackStack(null)
                .commit();
    }

}

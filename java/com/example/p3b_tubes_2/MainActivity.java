package com.example.p3b_tubes_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.p3b_tubes_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
    }
}
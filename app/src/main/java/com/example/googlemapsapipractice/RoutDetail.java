package com.example.googlemapsapipractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.googlemapsapipractice.databinding.ActivityRoutDetailBinding;

public class RoutDetail extends AppCompatActivity {
    ActivityRoutDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String path = "";
        path = intent.getStringExtra("path");
        binding.textView.setText(path);
    }
}
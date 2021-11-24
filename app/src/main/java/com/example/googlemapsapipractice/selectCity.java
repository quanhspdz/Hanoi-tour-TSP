package com.example.googlemapsapipractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.googlemapsapipractice.databinding.ActivitySelectCityBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class selectCity extends AppCompatActivity {
    CityAdapter adapter;
    ArrayList<PlaceForm> places;
    ActivitySelectCityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        places = PlacePositionSaver.places;
        adapter = new CityAdapter(R.layout.each_row_city, getApplicationContext(), places);
        binding.listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PlacePositionSaver.places = places;
    }
}
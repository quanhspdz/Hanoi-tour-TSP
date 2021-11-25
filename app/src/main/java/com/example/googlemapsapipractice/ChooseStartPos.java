package com.example.googlemapsapipractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.googlemapsapipractice.databinding.ActivityChooseStartPosBinding;

import java.util.ArrayList;

public class ChooseStartPos extends AppCompatActivity {
    ActivityChooseStartPosBinding binding;
    ArrayList<PlaceForm> places;
    ArrayAdapter<String> adapter;
    ArrayList<String> placeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseStartPosBinding.inflate(getLayoutInflater());
        setTitle("Choose your starting point!");
        setContentView(binding.getRoot());

        places = PlacePositionSaver.places;
        placeNames = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            placeNames.add(places.get(i).getName());
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.start_city_each_row, placeNames);
        binding.listViewStart.setAdapter(adapter);

        binding.listViewStart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int k = 0; k < places.size(); k++) {
                    places.get(k).setStart(false);
                }
                places.get(i).setStart(true);
                PlacePositionSaver.places = places;
                finish();
            }
        });
    }
}
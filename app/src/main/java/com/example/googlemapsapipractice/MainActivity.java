package com.example.googlemapsapipractice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.googlemapsapipractice.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    static final String API_KEY = "AIzaSyCqn9jD9UPxnVjp3Tw1IevkasiFqEePylk";
    ActivityMainBinding binding;
    GoogleMap map;
    ArrayList<PlaceForm> places;
    LatLng currentLatLng;
    int startPos;
    Boolean DesIsSet = false;
    Boolean StartIsSet = false;
    double distance = 0;
    String path = "";
    ArrayList<Integer> pathArr, arrayPath;
    public static ColorStateList buttonColor = ColorStateList.valueOf(Color.rgb(75, 199, 207));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setTitle("Hanoi Tour - DSA Group 4");
        setContentView(binding.getRoot());

        init();
        setListener();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        places = PlacePositionSaver.places;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getDes()) {
                DesIsSet = true;
            }
            if (places.get(i).getStart()) {
                StartIsSet = true;
            }
        }
        if (StartIsSet || DesIsSet) resetMarker();
    }

    private void setListener() {
        binding.fbuttonDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, selectCity.class);
                startActivity(intent);
            }
        });
        binding.fbuttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseStartPos.class);
                startActivity(intent);
            }
        });
        binding.fbuttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DesIsSet && StartIsSet) {
                    startTour();
                }
            }
        });
    }

    private void startTour() {
        int k = 0;
        int[] desPlace = new int[places.size()];
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getStart() || places.get(i).getDes()) {
                if (places.get(i).getStart()) {
                    desPlace[k] = desPlace[0];
                    desPlace[0] = i;
                } else {
                    desPlace[k] = i;
                }
                k++;
            }
        }

        double[][] dist = new double[k + 2][k + 2];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = distance(places.get(desPlace[i]).getPosition().latitude,
                                places.get(desPlace[i]).getPosition().longitude,
                                places.get(desPlace[j]).getPosition().latitude,
                                places.get(desPlace[j]).getPosition().longitude);
                    dist[j][i] = dist[i][j];
                }
            }
        }

        TSP.n = k;
        TSP.dist = dist;
        TSP.start = 0;
        TSP.init();
        distance = TSP.CalculateTSP(1, 0);
        TSP.showPath_TSP(1, 0);
        path = TSP.pathString;
        pathArr = TSP.pathArr;

        path = "";
        arrayPath = new ArrayList<>();
        for (int i = 0; i < pathArr.size(); i++) {
            arrayPath.add(desPlace[pathArr.get(i)]);
            String tmp = places.get(desPlace[pathArr.get(i)]).getName();
            path += tmp + "\n";
        }
//        Intent intent = new Intent(MainActivity.this, RoutDetail.class);
//        intent.putExtra("path", path);
//        startActivity(intent);

        drawPolyline();
    }

    private void drawPolyline() {
        for (int i = 0; i < arrayPath.size() - 1; i++) {
            map.addPolyline(new PolylineOptions()
                    .add(places.get(arrayPath.get(i)).getPosition()
                            , places.get(arrayPath.get(i + 1)).getPosition())
                    .color(Color.YELLOW));
        }
        binding.fbuttonSubmit.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PlacePositionSaver.HoGuom, 15));
    }

    private void resetMarker() {
        map.clear();
        if (StartIsSet) {
            for (int i = 0; i < places.size(); i++) {
                if (places.get(i).getStart()) {
                    map.addMarker(new MarkerOptions()
                            .title(places.get(i).getName())
                            .snippet(places.get(i).getDetail())
                            .position(places.get(i).getPosition())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    startPos = i;
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(places.get(i).getPosition(), 15));
                    break;
                }
            }
            binding.fbuttonStart.setText("Start from: " + places.get(startPos).getName());
            binding.fbuttonStart.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
        }
        if (DesIsSet) {
            int numDes = 0;
            for (int i = 0; i < places.size(); i++) {
                if (places.get(i).getDes() && i != startPos) {
                    map.addMarker(new MarkerOptions()
                            .title(places.get(i).getName())
                            .snippet(places.get(i).getDetail())
                            .position(places.get(i).getPosition()));
                    numDes++;
                }
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(PlacePositionSaver.DongXuan, 14));
            binding.fbuttonDes.setText("To: " + numDes + " place(s) selected!");
            binding.fbuttonDes.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
        }
        binding.fbuttonSubmit.setBackgroundTintList(buttonColor);
    }

    private void init() {
        binding.fbuttonStart.setBackgroundTintList(buttonColor);
        binding.fbuttonDes.setBackgroundTintList(buttonColor);
        binding.fbuttonSubmit.setBackgroundTintList(buttonColor);

        PlacePositionSaver.addPlace();
        places = new ArrayList<>();
        places = PlacePositionSaver.places;
    }
}
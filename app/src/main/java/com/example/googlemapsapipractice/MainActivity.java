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
    ArrayList<Integer> pathArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        int startPos = 0, k = 0;
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
        for (int i = 0; i < pathArr.size(); i++) {
            String tmp = places.get(desPlace[pathArr.get(i)]).getName();
            path += tmp + "\n";
        }
        Intent intent = new Intent(MainActivity.this, RoutDetail.class);
        intent.putExtra("path", path);
        startActivity(intent);
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
            binding.fbuttonStart.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
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
            binding.fbuttonDes.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }
    }

    private void init() {
        PlacePositionSaver.addPlace();
        places = new ArrayList<>();
        places.add(new PlaceForm("Lăng Bắc", "Quảng trường Ba Đình", new LatLng(21.037568724591225, 105.83623230512778)));
        places.add(new PlaceForm("Hồ Gươm", "", new LatLng(21.028854850370298, 105.8522037649517)));
        places.add(new PlaceForm("Hồ Tây", "", new LatLng(21.054586820871524, 105.82579584525263)));
        places.add(new PlaceForm("Nhà thờ lớn Hà Nội", "", new LatLng(21.028762799739486, 105.84887396705543)));
        places.add(new PlaceForm("Nhà hát lớn Hà Nội", "", new LatLng(21.02443949254371, 105.8575136263105)));
        places.add(new PlaceForm("Hoàng thành Thăng Long", "", new LatLng(21.034501153013178, 105.8401141974747)));
        places.add(new PlaceForm("Chùa Một Cột", "", new LatLng(21.03600339326734, 105.83363352631059)));
        places.add(new PlaceForm("Văn Miếu – Quốc Tử Giám", "", new LatLng(21.02768767428784, 105.83551459932626)));
        places.add(new PlaceForm("Cột cờ Hà Nội", "", new LatLng(21.032635698333745, 105.83978243980273)));
        places.add(new PlaceForm("Cầu Long Biên", "", new LatLng(21.043575385627655, 105.85889041096694)));
        places.add(new PlaceForm("Ga Hà Nội", "", new LatLng(21.025251762298964, 105.84121358398244)));
        places.add(new PlaceForm("Chợ Đồng Xuân", "", new LatLng(21.037827398640594, 105.8495516146702)));
        places.add(new PlaceForm("Đền Quán Thánh", "", new LatLng(21.043125346077066, 105.83650261283685)));
        places.add(new PlaceForm("Phố đi bộ Hồ Gươm", "", new LatLng(21.025567489268102, 105.85329782816218)));
    }
}
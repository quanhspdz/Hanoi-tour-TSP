package com.example.googlemapsapipractice;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlacePositionSaver {
    public static final LatLng HoGuom = new LatLng(21.028854850370298, 105.8522037649517);
    public static final LatLng DongXuan = new LatLng(21.037827398640594, 105.8495516146702);
    public static ArrayList<PlaceForm> places = new ArrayList<>();
    public static void addPlace() {
        places.add(new PlaceForm("Lăng Bác", "Quảng trường Ba Đình", new LatLng(21.037568724591225, 105.83623230512778)));
        places.add(new PlaceForm("Truờng Đại học Công nghệ", "", new LatLng(21.038902482537342, 105.78296809797327)));
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
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
//        places.add(new PlaceForm("", "", new LatLng()));
    }
}

package com.example.googlemapsapipractice;

import com.google.android.gms.maps.model.LatLng;

public class PlaceForm {
    private String name;
    private String detail;
    private LatLng position;
    private Boolean isStart = false;
    private Boolean isDes = false;

    public PlaceForm(String name, String detail, LatLng position) {
        this.name = name;
        this.detail = detail;
        this.position = position;
    }

    public PlaceForm(String name, String detail, LatLng position, Boolean isStart, Boolean isDes) {
        this.name = name;
        this.detail = detail;
        this.position = position;
        this.isStart = isStart;
        this.isDes = isDes;
    }

    public Boolean getStart() {
        return isStart;
    }

    public void setStart(Boolean start) {
        isStart = start;
    }

    public Boolean getDes() {
        return isDes;
    }

    public void setDes(Boolean des) {
        isDes = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}

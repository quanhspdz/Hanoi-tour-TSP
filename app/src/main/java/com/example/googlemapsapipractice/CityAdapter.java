package com.example.googlemapsapipractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter {
    int layout;
    Context context;
    ArrayList<PlaceForm> places;

    public CityAdapter(int layout, Context context, ArrayList<PlaceForm> places) {
        this.layout = layout;
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.checkBox = view.findViewById(R.id.checkBox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.checkBox.setText(places.get(i).getName());
        if (places.get(i).getDes()) {
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    places.get(i).setDes(true);
                } else {
                    places.get(i).setDes(false);
                }
            }
        });
        return view;
    }
    class ViewHolder {
        public CheckBox checkBox;
    }
}

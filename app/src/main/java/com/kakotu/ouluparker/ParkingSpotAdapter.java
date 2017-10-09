package com.kakotu.ouluparker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 7.10.2017.
 */

public class ParkingSpotAdapter extends ArrayAdapter<ParkingSpot>{
    Context context;

    public ParkingSpotAdapter(Context context, int resourceId, List<ParkingSpot> parkingSpots){
        super(context, resourceId, parkingSpots);
        //Log.d(TAG, "constructor");
        this.context = context;
    }

    private class ViewHolder {
        TextView spotName;
        TextView freeSpace;
        TextView totalSpace;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ParkingSpot parkingSpot = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.parking_spot, null);
            holder = new ViewHolder();
            holder.spotName = (TextView) convertView.findViewById(R.id.textViewSpotName);
            holder.freeSpace = (TextView) convertView.findViewById(R.id.textViewFreeSpace);
            holder.totalSpace = (TextView) convertView.findViewById(R.id.textViewTotalSpace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.spotName.setText(parkingSpot.getName());
        holder.freeSpace.setText("" + parkingSpot.getFreeSpace());
        holder.totalSpace.setText("" + parkingSpot.getTotalSpace());

        return convertView;
    }

}

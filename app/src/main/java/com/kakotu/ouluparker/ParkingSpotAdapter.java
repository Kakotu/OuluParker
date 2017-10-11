package com.kakotu.ouluparker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 7.10.2017.
 */

class ParkingSpotAdapter extends ArrayAdapter<ParkingSpot>{
    private Context context;

    ParkingSpotAdapter(Context context, int resourceId, List<ParkingSpot> parkingSpots){
        super(context, resourceId, parkingSpots);
        this.context = context;
    }

    private class ViewHolder {
        TextView spotName;
        TextView freeSpace;
        TextView address;
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
            holder.address = (TextView) convertView.findViewById(R.id.textViewAddress);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.spotName.setText(parkingSpot.getName());
        holder.freeSpace.setText("" + parkingSpot.getFreeSpace());
        holder.address.setText("" + parkingSpot.getAddress());
        if (position % 2 == 1) {
            convertView.setBackgroundColor(context.getColor(R.color.colorAccent));
            holder.spotName.setTextColor(Color.WHITE);
            holder.freeSpace.setTextColor(Color.WHITE);
            holder.address.setTextColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
            holder.spotName.setTextColor(context.getColor(R.color.colorPrimary));
            holder.freeSpace.setTextColor(context.getColor(R.color.colorPrimary));
            holder.address.setTextColor(context.getColor(R.color.colorPrimary));
        }
        return convertView;
    }

}

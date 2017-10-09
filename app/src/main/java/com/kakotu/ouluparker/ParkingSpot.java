package com.kakotu.ouluparker;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 7.10.2017.
 */

class ParkingSpot {
    //FIXME? id might not be necessary
    private int id;
    private String name;
    private LatLng latLng;
    private int freeSpace;
    private int totalSpace;

    public int getId() {
        return id;
    }

    public LatLng getLatLng() {
        return latLng;
    }


    public String getName() {
        return name;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public ParkingSpot(int id, String name, LatLng latLng, int freeSpace, int totalSpace){
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
    }



}

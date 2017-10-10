package com.kakotu.ouluparker;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 7.10.2017.
 */

class ParkingSpot {
    private String name;
    private String address;
    private int freeSpace;
    private int totalSpace;

    public String getAddress() {
        return address;
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

    public ParkingSpot(String name, String address, int freeSpace, int totalSpace){
        this.name = name;
        this.address = address;
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
    }

    public ParkingSpot(ParkingSpot parkingSpot){
        this.name = parkingSpot.getName();
        this.address = parkingSpot.getAddress();
        this.freeSpace = parkingSpot.getFreeSpace();
        this.totalSpace = parkingSpot.getTotalSpace();
    }


}

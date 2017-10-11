package com.kakotu.ouluparker;


/**
 * Created by Administrator on 7.10.2017.
 */

class ParkingSpot {
    private String name;
    private String address;
    private int freeSpace;
    private int totalSpace;

    String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    int getFreeSpace() {
        return freeSpace;
    }

    private int getTotalSpace() {
        return totalSpace;
    }

    ParkingSpot(String name, String address, int freeSpace, int totalSpace){
        this.name = name;
        this.address = address;
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
    }


}

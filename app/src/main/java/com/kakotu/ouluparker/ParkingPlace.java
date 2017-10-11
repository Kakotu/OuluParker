package com.kakotu.ouluparker;

/**
 * OuluParker
 *
 * Created by Janne on 3.10.2017.
 *
 */

class ParkingPlace {

    private final ParkingSpot spot;

    ParkingPlace(int id, double lat, double lng, String name, ParkingSpot spot) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.spot = spot;
    }

    double getLat() {
        return lat;
    }

    double getLng() {
        return lng;
    }

    private double lat, lng;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ParkingSpot getSpot() {
        return spot;
    }
}

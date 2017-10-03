package com.kakotu.ouluparker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.kakotu.ouluparker.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static int zoomLevelCity = 13;
    private final static int zoomLevelSpot = 16;

    LatLng oulu = new LatLng(65.0123600, 25.4681600);

    private ImageButton buttonParkingPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        buttonParkingPlaces = (ImageButton) findViewById(R.id.buttonParkingPlaces);

        buttonParkingPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(MapsActivity.this, ParkingSpotListActivity.class);
                startActivity(listIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Oulu and move the camera
        mMap.addMarker(new MarkerOptions().position(oulu).title("Marker in Oulu"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(oulu)
                .zoom(zoomLevelCity)
                .build();

        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}

package com.kakotu.ouluparker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.kakotu.ouluparker.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static int zoomLevelCity = 13;
    private final static int zoomLevelSpot = 16;

    LatLng oulu = new LatLng(65.0123600, 25.4681600);

    private ImageButton buttonParkingPlaces;
    private ImageButton buttonRefreshPlaces;

    Engine engine;
    ArrayList<ParkingPlace> allParkingPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        buttonParkingPlaces = (ImageButton) findViewById(R.id.buttonParkingPlaces);
        buttonRefreshPlaces = (ImageButton) findViewById(R.id.buttonRefresh);

        buttonParkingPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(MapsActivity.this, ParkingSpotListActivity.class);
                startActivity(listIntent);
            }
        });

        buttonRefreshPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                new JsonFetchTask().execute();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(oulu)
                .zoom(zoomLevelCity)
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        new JsonFetchTask().execute();

    }


    private class JsonFetchTask extends AsyncTask<Object, Object, int[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            engine = new Engine();
        }

        @Override
        protected int[] doInBackground(Object... objects) {

            try {
                engine.getParkPlaces();
            } catch (Exception e) {
                e.printStackTrace();
            }

            allParkingPlaces = engine.getAllParkingPlaces();

            return null;
        }

        @Override
        protected void onPostExecute(int[] ints) {
            super.onPostExecute(ints);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mMap != null) {
                        for (int i = 0; i < allParkingPlaces.size(); i++) {
                            String name = allParkingPlaces.get(i).getName();
                            LatLng latLng = new LatLng(allParkingPlaces.get(i).getLng(), allParkingPlaces.get(i).getLat());
                            int freeSpaces = allParkingPlaces.get(i).getSpot().getFreeSpace();

                            MarkerOptions mapMarker = new MarkerOptions()
                                    .position(latLng)
                                    .title(name)
                                    .snippet("Vapaata: " + freeSpaces);

                            if (freeSpaces == 0) {
                                mapMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            } else {
                                float[] hsv = new float[3];
                                Color.colorToHSV(getColor(R.color.colorAccent), hsv);
                                mapMarker.icon(BitmapDescriptorFactory.defaultMarker(hsv[0]));
                            }

                            mMap.addMarker(mapMarker);
                        }
                    }

                }
            });
        }
    }


}

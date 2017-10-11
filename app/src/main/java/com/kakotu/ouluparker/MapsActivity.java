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
import android.widget.ProgressBar;

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
    CameraPosition cameraPosition;
    private final static int zoomLevelCity = 14;
    private final static int zoomLevelSpot = 16;

    LatLng oulu = new LatLng(65.0123600, 25.4681600);

    private ImageButton buttonParkingPlaces;
    private ImageButton buttonRefreshPlaces;

    Engine engine;
    ArrayList<ParkingPlace> allParkingPlaces;

    ProgressBar progressBar;

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

        Bundle args = getIntent().getBundleExtra("latLng");
        if (args != null) {
            LatLng lngLat = new LatLng(args.getDouble("lng"), args.getDouble("lat"));

            cameraPosition = new CameraPosition.Builder()
                    .target(lngLat)
                    .zoom(zoomLevelSpot)
                    .build();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        new JsonFetchTask().execute();
        mMap = googleMap;

        if (cameraPosition == null) {
            cameraPosition = new CameraPosition.Builder()
                    .target(oulu)
                    .zoom(zoomLevelCity)
                    .build();
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                } else {
                    // permission denied!
                }
                return;
            }
        }
    }

    private class JsonFetchTask extends AsyncTask<Object, Object, int[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar)findViewById(R.id.progressBar3);
            progressBar.setVisibility(View.VISIBLE);
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
                    progressBar = (ProgressBar)findViewById(R.id.progressBar3);
                    progressBar.setVisibility(View.INVISIBLE);
                    if (mMap != null) {
                        for (int i = 0; i < allParkingPlaces.size(); i++) {
                            String name = allParkingPlaces.get(i).getName();
                            LatLng latLng = new LatLng(allParkingPlaces.get(i).getLng(), allParkingPlaces.get(i).getLat());
                            int freeSpaces = allParkingPlaces.get(i).getSpot().getFreeSpace();

                            MarkerOptions mapMarker = new MarkerOptions()
                                    .position(latLng)
                                    .title(name)
                                    .snippet(getString(R.string.vapaata) + freeSpaces);

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

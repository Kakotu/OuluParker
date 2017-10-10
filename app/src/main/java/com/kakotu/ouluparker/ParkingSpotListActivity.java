package com.kakotu.ouluparker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotListActivity extends Activity {

    ListView parkingSpotList;
    ParkingSpotAdapter parkingSpotAdapter;
    Engine engine;
    ArrayList<ParkingPlace> allParkingPlaces;

    ArrayList<ParkingSpot> allParkingSpots = new ArrayList<>();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spot_list);

        parkingSpotList = (ListView) findViewById(R.id.parkingSpotList);
        parkingSpotAdapter = new ParkingSpotAdapter(getApplicationContext(), R.layout.parking_spot, allParkingSpots);

        new JsonFetchTask().execute();

        parkingSpotList.setAdapter(parkingSpotAdapter);

        parkingSpotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), allParkingSpots.get(position).getName() + " clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });



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
            for (int i = 0; i < allParkingPlaces.size(); i++) {
                allParkingSpots.add(allParkingPlaces.get(i).getSpot());
            }
            return null;
        }

        @Override
        protected void onPostExecute(int[] ints) {
            super.onPostExecute(ints);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar = (ProgressBar)findViewById(R.id.progressBar2);
                    progressBar.setVisibility(View.GONE);
                    parkingSpotAdapter.notifyDataSetChanged();

                }
            });
        }
    }


}

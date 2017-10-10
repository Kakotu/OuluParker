package com.kakotu.ouluparker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotListActivity extends Activity {

    ListView parkingSpotList;
    ParkingSpotAdapter parkingSpotAdapter;

    ArrayList<ParkingSpot> mockUpList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spot_list);

        mockUpList.add(new ParkingSpot("TestiParkki1", "Testitie 1", 10, 20));
        mockUpList.add(new ParkingSpot("TestiParkki2", "Testitie 2", 20, 30));
        mockUpList.add(new ParkingSpot("TestiParkki3", "Testitie 3", 30, 50));
        mockUpList.add(new ParkingSpot("TestiParkki4", "Testitie 4", 40, 60));


        parkingSpotList = (ListView) findViewById(R.id.parkingSpotList);
        //TODO Replace mockUpList with an actual list parsed from the json file
        parkingSpotAdapter = new ParkingSpotAdapter(getApplicationContext(), R.layout.parking_spot, mockUpList);
        parkingSpotList.setAdapter(parkingSpotAdapter);

        parkingSpotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), mockUpList.get(position).getName() + " clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }


}

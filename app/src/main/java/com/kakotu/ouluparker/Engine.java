package com.kakotu.ouluparker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * OuluParker
 * Created by Janne on 3.10.2017.
 */

class Engine {
    private ParkPlaceFetcher parkPlaceFetcher = new ParkPlaceFetcher();

    private boolean dataFetched = false;
    private ArrayList<ParkingPlace> allParkingPlaces = new ArrayList<>();

    public boolean isDataFetched() {
        return dataFetched;
    }

    void setDataFetched(boolean dataFetched) {
        this.dataFetched = dataFetched;
    }

    public ArrayList<ParkingPlace> getAllParkingPlaces() {
        return allParkingPlaces;
    }

    void getParkPlaces() throws JSONException {

        JSONObject jsonData = null;
        try {
            jsonData = new JSONObject(parkPlaceFetcher.fetchParkData("https://www.oulunliikenne.fi/public_traffic_api/parking/parkingstations.php"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonData != null;
        JSONArray jArray = jsonData.getJSONArray("parkingstation");
        //Loop through the jsonArray to put it into arrayList
        for (int i=0; i < jArray.length(); i++)
        {
            try {
                JSONObject onePlace= jArray.getJSONObject(i);
                // Pulling items from the array
                int id = onePlace.getInt("id");
                String location = onePlace.getString("geom");
                String name = onePlace.getString("name");

                String [] split1 = location.split("\\[");
                String[] split2 = split1[1].split("\\]");
                String[] result = split2[0].split(",");

                //JSONObject locationJson  = onePlace.getJSONObject("geom");
                //JSONArray  locationArray = locationJson.getJSONArray("coordinates");
                //jsonData = new JSONObject(location);


                double latitude =  Double.parseDouble(result[0]);
                double longitude =  Double.parseDouble(result[1]);

                ParkingSpot spot = getParkPlaceInfoById(id);

                //Set parking places to Array
                allParkingPlaces.add(new ParkingPlace(id, latitude, longitude, name, spot));
            } catch (JSONException e) {
                //Something went wrong
                e.printStackTrace();
            }
        }
    }

    protected ParkingSpot getParkPlaceInfoById(int id) throws JSONException {

        //Example link https://www.oulunliikenne.fi/public_traffic_api/parking/parking_details.php?parkingid=2
        JSONObject parkingSpot = null;
        try {
            parkingSpot = new JSONObject(parkPlaceFetcher.fetchParkData("https://www.oulunliikenne.fi/public_traffic_api/parking/parking_details.php?parkingid="+id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert parkingSpot != null;
        String name = parkingSpot.getString("name");
        String address = parkingSpot.getString("address");
        int totalspace = Integer.parseInt(parkingSpot.getString("totalspace"));
        int freeSpace = Integer.parseInt(parkingSpot.getString("freespace"));

        return new ParkingSpot(name, address, freeSpace, totalspace);
    }
}

class ParkPlaceFetcher {
    
    private HttpURLConnection connection = null;
    private BufferedReader reader = null;
    private String link = "";


    public String fetchParkData(String link) {
        Engine engine = new Engine();
        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
                Log.d("Buffer: ", "> " + buffer.toString());
            }

            engine.setDataFetched(true);
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

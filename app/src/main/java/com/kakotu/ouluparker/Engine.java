package com.kakotu.ouluparker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * OuluParker
 * Created by Janne on 3.10.2017.
 */

class Engine {
    private ParkPlaceFetcher parkPlaceFetcher = new ParkPlaceFetcher();

    private boolean dataFetched = false;

    public boolean isDataFetched() {
        return dataFetched;
    }

    void setDataFetched(boolean dataFetched) {
        this.dataFetched = dataFetched;
    }

    String getParkPlaces() {
        return parkPlaceFetcher.fetchParkData("https://www.oulunliikenne.fi/public_traffic_api/parking/parkingstations.php");
    }

    protected String getParkPlaceInfoById(String id){
        //Example link https://www.oulunliikenne.fi/public_traffic_api/parking/parking_details.php?parkingid=2
        return parkPlaceFetcher.fetchParkData("https://www.oulunliikenne.fi/public_traffic_api/parking/parking_details.php?parkingid="+id);
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

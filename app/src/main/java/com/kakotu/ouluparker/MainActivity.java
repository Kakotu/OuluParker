package com.kakotu.ouluparker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Engine engine = new Engine();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsonFetchTask().execute();


        //TODO: Create map here
    }

    private class JsonFetchTask extends AsyncTask<Object, Object, int[]> {
        String data;
        ArrayList<String> names = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected int[] doInBackground(Object... objects) {
            try {
                engine.getParkPlaces();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(int[] ints) {
            super.onPostExecute(ints);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = (TextView)findViewById(R.id.textView);
                    for(int i = 0; i < engine.getAllParkingPlaces().size(); i++) {
                        textView.append(engine.getAllParkingPlaces().get(i).getName() + "\n"
                            +engine.getAllParkingPlaces().get(i).getLat() + " "
                                + engine.getAllParkingPlaces().get(i).getLng()+"\n"
                            +engine.getAllParkingPlaces().get(i).getId() + "\n"+ "\n");
                    }


                }
            });
        }
    }

}

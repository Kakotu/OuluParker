package com.kakotu.ouluparker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected int[] doInBackground(Object... objects) {
            data = engine.getParkPlaces();

            return null;
        }

        @Override
        protected void onPostExecute(int[] ints) {
            super.onPostExecute(ints);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = (TextView)findViewById(R.id.textView);
                    textView.setText(data);
                }
            });
        }
    }

}

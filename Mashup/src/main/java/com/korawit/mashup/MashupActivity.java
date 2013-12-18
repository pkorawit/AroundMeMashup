package com.korawit.mashup;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*
*  This application was created for testing concept of manual mashup by using location and GooglePlace API
*  Technical implementation
*  Google Map API v2
*  Google Location Aware Apps
*  Spring Web service client for Android
*  Jackson JSON parser
*  Google Place API : Radar search
*
* */


public class MashupActivity extends Activity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private LatLng currentPosition; // = new LatLng(35.607513, 139.685647);
    LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mashup);

        try {
            // Loading map
            initializeMap();
            locationProvider = new LocationProvider(this);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mashup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {


            currentPosition = new LatLng(locationProvider.getCurrentLocation().getLatitude(), locationProvider.getCurrentLocation().getLongitude());
            Toast.makeText(getApplicationContext(),currentPosition.toString(), Toast.LENGTH_SHORT).show();
            placeMarker(currentPosition,"I'm here");
            setCenterLocation(currentPosition, 16);

            new HttpRequestTask().execute();



            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Get map handler
    private void initializeMap() {
        if (googleMap == null) {

            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Log.d(getApplicationContext().getPackageName(),"Cannot get map fragment");
            }
            else{
                // Set maker click event listener
                googleMap.setOnMarkerClickListener(this);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }


    // Focus map to a specific location
    public void setCenterLocation(LatLng location, float zoomLevel){

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

    }

    // Put a marker to a location
    public void placeMarker(LatLng location, String title){

        googleMap.addMarker(new MarkerOptions().position(location).title(title));

    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(getApplicationContext(),marker.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
        return false;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, PlaceResults> {
        @Override
        protected PlaceResults doInBackground(Void... params) {
            try {
                final String url = String.format("https://maps.googleapis.com/maps/api/place/radarsearch/json?location=%s,%s" +
                        "&radius=500&types=food|cafe&sensor=true&key=AIzaSyCcoCKxREA3yfpdahlCKqJxEP2qHqs9JvQ"
                        ,currentPosition.latitude,currentPosition.longitude);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                PlaceResults results = restTemplate.getForObject(url, PlaceResults.class);
                return results;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(PlaceResults places) {

            List<Results> res = places.getResults();

            for (Results place : res) {

                placeMarker(new LatLng(place.getGeometry().getLocation().getLat().doubleValue(),place.getGeometry().getLocation().getLng().doubleValue()),place.getId());

            }
        }

    }


}

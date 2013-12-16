package com.korawit.mashup;

import android.app.Activity;
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

public class MashupActivity extends Activity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private LatLng currentPosition = new LatLng(35.607513, 139.685647);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mashup);

        try {
            // Loading map
            initializeMap();
            setCenterLocation(currentPosition, 16);
            placeMarker(currentPosition, "I'm here");
            placeMarker(new LatLng(35.603513, 139.685647), "Location 1");
            placeMarker(new LatLng(35.604513, 139.685647), "Location 2");
            placeMarker(new LatLng(35.605513, 139.685647), "Location 3");
            placeMarker(new LatLng(35.606513, 139.685647), "Location 4");

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
                Log.d("debug","Cannot get map fragment");
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
}

package com.korawit.mashup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;


public class LocationUpdateReceiver extends BroadcastReceiver {


    private MashupActivity parent;

    void setParentActivityHandler(MashupActivity parent){

        this.parent = parent;

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String locationKey = LocationClient.KEY_LOCATION_CHANGED;

        if (intent.hasExtra(locationKey)) {

            android.location.Location location = (android.location.Location) intent.getExtras().get(locationKey);

            if(parent != null){

                String msg = "Updated Location(AroundMe Mashup): " +
                        Double.toString(location.getLatitude()) + "," +
                        Double.toString(location.getLongitude());
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


                LatLng newPosition = new LatLng(location.getLatitude(),location.getLongitude());
                parent.UpdatePlaceMarker(newPosition);

            }else{

                String msg = "Parent activity is null";
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }


        }
        else{

            String msg = "Updated Location(AroundMe Mashup): Intent extra not found";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }

    }
}

package com.korawit.mashup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;


public class LocationProvider implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

    private Context context;
    private android.location.Location location;
    private LocationClient locationClient;


    public LocationProvider(Context context){

        this.context = context;
        locationClient= new LocationClient(context,this,this);
        locationClient.connect();
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Log.d("LocationProvider", "Connected");
        location = locationClient.getLastLocation();
        Log.d("LocationProvider",location.toString());
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Log.d("LocationProvider", "Disconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

            Log.d("LocationProvider",connectionResult.toString());
    }

    public void Disconnect(){

        locationClient.disconnect();

    }

    public android.location.Location getCurrentLocation(){

        return location;

    }
}

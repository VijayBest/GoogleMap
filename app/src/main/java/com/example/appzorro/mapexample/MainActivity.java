package com.example.appzorro.mapexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
        , OnMapReadyCallback {

    Toolbar toolbar;

    GoogleApiClient googleApiClient;
    GoogleMap mGoogleMap;
    Location mloctaion;
    double currentlat, currentlang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    // ..................... Here we decare the all wigdt and intilize... and also initilize the googlapiclient class ...........
    public void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Map ");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mloctaion = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.e("on connected", "");
        initCamera(mloctaion);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

 // this line used for set the current location button on your map when you click on this button the camera automatically moves to your current location

        mGoogleMap.setMyLocationEnabled(true);

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

    // this event is call when camera movement is cancel  ..................................

        mGoogleMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                Log.e("camera move","cancel");

            }
        });
  //   this  event is  call when Camera is in idle postion ............................

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                Log.e("camera is ","idle");


            }
        });

  // this event is call when the the  camera movement is started..........................

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                Log.e("camera move","started");

            }
        });

 // this event is call when camera is on move ..........


         mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
             @Override
             public void onCameraMove() {

                 Log.e("camera move","postion");


             }
         });








    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        googleApiClient.disconnect();
    }

 //.. here we are geeting the current location of the user and animate the camera to the current location and add the marker on your location


    private void initCamera(Location mLocation) {

        Log.e("init camera", "fuction are called");

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition position = CameraPosition
                .builder().target(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                .zoom(10f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        currentlat = mLocation.getLatitude();
        currentlang = mLocation.getLongitude();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(currentlat, currentlang));
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(currentlat,currentlang)));




    }



}

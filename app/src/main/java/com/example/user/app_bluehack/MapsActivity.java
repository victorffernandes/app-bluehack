package com.example.user.app_bluehack;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    Location local;
    LocationManager locationManager;
    String mprovider;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        int latitude = (int) (location.getLatitude());
        int longitude = (int) (location.getLongitude());
    }

    public void SendRequest( String origin){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"&destination=CaboFrio&key=AIzaSyCIXvWzLQK5KbOW_h2fRMiWxWmCZqhMN2c";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.i("Rada",obj.getString("routes"));
                        }catch ( JSONException e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mMap = googleMap;
        LatLng sydney = new LatLng(	-22.970722, -43.182365);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(8);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Check if we were successful in obtaining the map.
        if (mMap != null) {


            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {
                    // TODO Auto-generated method stub
                    SendRequest(arg0.getLatitude()+","+ arg0.getLongitude());
                }
            });

        }
    }
}

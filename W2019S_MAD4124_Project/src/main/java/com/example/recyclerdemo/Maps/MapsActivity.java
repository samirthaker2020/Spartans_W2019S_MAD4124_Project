package com.example.recyclerdemo.Maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.recyclerdemo.Database.DatabaseHelper;
import com.example.recyclerdemo.Modal.NoteDetails;
import com.example.recyclerdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private double lati,longi;
private String fulladd;
private int stuff=0;
    ArrayList<NoteDetails> not= new ArrayList<NoteDetails>();
private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
     
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        db = new DatabaseHelper(this);
        if(bundle.containsKey("lati") || bundle.containsKey("longi")||bundle.containsKey("fulladd")){
            lati= bundle.getDouble("lati");
            longi= bundle.getDouble("longi");
            fulladd= bundle.getString("fulladd");
        }
        else {
            stuff=bundle.getInt("cat");

            not.addAll(db.getNoteDetails(stuff));

        }



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
if(stuff==0) {
    // Add a marker in Sydney and move the camera
    LatLng sydney = new LatLng(lati, longi);
    mMap.addMarker(new MarkerOptions().position(sydney).title(fulladd));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));

}else {
    for(NoteDetails n:not)
    {
        System.out.println(n.getLongitude());
        LatLng sydney = new LatLng(n.getLatitude(), n.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(n.getFulldaaress()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 9));
    }
}
    }
}

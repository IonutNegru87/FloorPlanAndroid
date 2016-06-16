package com.adbelsham.HousePlans;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Infrastructure.AppCommon;
import LocationInfra.GPSTracker;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTracker = new GPSTracker(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Add a marker in Sydney and move the camera
        double latitude = AppCommon.getInstance(this).getLatitude();
        double longitude = AppCommon.getInstance(this).getLongitude();

        if (latitude == 0.0 || longitude == 0.0) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }

        LatLng currentPoint = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(currentPoint).title("Floor Plan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPoint, 18.0f));
    }

    @OnClick(R.id.backArrow)
    public void backPress() {
        this.finish();
    }

    @OnClick(R.id.pdfBtnClick)
    public void pdfBtnClick() {
        Intent purchaseActivityIntent = new Intent(this, PurchasePlanActivity.class);
        startActivity(purchaseActivityIntent);
    }
}

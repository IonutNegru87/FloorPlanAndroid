package com.adbelsham.HousePlans;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Location_Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;

    @InjectView(R.id.editTextSearch)
    EditText editTextSearch;

    public Context context = this;
    String mStringLatitude;
    String mStringLongitude;
    String mCompleteAddress;
    String completeAddress;
    double mLatitude;
    double mLongitude;
    Location mLastLocation;
    String enteredAddress;
    LatLng latLng, latitudeLongitude;
    String lat;
    String lon;


    GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        ButterKnife.inject(this);

        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("long");

        if (lat != null && lon != null && !lat.isEmpty() && !lon.isEmpty()) {
            latitudeLongitude = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        editTextSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("enterButtonCalled", "true");
                    enteredAddress = editTextSearch.getText().toString();
                    getLatLongFromAddress(enteredAddress);
                }
                return true;
            }
        });
    }

    @OnClick(R.id.backArrow)
    void setBackArrow(){
        onBackPressed();
    }

    @OnClick(R.id.buttonSave)
    public void doneClick() {
        getDataFromLocationActiviy(mStringLatitude, mStringLongitude, completeAddress);

    }

    private void getDataFromLocationActiviy(String mLatitude, String mLongitude, String s) {
        mCompleteAddress = s;
        mStringLatitude = mLatitude;
        mStringLongitude = mLongitude;
        AppCommon.getInstance(this).setLatitude(mStringLatitude);
        AppCommon.getInstance(this).setLongitude(mStringLongitude);
        AppCommon.showDialog(this, "Location update successfully!");
    }


    private void getLatLongFromAddress(String enteredAddress) {
        Geocoder geocoder = new Geocoder(Location_Activity.this);
        try {
            List<Address> addressesList = geocoder.getFromLocationName(enteredAddress, 5);
            mLatitude = addressesList.get(0).getLatitude();
            mLongitude = addressesList.get(0).getLongitude();
            LatLng latLng = new LatLng(mLatitude, mLongitude);
            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isGooglePlayServicesEnable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            }).show();
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);

        if (latitudeLongitude != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latitudeLongitude));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(9));
           // mMap.addMarker(new MarkerOptions().position(latitudeLongitude));
            new ReverseGeocodingTask().execute(latitudeLongitude);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Location", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Log.v("Location", "Permission is granted");
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.i("" + cameraPosition.target.latitude, "" + cameraPosition.target.longitude);
                new ReverseGeocodingTask().execute(cameraPosition.target);
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mLastLocation != null) {
            //place marker at current position
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        double mLatitude, mLongitude;

        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(context);
            mLatitude = params[0].latitude;
            mLongitude = params[0].longitude;
            mStringLatitude = String.valueOf(mLatitude);
            mStringLongitude = String.valueOf(mLongitude);
            List<Address> addresses = new ArrayList<>();
            completeAddress = "";
            try {
                addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(",");
                }
                sb.append(address.getCountryName());
                completeAddress = sb.toString();
            }

            return completeAddress;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            editTextSearch.setText(s);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onLocationChanged(Location location) {
        mLastLocation = location;


        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {
                    } else {
                        Toast.makeText(this, "Permission Denied, You cannot access location data ", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

    }

}

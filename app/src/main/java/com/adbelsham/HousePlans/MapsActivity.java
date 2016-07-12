package com.adbelsham.HousePlans;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import ApiResponse.PlanData;
import CustomControls.ZoomableDraweeView;
import Infrastructure.AppCommon;
import LocationInfra.GPSTracker;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker gpsTracker;

    @InjectView(R.id.zoomableDraweeView)
    ZoomableDraweeView draweeView;

    @InjectView(R.id.parentRelativeLayout)
    RelativeLayout parentRelativeLayout;

    @InjectView(R.id.lockPlan)
    Button locPlanBtn;

    @InjectView(R.id.lockMap)
    Button lockMapBtn;

    @InjectView(R.id.imageShow)
    Button imageShow;

    PlanData planData;
    SupportMapFragment mapFragment;


    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTracker = new GPSTracker(this);

        Gson gson = new Gson();
        planData = gson.fromJson(getIntent().getExtras().getString("detailObj"), PlanData.class);

        String imageUrl = planData.getPlan_thumb();
        imageUrl = imageUrl.replace(" ", "%20");
        Uri uri = Uri.parse(imageUrl);
        DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(
                uri).setTapToRetryEnabled(true).build();
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();

        draweeView.setController(ctrl);
        draweeView.setHierarchy(hierarchy);

//        draweeView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                final int X = (int) event.getRawX();
//                final int Y = (int) event.getRawY();
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                        _xDelta = X - lParams.leftMargin;
//                        _yDelta = Y - lParams.topMargin;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        break;
//                    case MotionEvent.ACTION_POINTER_UP:
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                        layoutParams.leftMargin = X - _xDelta;
//                        layoutParams.topMargin = Y - _yDelta;
//                        layoutParams.rightMargin = -250;
//                        layoutParams.bottomMargin = -250;
//                        view.setLayoutParams(layoutParams);
//                        break;
//                }
//                //_root.invalidate();
//                return true;
//            }
//        });

//        draweeView.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                switch(event.getAction())
//                {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
//                        Log.d("Test", "Action is DragEvent.ACTION_DRAG_STARTED");
//
//                        // Do nothing
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        Log.d("Test", "Action is DragEvent.ACTION_DRAG_ENTERED");
//                        int x_cord = (int) event.getX();
//                        int y_cord = (int) event.getY();
//                        break;
//
//                    case DragEvent.ACTION_DRAG_EXITED :
//                        Log.d("Test", "Action is DragEvent.ACTION_DRAG_EXITED");
//                        x_cord = (int) event.getX();
//                        y_cord = (int) event.getY();
//                        layoutParams.leftMargin = x_cord;
//                        layoutParams.topMargin = y_cord;
//                        v.setLayoutParams(layoutParams);
//                        break;
//
//                    case DragEvent.ACTION_DRAG_LOCATION  :
//                        Log.d("Test", "Action is DragEvent.ACTION_DRAG_LOCATION");
//                        x_cord = (int) event.getX();
//                        y_cord = (int) event.getY();
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENDED   :
//                        Log.d("Test", "Action is DragEvent.ACTION_DRAG_ENDED");
//
//                        // Do nothing
//                        break;
//
//                    case DragEvent.ACTION_DROP:
//                        Log.d("Test", "ACTION_DROP event");
//
//                        // Do nothing
//                        break;
//                    default: break;
//                }
//                return true;
//            }
//        });
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

    @OnClick(R.id.imageShow)
    public void imageShowClick() {
        if (locPlanBtn.getVisibility() == View.INVISIBLE) {
            draweeView.setVisibility(View.VISIBLE);
            locPlanBtn.setVisibility(View.VISIBLE);
            imageShow.setText("Image Hide");
        } else {
            draweeView.setVisibility(View.GONE);
            locPlanBtn.setVisibility(View.INVISIBLE);
            imageShow.setText("Image Show");
        }
    }

    @OnClick(R.id.lockPlan)
    public void lockPlanBtn() {
        if (draweeView.isTouchEnable) {
            locPlanBtn.setText("Unlock Plan");
            draweeView.isTouchEnable = false;
        } else {
            locPlanBtn.setText("Lock Plan");
            draweeView.isTouchEnable = true;
        }
    }

    @OnClick(R.id.lockMap)
    public void lockMapClick() {
        if (mMap.getUiSettings().isScrollGesturesEnabled()) {
            lockMapBtn.setText("Unlock Map");
            mMap.getUiSettings().setAllGesturesEnabled(false);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
        } else {
            lockMapBtn.setText("Lock Map");
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
        }
    }
}

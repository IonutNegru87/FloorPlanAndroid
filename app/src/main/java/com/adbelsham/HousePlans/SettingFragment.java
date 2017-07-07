package com.adbelsham.HousePlans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapter.SettingAdapter;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SettingFragment extends Fragment {

    @InjectView(R.id.settingRecycleView)
    RecyclerView settingRecycleView;

    SettingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, mView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        settingRecycleView.setLayoutManager(mLinearLayoutManager);

        adapter = new SettingAdapter(getActivity());
        settingRecycleView.setAdapter(adapter);
        return mView;
    }


    public void updateLocation(String latitude, String longitude) {
        if (adapter.gpsTracker != null) {
            AppCommon.getInstance(getActivity()).setLatitude(latitude);
            AppCommon.getInstance(getActivity()).setLongitude(longitude);
//            AppCommon.showDialog(getActivity(), "Location update successfully!");
            Intent locationIntent = new Intent(getContext(), Location_Activity.class);
            locationIntent.putExtra("lat",latitude);
            locationIntent.putExtra("long",longitude);
            startActivity(locationIntent);
            adapter.makeTrackerNil();
        }
    }

}

package com.adbelsham.HousePlans;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    @InjectView(R.id.bedroomSeekBar)
    SeekBar bedRoomSeekBar;

    @InjectView(R.id.bathroomSeekBar)
    SeekBar bathRoomSeekBar;

    @InjectView(R.id.garagesSeekBar)
    SeekBar garagesSeeKBar;

    @InjectView(R.id.toiletsSeekBar)
    SeekBar toiletSeekBar;

    @InjectView(R.id.bedroomTextView)
    TextView bedRoomTextView;

    @InjectView(R.id.bathroomTextView)
    TextView bathRoomTextView;

    @InjectView(R.id.garagesTextView)
    TextView garagesTextView;

    @InjectView(R.id.toiletTextView)
    TextView toiletTextView;

    String bathroomNumber = "1";
    String bedroomNumber = "1";
    String toiletsNumber = "1";
    String garagesNumner = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, mView);
        bedRoomSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
        bathRoomSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
        garagesSeeKBar.setOnSeekBarChangeListener(new SeekBarListener());
        toiletSeekBar.setOnSeekBarChangeListener(new SeekBarListener());

        //bedRoomSeekBar.setProgress(1);
        //bathRoomSeekBar.setProgress(1);
        //toiletSeekBar.setProgress(1);
        return mView;
    }

    @OnClick(R.id.viewPlan)
    public void viewAllPlan() {
        ((HomeActivity) getActivity()).updateFragment(2);
    }

    @OnClick(R.id.searchBtn)
    public void searchBtn() {
        Intent filteredPlanIntent = new Intent(getActivity(), FilteredPlanActivity.class);
        filteredPlanIntent.putExtra("bedroom", bedroomNumber);
        filteredPlanIntent.putExtra("bathroom", bathroomNumber);
        filteredPlanIntent.putExtra("toilets", toiletsNumber);
        filteredPlanIntent.putExtra("garages", garagesNumner);
        getActivity().startActivity(filteredPlanIntent);
    }

    public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

            int index = Integer.parseInt(seekBar.getTag().toString());
            if (index == 1) {
                int value = 1;
                if (progresValue > 60) {
                    value = 4;
                } else if(progresValue > 40){
                    value = 3;
                }else if(progresValue > 20){
                    value =2;
                }else{
                    value = 1;
                }
                bedRoomSeekBar.setProgress(progresValue);
                bedRoomTextView.setText("Bedrooms " + value);
                bedroomNumber = Integer.toString(value);
            } else if (index == 2) {
                int value = 1;

                    if (progresValue == 0) {
                        value = 1;
                    } else if(progresValue > 15) {
                        value = 2;
                    }

                bathRoomSeekBar.setProgress(progresValue);
                bathRoomTextView.setText("Bathrooms " + value);
                bathroomNumber = Integer.toString(value);
            } else if (index == 3) {
                int value = 0;
                if (progresValue > 25) {
                    value = 2;
                } else if (progresValue ==0) {
                    value = 0;
                }else{
                    value= 1;
                }
                garagesSeeKBar.setProgress(progresValue);
                garagesNumner = Integer.toString(value);
                garagesTextView.setText("Garages " + value);
            } else if (index == 4) {
                int value = 1;
                if (progresValue > 40) {
                    value = 3;
                } else if(progresValue>20){
                    value = 2;
                }else{
                    value = 1;
                }
                toiletSeekBar.setProgress(progresValue);
                toiletsNumber = Integer.toString(value);
                toiletTextView.setText("Toilets " + value);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}

package com.adbelsham.HousePlans;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends Fragment {

    @InjectView(R.id.bedroomSeekBar)
    SeekBar bedRoomSeekBar;

    @InjectView(R.id.bedroomTextView)
    TextView bedRoomTextView;

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
        return mView;
    }

    public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
        int progress = 0;

        @Override

        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            progress = progresValue;
            bedRoomTextView.setText("Bedrooms " + progresValue);
        }

        @Override

        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override

        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}

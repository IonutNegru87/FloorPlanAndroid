package com.adbelsham.HousePlans;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PrivacyActivity extends AppCompatActivity {

    @InjectView(R.id.expandableListView)
    ExpandableListView expandbleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.inject(this);

    }

}

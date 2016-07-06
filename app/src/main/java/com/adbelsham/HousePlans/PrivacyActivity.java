package com.adbelsham.HousePlans;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.FaqAdapterExpList;
import Adapter.PrivacyAdapterExpList;
import CustomControls.FaqDataProvideList;
import CustomControls.PrivacyDataProvideList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PrivacyActivity extends AppCompatActivity {

    @InjectView(R.id.expandableListView)
    ExpandableListView expandbleListView;

    List<String> title;
    HashMap<String, List<String>> privacyList;
    PrivacyAdapterExpList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.inject(this);

        privacyList = PrivacyDataProvideList.getInfo(this);
        title = new ArrayList<String>();
        title.add(getResources().getString(R.string.privacy_ques1));
        title.add(getResources().getString(R.string.privacy_ques2));
        title.add(getResources().getString(R.string.privacy_ques3));
        title.add(getResources().getString(R.string.privacy_ques4));
        title.add(getResources().getString(R.string.privacy_ques5));
        title.add(getResources().getString(R.string.privacy_ques6));
        title.add(getResources().getString(R.string.privacy_ques7));
        title.add(getResources().getString(R.string.privacy_ques8));
        adapter = new PrivacyAdapterExpList(this, privacyList, title);
        expandbleListView.setAdapter(adapter);
    }

    @OnClick(R.id.backArrow)
    public void backClick() {
        this.finish();
    }

}

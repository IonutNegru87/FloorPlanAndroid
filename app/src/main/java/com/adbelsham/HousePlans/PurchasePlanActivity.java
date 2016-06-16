package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurchasePlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_plan);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }

}

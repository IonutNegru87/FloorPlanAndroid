package com.adbelsham.HousePlans;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PlansFragment extends Fragment {

    @InjectView(R.id.plansRecycleView)
    RecyclerView planRecycleView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_plans, container, false);
        ButterKnife.inject(this, mView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        planRecycleView.setLayoutManager(mGridLayoutManager);
        return mView;
    }

}

package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import API.FloorPlanService;
import API.ServiceGenerator;
import Adapter.PlansAdapter;
import ApiResponse.PlanData;
import ApiResponse.PlanResponse;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavouritesFragment extends Fragment {

    @InjectView(R.id.favouritesRecycleView)
    RecyclerView favouritesRecycleView;

    List<PlanData> planDataList;

    PlansAdapter plansAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.inject(this, mView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        favouritesRecycleView.setLayoutManager(mGridLayoutManager);
        getFavouritePlan();
        return mView;
    }

    public void getFavouritePlan() {
        FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
        Call call = fp.getFavPlan(AppCommon.getInstance(getActivity()).getUserID());
        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Response response) {
                PlanResponse planResponse = (PlanResponse) response.body();
                if (Boolean.valueOf(planResponse.getSuccess())) {
                    planDataList = planResponse.getPlanDataArrayList();
                    plansAdapter = new PlansAdapter(getActivity(), planDataList);
                    favouritesRecycleView.setAdapter(plansAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}

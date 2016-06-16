package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import API.FloorPlanService;
import API.ServiceGenerator;
import Adapter.PlansAdapter;
import ApiResponse.PlanData;
import ApiResponse.PlanResponse;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilteredPlanActivity extends AppCompatActivity {

    @InjectView(R.id.filteredPlanRecycleView)
    RecyclerView filteredPlanRecycleView;

    List<PlanData> planDataArrayList;
    PlansAdapter plansAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_plan);
        ButterKnife.inject(this);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        filteredPlanRecycleView.setLayoutManager(mGridLayoutManager);
        String bathroom = getIntent().getExtras().getString("bathroom");
        String bedroom = getIntent().getExtras().getString("bedroom");
        String toilets = getIntent().getExtras().getString("toilets");
        String garages = getIntent().getExtras().getString("garages");
        fetchAllPlans(bathroom, bedroom, toilets, garages);
    }

    public void fetchAllPlans(String bathroom, String bedroom, String toilets, String garages) {
        FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
        Call call = fp.searchPlan(bathroom, bedroom, toilets, garages, AppCommon.getInstance(this).getUserID(), Boolean.toString(AppCommon.getInstance(this).isPurchased()));
        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Response response) {
                PlanResponse planResponse = (PlanResponse) response.body();
                if (Boolean.valueOf(planResponse.getSuccess())) {
                    planDataArrayList = planResponse.getPlanDataArrayList();
                    plansAdapter = new PlansAdapter(FilteredPlanActivity.this, planDataArrayList);
                    filteredPlanRecycleView.setAdapter(plansAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @OnClick(R.id.backArrow)
    public void backClick() {
        this.finish();
    }

}

package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import API.FloorPlanService;
import API.ServiceGenerator;
import Adapter.PlansAdapter;
import ApiResponse.PlanData;
import ApiResponse.PlanResponse;
import ApiResponse.PurchaseAppResponse;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlansFragment extends Fragment {

    @InjectView(R.id.plansRecycleView)
    RecyclerView planRecycleView;


    List<PlanData> planDataArrayList;
    PlansAdapter plansAdapter;

    @InjectView(R.id.progressView)
    ProgressBar progressView;

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

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (planDataArrayList.get(position).isShownSubscribeButton())
                    return 2;
                else {
                    return 1;
                }
            }
        });

        fetchAllPlans();
        return mView;
    }


    public void fetchAllPlans() {
        if (AppCommon.isConnectingToInternet(getActivity())) {
            progressView.setVisibility(View.VISIBLE);
            FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
            Call call = fp.searchPlan("", "", "", "", AppCommon.getInstance(getActivity()).getUserID(), Boolean.toString(AppCommon.getInstance(getActivity()).isPurchased()));
            call.enqueue(new Callback<PlanResponse>() {
                @Override
                public void onResponse(Response response) {
                    progressView.setVisibility(View.GONE);
                    if (planDataArrayList != null) {
                        planDataArrayList.clear();
                        planRecycleView.removeAllViews();
                    }
                    PlanResponse planResponse = (PlanResponse) response.body();
                    if (Boolean.valueOf(planResponse.getSuccess())) {
                        planDataArrayList = planResponse.getPlanDataArrayList();
                        if (!AppCommon.getInstance(getActivity()).isPurchased()) {
                            PlanData data = new PlanData();
                            data.setIsShownSubscribeButton(true);
                            planDataArrayList.add(data);
                        }
                        plansAdapter = new PlansAdapter(getActivity(), planDataArrayList);
                        planRecycleView.setAdapter(plansAdapter);
                    } else {
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    progressView.setVisibility(View.GONE);
                }
            });
        } else {
            progressView.setVisibility(View.GONE);
            AppCommon.showDialog(getActivity(), this.getResources().getString(R.string.networkTitle));
        }
    }

    public void refreshDataAfterIAP() {
        if (AppCommon.getInstance(getActivity()).isConnectingToInternet(getActivity())) {
            purchaseApp();
            fetchAllPlans();
        } else {
            AppCommon.getInstance(getActivity()).showDialog(getActivity(), getResources().getString(R.string.networkTitle));
        }
    }

    public void purchaseApp() {
        FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
        Call call = fp.purchaseApp(AppCommon.getInstance(getActivity()).getUserID(), Boolean.toString(AppCommon.getInstance(getActivity()).isPurchased()));
        call.enqueue(new Callback<PurchaseAppResponse>() {
            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}

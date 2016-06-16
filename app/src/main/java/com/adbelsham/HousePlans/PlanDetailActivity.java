package com.adbelsham.HousePlans;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import API.FloorPlanService;
import API.ServiceGenerator;
import Adapter.PlansAdapter;
import ApiResponse.FavouriteResponse;
import ApiResponse.PlanData;
import ApiResponse.PlanResponse;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDetailActivity extends AppCompatActivity {

    @InjectView(R.id.planPic)
    SimpleDraweeView planPic;

    @InjectView(R.id.descTextView)
    TextView descTextView;

    @InjectView(R.id.favouriteBtn)
    Button favouriteBtn;

    PlanData planData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.inject(this);

        Gson gson = new Gson();
        planData = gson.fromJson(getIntent().getExtras().getString("detailObj"), PlanData.class);

        String imageUrl = planData.getPlan_thumb();
        imageUrl = imageUrl.replace(" ", "%20");
        planPic.setController(AppCommon.getDraweeController(planPic, imageUrl, 350));

        descTextView.setText(planData.getDimention());

        if (planData.getIs_favorate() == null || planData.getIs_favorate().equals("1")) {
            favouriteBtn.setVisibility(View.GONE);
        } else {
            favouriteBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }

    @OnClick(R.id.favouriteBtn)
    public void favouriteBtnClick() {
        FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
        Call call = fp.markFavourite(AppCommon.getInstance(this).getUserID(), planData.getId());
        call.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Response response) {
                FavouriteResponse favouriteResponse = (FavouriteResponse) response.body();
                if (!Boolean.valueOf(favouriteResponse.getError())) {
                    AppCommon.showDialog(PlanDetailActivity.this, favouriteResponse.getMsg());
                    favouriteBtn.setVisibility(View.GONE);
                } else {
                    AppCommon.showDialog(PlanDetailActivity.this, favouriteResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @OnClick(R.id.seeLocationBtn)
    public void seeLocationBtn() {
        Gson gson = new Gson();
        String planDataString = gson.toJson(planData);
        Intent mapIntent = new Intent(this, MapsActivity.class);
        mapIntent.putExtra("detailObj", planDataString);
        startActivity(mapIntent);
    }

}

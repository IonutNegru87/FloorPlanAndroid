package com.adbelsham.HousePlans;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import API.FloorPlanService;
import API.ServiceGenerator;
import Adapter.PlansAdapter;
import ApiResponse.FavouriteResponse;
import ApiResponse.PlanData;
import ApiResponse.PlanResponse;
import ApiResponse.PurchaseAppResponse;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDetailActivity extends IAPActivity {

    @InjectView(R.id.planPic)
    SimpleDraweeView planPic;

    @InjectView(R.id.descTextView)
    TextView descTextView;

    @InjectView(R.id.favouriteBtn)
    Button favouriteBtn;

    @InjectView(R.id.progressView)
    ProgressBar progressView;

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
        if (AppCommon.isConnectingToInternet(this)) {
            progressView.setVisibility(View.VISIBLE);
            FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
            Call call = fp.markFavourite(AppCommon.getInstance(this).getUserID(), planData.getId());
            call.enqueue(new Callback<FavouriteResponse>() {
                @Override
                public void onResponse(Response response) {
                    progressView.setVisibility(View.GONE);
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
                    progressView.setVisibility(View.GONE);
                }
            });
        } else {
            progressView.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    @OnClick(R.id.seeLocationBtn)
    public void seeLocationBtn() {
        if (AppCommon.getInstance(this).isPurchased()) {
            Gson gson = new Gson();
            String planDataString = gson.toJson(planData);
            Intent mapIntent = new Intent(this, MapsActivity.class);
            mapIntent.putExtra("detailObj", planDataString);
            startActivity(mapIntent);
        } else {
            setKey(this.getResources().getString(R.string.PURCHASE_APP), this);
        }
    }

    void setData(String pID) {
        if (pID.equals(this.getResources().getString(
                R.string.PURCHASE_PLAN))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.Plan_purchased));
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    AppCommon.getInstance(PlanDetailActivity.this).setIsPurchased(true);
                    Gson gson = new Gson();
                    String planDataString = gson.toJson(planData);
                    Intent mapIntent = new Intent(PlanDetailActivity.this, MapsActivity.class);
                    mapIntent.putExtra("detailObj", planDataString);
                    startActivity(mapIntent);
                    purchaseApp();
                }
            });
            builder.show();
        }
    }

    public void purchaseApp() {
        FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
        Call call = fp.purchaseApp(AppCommon.getInstance(this).getUserID(), Boolean.toString(AppCommon.getInstance(this).isPurchased()));
        call.enqueue(new Callback<PurchaseAppResponse>() {
            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @OnClick(R.id.pdfBtn)
    public void pdfBtnClick() {
        Gson gson = new Gson();
        String planDataString = gson.toJson(planData);
        Intent purchaseActivityIntent = new Intent(this, PurchasePlanActivity.class);
        purchaseActivityIntent.putExtra("detailObj", planDataString);
        startActivity(purchaseActivityIntent);
    }

    @OnClick(R.id.planPic)
    public void picClick() {
        Gson gson = new Gson();
        String planDataString = gson.toJson(planData);
        Intent fullPlanActivityIntent = new Intent(this, FullPlanActivity.class);
        fullPlanActivityIntent.putExtra("detailObj", planDataString);
        startActivity(fullPlanActivityIntent);
    }

}

package com.adbelsham.HousePlans;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.gson.Gson;

import ApiResponse.PlanData;
import CustomControls.ZoomableDraweeView;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FullPlanActivity extends AppCompatActivity {


    @InjectView(R.id.zoomableDraweeView)
    ZoomableDraweeView draweeView;

    PlanData planData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_plan);
        ButterKnife.inject(this);

        Gson gson = new Gson();
        planData = gson.fromJson(getIntent().getExtras().getString("detailObj"), PlanData.class);

        String imageUrl = planData.getPlan_thumb();
        imageUrl = imageUrl.replace(" ", "%20");
        Uri uri = Uri.parse(imageUrl);
        DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(
                uri).setTapToRetryEnabled(true).build();
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setProgressBarImage(new ProgressBarDrawable())
                .build();

        draweeView.setController(ctrl);
        draweeView.setHierarchy(hierarchy);
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }
}

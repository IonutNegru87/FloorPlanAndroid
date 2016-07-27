package Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adbelsham.HousePlans.HomeActivity;
import com.adbelsham.HousePlans.PlanDetailActivity;
import com.adbelsham.HousePlans.PlansFragment;
import com.adbelsham.HousePlans.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ApiResponse.PlanData;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Rohit on 5/13/2016.
 */
public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.DataHolder> {


    Activity activityCtx;
    List<PlanData> planDataArrayList;


    public PlansAdapter(Activity activityCtx, List<PlanData> planDataArrayList) {
        this.activityCtx = activityCtx;
        this.planDataArrayList = planDataArrayList;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_button_row_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_row_item, parent, false);
        }
        return new DataHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        if (holder.viewType != 1) {
            PlanData planData = planDataArrayList.get(position);
            int Width = (int) ((((FragmentActivity) activityCtx).getWindowManager().getDefaultDisplay().getWidth()) * .5);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
            layoutParams.width = Width;
            layoutParams.height = Width;
            holder.planPic.setLayoutParams(layoutParams);
            holder.planPic.requestLayout();
            holder.planPic.setTag(position);
            String imageUrl = planData.getPlan_thumb();
            imageUrl = imageUrl.replace(" ", "%20");

            holder.planPic.setController(AppCommon.getDraweeController(holder.planPic, imageUrl, 150));
        } else {
            holder.subscribeBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE;
        if (planDataArrayList.get(position).isShownSubscribeButton()) {
            VIEW_TYPE = 1;
        } else {
            VIEW_TYPE = 2;
        }
        return VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return this.planDataArrayList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.planPic)
        SimpleDraweeView planPic;

        Button subscribeBtn;

        int viewType;

        public DataHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType != 1) {
                ButterKnife.inject(this, itemView);
            } else {
                subscribeBtn = (Button) itemView.findViewById(R.id.subscribeBtn);
                subscribeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((HomeActivity) activityCtx).setKey(activityCtx.getResources().getString(R.string.PURCHASE_APP));
                    }
                });
            }
        }

        @OnClick(R.id.planPic)
        public void planPicClick(View view) {
            int index = Integer.parseInt(view.getTag().toString());
            PlanData planData = planDataArrayList.get(index);
            Gson gson = new Gson();
            String planDataString = gson.toJson(planData);
            Intent planDataIntent = new Intent(activityCtx, PlanDetailActivity.class);
            planDataIntent.putExtra("detailObj", planDataString);
            activityCtx.startActivity(planDataIntent);
        }
    }
}

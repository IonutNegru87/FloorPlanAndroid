package Adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adbelsham.HousePlans.HomeActivity;
import com.adbelsham.HousePlans.HomeFragment;
import com.adbelsham.HousePlans.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Rohit on 5/13/2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.DataHolder> {

    private List<String> itemData = new ArrayList<String>();
    private List<Integer> itemIcons = new ArrayList<Integer>();
    Activity activityCtx;

    public NavigationDrawerAdapter(Activity activityCtx) {
        this.activityCtx = activityCtx;
        makeData();
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_row_item, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.itemText.setText(itemData.get(position));
        holder.parentLayout.setTag(Integer.toString(position));
        holder.imageView.setImageResource(itemIcons.get(position));
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rowItemText)
        TextView itemText;

        @InjectView(R.id.rowItemIcon)
        ImageView imageView;

        @InjectView(R.id.parentLayout)
        RelativeLayout parentLayout;

        public DataHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.parentLayout)
        public void itemClick(View v) {
            HomeActivity activity = (HomeActivity) activityCtx;
            int pos = Integer.parseInt(v.getTag().toString());
            activity.updateFragment(pos);
        }
    }

    public void makeData() {
        itemData.add("Filter Search");
        itemData.add("Favourites");
        itemData.add("Plans");
        itemData.add("Settings");

        itemIcons.add(R.drawable.search_slider);
        itemIcons.add(R.drawable.favorite_slider);
        itemIcons.add(R.drawable.plans_slider);
        itemIcons.add(R.drawable.settings_slider);
    }
}

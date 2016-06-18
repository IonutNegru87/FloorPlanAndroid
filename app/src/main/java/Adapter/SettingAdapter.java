package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adbelsham.HousePlans.AboutUsActivity;
import com.adbelsham.HousePlans.HomeActivity;
import com.adbelsham.HousePlans.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Rohit on 5/13/2016.
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.DataHolder> {

    private List<String> itemData = new ArrayList<String>();
    private List<Integer> itemIcons = new ArrayList<Integer>();
    Activity activityCtx;

    public SettingAdapter(Activity activityCtx) {
        this.activityCtx = activityCtx;
        makeData();
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_row_item, parent, false);
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
            int pos = Integer.parseInt(v.getTag().toString());
            switch (pos) {
                case 0:
                    break;
                case 1:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, "");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    activityCtx.startActivity(Intent.createChooser(intent, "Send Email"));
                    break;
                case 2:
                    Intent aboutUsIntent = new Intent(activityCtx, AboutUsActivity.class);
                    activityCtx.startActivity(aboutUsIntent);
                    break;
            }
        }
    }

    public void makeData() {
        itemData.add(activityCtx.getResources().getString(R.string.FAQ_TEXT));
        itemData.add(activityCtx.getResources().getString(R.string.Drop_query));
        itemData.add(activityCtx.getResources().getString(R.string.about));
        itemData.add(activityCtx.getResources().getString(R.string.privacy));
        itemData.add(activityCtx.getResources().getString(R.string.change_password));
        itemData.add(activityCtx.getResources().getString(R.string.update_location));
        itemData.add(activityCtx.getResources().getString(R.string.logout));

        itemIcons.add(R.drawable.img_faq);
        itemIcons.add(R.drawable.img_query);
        itemIcons.add(R.drawable.img_about_me);
        itemIcons.add(R.drawable.img_privacy);
        itemIcons.add(R.drawable.img_chng_password);
        itemIcons.add(R.drawable.img_location);
        itemIcons.add(R.drawable.img_logout);
    }
}

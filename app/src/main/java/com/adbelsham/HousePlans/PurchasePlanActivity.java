package com.adbelsham.HousePlans;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ApiResponse.PlanData;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class PurchasePlanActivity extends IAPActivity {

    PlanData planData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_plan);
        ButterKnife.inject(this);

        Gson gson = new Gson();
        planData = gson.fromJson(getIntent().getExtras().getString("detailObj"), PlanData.class);
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }


    @OnClick(R.id.purchasePlanBtn)
    public void purchasePlan() {
        if (planData.getIs_purchase().equals("1")) {
            alert(getResources().getString(R.string.Plan_Already_Purchased));
        } else {
            setKey(getResources().getString(R.string.PURCHASE_PLAN), this);
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
                    new DownloadImage().execute();
                }
            });
            builder.show();
        }
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(PurchasePlanActivity.this, "Downloading...", null, true, true);
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            loading.dismiss();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, planData.getId(), "");
            alert("Download image successfully!");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String add = planData.getPlan_image();
            URL url = null;
            Bitmap image = null;
            try {
                url = new URL(add);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }
    }
}

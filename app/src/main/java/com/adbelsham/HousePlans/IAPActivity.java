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
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ApiResponse.PlanData;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class IAPActivity extends AppCompatActivity {

    public IabHelper mHelper;
    boolean mIsPremium = false;
    String productID;
    static final int RC_REQUEST = 10001;
    Activity ctx;


    public void setKey(String productID, Activity ctx) {
        this.ctx = ctx;
        this.productID = productID;
        String base64EncodedPublicKey = getResources().getString(R.string.IAP_BASE64_String);
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException(
                    "Please put your app's public key in MainActivity.java. See README.");
        }
        if (this.getPackageName().startsWith("com.example")) {
            throw new RuntimeException(
                    "Please change the sample's package name! See README.");
        }
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                if (mHelper == null)
                    return;
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            if (mHelper == null)
                return;
            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Purchase premiumPurchase = inventory.getPurchase(productID);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            onBuy();
        }
    };

    public void onBuy() {
        if (!mIsPremium) {
            String payload = "";
            mHelper.launchPurchaseFlow((Activity) this, this.productID,
                    RC_REQUEST, mPurchaseFinishedListener, payload);
        } else {
            alert("Item is alredy purchased");
            if (ctx instanceof PurchasePlanActivity) {
                ((PurchasePlanActivity) ctx).setData(productID);
            } else if (ctx instanceof PlanDetailActivity) {
                ((PlanDetailActivity) ctx).setData(productID);
            }
        }
    }


    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }
            if (purchase.getSku().equals(productID)) {
                alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                if (ctx instanceof PurchasePlanActivity) {
                    ((PurchasePlanActivity) ctx).setData(purchase.getSku());
                } else if (ctx instanceof PlanDetailActivity) {
                    ((PlanDetailActivity) ctx).setData(purchase.getSku());
                }
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            if (result.isSuccess()) {

            } else {
                complain("Error while consuming: " + result);
            }
        }
    };

    void complain(String message) {

        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {

        }
    }
}

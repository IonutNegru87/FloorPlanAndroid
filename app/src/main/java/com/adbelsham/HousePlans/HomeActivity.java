package com.adbelsham.HousePlans;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;


import Adapter.NavigationDrawerAdapter;
import Infrastructure.AppCommon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.left_drawer_recycleView)
    RecyclerView recyclerView;
    public IabHelper mHelper;
    boolean mIsPremium = false;
    String TAG = "HE";
    static final int RC_REQUEST = 10001;
    String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this);
        recyclerView.setAdapter(adapter);
        updateFragment(0);

    }

    @OnClick(R.id.menuBtn)
    public void menuClick() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void updateFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                Fragment fragment = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                break;
            case 1:
                Fragment favouriteFragment = new FavouritesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, favouriteFragment)
                        .commit();
                break;
            case 2:
                Fragment planFragment = new PlansFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, planFragment)
                        .commit();
                break;
            case 3:
                Fragment settingFragment = new SettingFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, settingFragment)
                        .commit();
                break;
        }

        drawerLayout.closeDrawer(Gravity.LEFT);
    }


    public void setKey(String productID) {
        this.productID = productID;
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiEtSTDk+z+jKZK+LZ0ZpqKyhilOd6H/xY05nnd1GAYv8imYjkOfMuCtnbXOYl2IsoNnNJ3+ABgVBHKk0+EbRHt3dAYUVQLSifIV79UcIcoLK8Bc7ZMfjHC1n0FChLo/5NLJHbotJPnVjJd6zB+OcquvmIRGE2dpTAz4PiCiHLrUIJT44+rIoFnBvK1oXpyFxG0C0rVdN/JoD7862WyM/CDXLihsGTy5rl2N3JQikJ1bHZAD3ES3OgQNAoCNzA8Ag7gRHJbF2fH+KHkz0MW20BqbTOEgEzKSAU7ycV5V3Z19DhFal2j1/kNoN4OGJrtOUn+5IWqLwJuaYNWj6kBXmJwIDAQAB";
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
                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                if (mHelper == null)
                    return;
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Listener that's called when we finish querying the items and
    // subscriptions we own
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
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
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
            setData(productID);
        }
    }

    void setData(String pID) {
        if (pID.equals(this.getResources().getString(
                R.string.PURCHASE_APP))) {
            AppCommon.getInstance(this).setIsPurchased(true);
            //((PlansFragment) this.fragmentCtx).refreshDataAfterIAP();
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: "
                    + purchase);
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

            Log.d(TAG, "Purchase successful.");
            if (purchase.getSku().equals(productID)) {
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                setData(purchase.getSku());
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase
                    + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            // We know this is the "gas" sku because it's the only one we
            // consume,
            // so we don't check which sku was consumed. If you have more than
            // one
            // sku, you probably should check...
            if (result.isSuccess()) {

            } else {
                complain("Error while consuming: " + result);
            }
            Log.d(TAG, "End consumption flow.");
        }
    };

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {

        }
    }
}

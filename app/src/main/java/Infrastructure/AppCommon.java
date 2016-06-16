package Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppCommon {
    public static AppCommon mInstance = null;

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);


    static Context mContext;

    //Meteor mMeteor;
    public static AppCommon getInstance(Context _Context) {
        if (mInstance == null) {
            mInstance = new AppCommon();
        }
        mContext = _Context;
        return mInstance;
    }

    public static int dipToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public void setUserID(String userID) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.USER_ID, userID);
        mEditor.commit();
    }

    public String getUserID() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        return mSharedPreferences.getString(MYPerference.USER_ID, "");
    }

    public boolean isLogin() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        return mSharedPreferences.getBoolean(MYPerference.IS_LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.IS_LOGIN, isLogin);
        mEditor.commit();
    }

    public boolean isPurchased() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        return mSharedPreferences.getBoolean(MYPerference.IS_PURCHASED, false);
    }

    public void setIsPurchased(boolean isPurchased) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.IS_PURCHASED, isPurchased);
        mEditor.commit();
    }

    public void setLatitude(String latitude) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.LATITUDE, latitude);
        mEditor.commit();
    }

    public double getLatitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        String latitude = mSharedPreferences.getString(MYPerference.LATITUDE, "");
        if (!latitude.equals("")) {
            return Double.parseDouble(latitude);
        } else {
            return 0.0;
        }
    }

    public void setLongitude(String longitude) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.LONGITUDE, longitude);
        mEditor.commit();
    }

    public double getLongitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        String longitude = mSharedPreferences.getString(MYPerference.LONGITUDE, "");
        if (!longitude.equals("")) {
            return Double.parseDouble(longitude);
        } else {
            return 0.0;
        }
    }

    public static void ClearSharedPreference() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_WORLD_READABLE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }

    public static void showDialog(Activity mactivity, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mactivity);
        builder.setTitle(title);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static DraweeController getDraweeController(DraweeView imageView, String imageUrl, int size) {
        Uri uri = Uri.parse(imageUrl);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(size, size))
                .setProgressiveRenderingEnabled(false)
                .build();

        return Fresco.newDraweeControllerBuilder()
                .setOldController(imageView.getController())
                .setImageRequest(request)
                .build();
    }
}

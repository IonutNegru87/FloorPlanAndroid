package Infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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
}
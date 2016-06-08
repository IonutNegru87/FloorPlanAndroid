package Infrastructure;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import android.util.Patterns;
import android.util.TypedValue;



import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by meteosoft on 15/01/16.
 */
public class Utils {

    

    public static int maximumNoOFDaysAgo = 6;
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String Uplaodfilename() {
        String uuid = (UUID.randomUUID().toString()).replaceAll("-", "k");
        Calendar calendar = Calendar.getInstance();
        Long Milesec = System.currentTimeMillis();
        int date = calendar.get(Calendar.DATE);
        int time = calendar.get(Calendar.HOUR);
        int mint = calendar.get(Calendar.MINUTE);
        return uuid + "" + Milesec + "" + date + "" + time + "" + mint + "" + "";

    }

    @SuppressLint("NewApi")
    public static String getPathnew(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static String gettime(String time) {
        SimpleDateFormat dateformat = AppCommon.formatter;
        dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String parseDateStr = "";

        Date date = new Date();
        date.setTime(Long.parseLong(time));
        //parseDate = dateformat.format(date);

        // String timeString = getTimeAgoFromDate(parseDate);
        return "";

    }

    public static String getTimeAgoFromDate(long milliSecond) {
        long timeSinceDate = (System.currentTimeMillis() - milliSecond);
        if (timeSinceDate < 0) {
            timeSinceDate = 0;
        }
        int min = 60 * 1000;
        int hour = 60 * min;
        int day = 24 * hour;
        int sec = 1000;
        // print up to 24 hours as a relative offset
        if (timeSinceDate < day) {
            int hoursSinceDate = Math.round(timeSinceDate / hour);
            if (hoursSinceDate < 1) {
                int minutesSinceDate = Math.round(timeSinceDate / min);
                if (minutesSinceDate < 1) {
                    int secSinceDate = Math.round(timeSinceDate / sec);
                    String second = "";
                    if (secSinceDate > 1) {
                        second = " Now";
                    } else {
                        second = " Now";
                    }
                    return "" + second;
                } else if (minutesSinceDate < 2) {
                    return 1 + " minute ago";
                } else {
                    return minutesSinceDate + " minutes ago";
                }
            } else {
                String time_hour = "";
                if (hoursSinceDate > 1) {
                    time_hour = " hours ago";
                } else {
                    time_hour = " hour ago";
                }

                return hoursSinceDate + "" + time_hour;
            }
        } else {
            Date date = new Date();
            date.setTime(milliSecond);
            return getDaysAgoFromDate(AppCommon.formatter.format(date));
        }
    }

    public static String getDaysAgoFromDate(String date) {
        try {
            Date dateTime = formatter.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            Calendar today = Calendar.getInstance();
            Date currentDate = today.getTime();
            long noOFDaysLeft = (currentDate.getTime() - dateTime.getTime()) / 86400000l;
            if (noOFDaysLeft <= maximumNoOFDaysAgo || noOFDaysLeft >= 0) {
                if (noOFDaysLeft == 0) {
                    return " Today";
                } else if (noOFDaysLeft == 1) {
                    return " Yesterday";
                } else {
                    String time_day = "";
                    if (noOFDaysLeft > 1) {
                        time_day = " days ago";
                    } else {
                        time_day = " day ago";
                    }
                    return noOFDaysLeft + "" + time_day;
                }
            } else {
                return date;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int dipToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int spToPx(Context context, float spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }

}

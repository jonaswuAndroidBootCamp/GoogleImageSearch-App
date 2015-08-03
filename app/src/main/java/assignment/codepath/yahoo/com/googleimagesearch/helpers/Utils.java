package assignment.codepath.yahoo.com.googleimagesearch.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jonaswu on 2015/8/2.
 */
public class Utils {
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static float convertPixelsToDp(String px, Context context) {
        Float floatPx = Float.valueOf(px);
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = floatPx / (metrics.densityDpi / 160f);
        return dp;
    }

    public static String getImagzStringParamsOfGoogleImageAPI(JSONObject sizeFilter) {
        String res = "";
        try {
            ArrayList<String> sizeFilterArray = new ArrayList<String>();
            if (sizeFilter.getBoolean("isSmall") == true) {
                sizeFilterArray.add("small");
            }
            if (sizeFilter.getBoolean("isMedium") == true) {
                sizeFilterArray.add("medium");
            }
            if (sizeFilter.getBoolean("isLarge") == true) {
                sizeFilterArray.add("large");
            }
            if (sizeFilter.getBoolean("isExtremeLarge") == true) {
                sizeFilterArray.add("xlarge");
            }
            res = StringUtils.join(sizeFilterArray.toArray(), "|");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("getImagzStringParamsOfGoogleImageAPI", res);
        return res;
    }

    public static void sendMailWithImageViewAsAttachment(Context context, ImageView imgageView, String FILENAME, String imageUrl) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), FILENAME), true);
            Bitmap myBitmap = imgageView.getDrawingCache();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.close();
            String message = "Image Url: " + imageUrl;
            String[] recipients = new String[]{""};
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Week 2 Assignment: Google Image Image Sharing");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(context.getFilesDir(), FILENAME)));     //this line is added to your code
            try {
                context.startActivity(Intent.createChooser(emailIntent, "CHOOSE EMAIL CLIENT"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "NO APP TO HANDLE THIS", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

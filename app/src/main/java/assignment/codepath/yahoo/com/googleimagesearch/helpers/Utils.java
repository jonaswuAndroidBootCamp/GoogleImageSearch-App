package assignment.codepath.yahoo.com.googleimagesearch.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
}

package baidumapsdk.demo.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by net on 2018/5/18.
 */

public class Util {
    private static final double EARTH_RADIUS = 6378137.0;//地球半径
    private static Context mContext;
    /**
     * 计算两个经纬度之间距离
     * @return s 以米为单位的距离
     */
    public static double getDistances(double latitude1,double longitude1,double latitude2,
                                      double longitude2 ) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static boolean checkPackage(Context context,String packageName) {
        if (packageName == null || "".equals(packageName)) return false;

        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }


    //判断浮点数（double和float）
    public static boolean isDoubleOrFloat(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static Context getApplicationContext(){
        return mContext;
    }

    public static void setContext(Context context){
        mContext = context;
    }
}

package code_base.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by data on 2017/9/1.
 * 设备信息获取工具类
 */

public class DeviceUtils {

    private static String tag = "DeviceUtils";

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static String getSDKVersion() {
        return Build.VERSION.SDK_INT + "";
    }

    /**
     * 返回CPU信息
     *
     * @return String
     */

    public static String getCpuInfos() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            @SuppressWarnings("resource")
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return strInfo;
    }

    /**
     * 返回CPU类型
     *
     * @return String
     */

    public static String getCpuType() {
        String strInfo = getCpuInfos();
        String strType = null;

        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }
        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }

        return strType;
    }

    /**
     * 返回CPU ABI
     *
     * @returnString
     */
    public static String getCPU_ABI() {
        return Build.CPU_ABI;
    }


    /**
     * 返回OS版本号
     *
     * @return String;
     */
    public static String getReleaseVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 返回设备名
     *
     * @return String;
     */
    public static String getDeviceName() {
        return Build.DEVICE;
    }

    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * 返回OS名
     *
     * @return String;
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 返回Mac地址
     *
     * @param context
     * @return String
     */
    public static String getMac(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String mac = wifi.getConnectionInfo().getMacAddress();
        return mac;
    }

    /**
     * 获取摄像头数量
     *
     * @return int
     */
    public static int getCameraNumber() {
        return Camera.getNumberOfCameras();
    }

    /**
     * 得到手机IMEI
     *
     * @param context
     * @return String
     */
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return int[widthPixels, heightPixels]
     */
    public static int[] getScreenSize(Context context) {
        int[] screens;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screens = new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static double getScreenInches(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        double x = Math.pow(point.x/ dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);

        Log.i(tag,"density "+dm.density);
        Log.i(tag,"densityDpi "+dm.densityDpi);
        Log.i(tag,"scaledDensity "+dm.scaledDensity);
        double PPI = Math.sqrt(Math.pow(dm.xdpi,2)+Math.pow(dm.ydpi,2));
        Log.i(tag,"ppi "+PPI);
        Log.i(tag,"real size "+point.x+"-"+point.y);
        Log.i(tag, "Screen inches : " + screenInches);
        return screenInches;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return float
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return metrics.density;
    }

    public static float getScreenScaleDensity(Context context) {
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return metrics.scaledDensity;
    }

    public static float getDPI(Context context) {
        DisplayMetrics metrics = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return metrics.densityDpi;
    }

    /**
     * 判断是否是平板电脑
     *
     * @param context
     * @return boolean
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获得屏幕尺寸 如4.5寸
     *
     * @param ctx
     * @return
     */
    public static double getScreenPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
                + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

    /**
     * 判断是否存在SD卡
     *
     * @return boolean
     */
    public static boolean hasSDcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}

package baidumapsdk.demo.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import baidumapsdk.demo.R;

/**
 * Created by net on 2018/5/18.
 */

public class Util {
    private static final double EARTH_RADIUS = 6378137.0;//地球半径
    private static Context mContext;
    static String TAG = "Util";
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

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(final float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(final float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static void showLocatePermissionTip(Context context){
        //在设置-应用-appname-权限中开启位置信息权限，以正常使用查看位置信息、推荐地点等功能（地图定位相关的App功能）
        showPermissionTip(context,"在设置-应用-"+"approot"
                +"-权限中开启位置信息权限，以正常使用查看位置信息、推荐地点等功能");
    }

    public static void showPermissionTip(final Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("权限申请");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goToSettingsForRequestPermission(context,context.getPackageName());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static void goToSettingsForRequestPermission(Context context,String applicationId) {
        Log.d(TAG, "goToSettingsForRequestPermission: manufacturer is "+getManufacturer());
        Intent intent = new Intent();
        try {
            switch (getManufacturer()) {

                case "HUAWEI":

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
                    intent.setComponent(comp);
                    break;
                case "Meizu":
                    intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra("packageName", applicationId);
                    break;
                case "Sony":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName compSony = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
                    intent.setComponent(compSony);
                    break;
                case "OPPO":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName compOPPO = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                    intent.setComponent(compOPPO);
                    break;
                case "LG":
                    intent.setAction("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName compLG = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                    intent.setComponent(compLG);
                    break;
                case "Letv":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName compLe = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
                    intent.setComponent(compLe);
                    break;
                case "QiKU":
                    intent.setAction("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", applicationId);
                    ComponentName comp360 = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
                    intent.setComponent(comp360);
                    break;
                case "vivo":
                    intent.putExtra("packagename", applicationId);
                    ComponentName comVivo = ComponentName.unflattenFromString("com.vivo.permissionmanager/.activity.SoftPermissionDetailActivity");
                    intent.setComponent(comVivo);
                case "Xiaomi":
                    int rom = getMiuiVersion();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d(TAG, "goToSettingsForRequestPermission: rom is "+rom);
                    if (rom == 5) {
                        Uri packageURI = Uri.parse("package:" + context.getApplicationInfo().packageName);
                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);

                    } else if (rom == 6 || rom == 7) {
                        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
//                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        intent.setComponent(componentName);
                        intent.putExtra("extra_pkgname", context.getPackageName());
                    }
                    else if (rom >= 8){
                        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", applicationId);
                    }
                    break;
                default:
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri packageURI = Uri.parse("package:" + context.getPackageName());
                    intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    break;
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "goToSettingsForRequestPermission: "+e.getMessage() );
            Uri packageURI = Uri.parse("package:" + context.getPackageName());
            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            context.startActivity(i);
        }
    }

    public static int getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + version);
            }
        }
        return -1;
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }
}

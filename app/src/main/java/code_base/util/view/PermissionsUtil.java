package code_base.util.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import code_base.AppConstants;
import code_base.listener.PermissionResultListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.reactivex.functions.Consumer;


/**
 * Created by data on 2017/8/22.
 * 收集各大手机厂商权限页
 */

public class PermissionsUtil {
    private static final String TAG = "PermissionsUtil";
    private RxPermissions rxPermissions;


    public PermissionsUtil(Activity context){
        rxPermissions = new RxPermissions(context);
    }

    public void requestPermission(final PermissionResultListener listener, final String... permissions) {

        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        listener.onHandlePermissionResult(granted);

                    }
                });
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

    public static void goToOpenAutoStart(Context context,String applicationId) {
        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packagename", applicationId);
        ComponentName comVivo = ComponentName.unflattenFromString("com.vivo.permissionmanager/.activity.SoftPermissionDetailActivity");
//        ComponentName comVivo = new ComponentName("com.vivo.permissionmanager","com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        intent.setComponent(comVivo);
        context.startActivity(intent);
    }


//    华为——HUAWEI
//    魅族——Meizu
//    小米——Xiaomi
//    索尼——Sony
//    oppo——OPPO
//    LG——LG
//    vivo——vivo
//    三星——samsung
//    乐视——Letv
//    中兴——ZTE
//    酷派——YuLong
//    联想——LENOVO
}

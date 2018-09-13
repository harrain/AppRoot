package code_base.module.downloadmanager.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by maimingliang on 2016/10/8.
 * APP版本升级常量
 */

public class CommonCons {


    public static String SAVE_APP_NAME = System.currentTimeMillis()+".apk";

    public final static String SAVE_APP_LOCATION = Environment.DIRECTORY_DOWNLOADS;


    public final static String APP_FILE_NAME = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ File.separator + SAVE_APP_NAME;
    public static final String DOWNLOAD_APK_ID_PREFS = "download_apk_id_prefs";
}

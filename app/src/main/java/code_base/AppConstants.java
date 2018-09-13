package code_base;

import android.os.Environment;

import com.damon.approot.R;

import code_base.util.Utils;

public class AppConstants {

    public static final String FILEPROVIDER_AUTHORITY = "app.content.FileProvider";
    public static boolean LOCALE_CHANGE = false;//系统语言改变 标志位
    public static boolean RESTARTED_MAIN = false;

    public static final String CRASH_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + Utils.getContext().getResources().getString(R.string.app_name)+"/crashes" ;



    public static final String Glide_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" +Utils.getContext().getResources().getString(R.string.app_name)+ "/glide";

    public static final String QRCODE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" +Utils.getContext().getResources().getString(R.string.app_name)+ "/qrcode/";
}

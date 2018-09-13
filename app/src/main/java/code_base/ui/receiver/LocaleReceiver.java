package code_base.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;


import java.util.Locale;

import code_base.AppConstants;

/**
 * 系统语言改变监听者
 */
public class LocaleReceiver extends BroadcastReceiver {
    String tag = "LocaleReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

//        internationalization(context);

        AppConstants.LOCALE_CHANGE = true;//全局变量改变
        Log.i(tag, "lang onReceive: locale change");
    }

    public void internationalization(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        String lang = config.locale.getLanguage();

        if (!lang.equals("zh_CN") && !lang.equals("en")){
//            config.setLocale(Locale.ENGLISH);
            config.locale = new Locale("en");
        }

        resources.updateConfiguration(config, dm);
        Log.i(tag,"lang "+config.locale.getLanguage());
    }
}

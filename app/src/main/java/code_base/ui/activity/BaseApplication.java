package code_base.ui.activity;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import code_base.util.LogUtils;
import code_base.util.ToastDebug;
import code_base.util.ToastUtil;
import code_base.util.Utils;

public class BaseApplication extends MultiDexApplication {

    String tag = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag,"onCreate");
        LogUtils.init(this,"AppRoot", Utils.isDebug());
        ToastDebug.init(this,Utils.isDebug());
        ToastUtil.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(tag,"onConfigurationChanged: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(tag, "onLowMemory: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(tag, "onTrimMemory: ");
    }
}

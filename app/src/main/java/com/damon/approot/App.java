package com.damon.approot;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import baidumapsdk.demo.BaiduMapSDK;
import code_base.util.LogUtils;
import code_base.util.ToastDebug;
import code_base.util.ToastUtil;
import code_base.util.Utils;

public class App extends MultiDexApplication {

    String tag = "App";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag,"onCreate");

        LogUtils.init(this,"AppRoot",BuildConfig.DEBUG);
        Utils.init(this,BuildConfig.DEBUG);
        ToastDebug.init(this,BuildConfig.DEBUG);
        ToastUtil.init(this);

        BaiduMapSDK.init(this);//初始化百度地图SDK
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

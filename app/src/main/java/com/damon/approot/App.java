package com.damon.approot;

import android.support.multidex.MultiDexApplication;

import baidumapsdk.demo.BaiduMapSDK;
import code_base.util.LogUtils;
import code_base.util.ToastDebug;
import code_base.util.ToastUtil;
import code_base.util.Utils;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.init(this,"AppRoot",BuildConfig.DEBUG);
        Utils.init(this,BuildConfig.DEBUG);
        ToastDebug.init(this,BuildConfig.DEBUG);
        ToastUtil.init(this);

        BaiduMapSDK.init(this);//初始化百度地图SDK
    }
}

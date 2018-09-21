package com.damon.approot;


import com.example.greendao.GreenDaoSDK;

import baidumapsdk.demo.BaiduMapSDK;
import code_base.ui.activity.BaseApplication;
import code_base.util.Utils;

public class App extends BaseApplication {


    @Override
    public void onCreate() {
        Utils.init(this,BuildConfig.DEBUG);
        super.onCreate();

        GreenDaoSDK.init(this);
        BaiduMapSDK.init(this);//初始化百度地图SDK
    }


}

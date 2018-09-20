package baidumapsdk.demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import baidumapsdk.demo.helper.Util;

/**
 * Created by data on 2017/9/26.
 * 百度地图SDK初始化引擎
 */

public class BaiduMapSDK {

    private static BaiduMapSDK instance;
    private Context mContext;
    /**
     * 调用该方法进行初始化 BaiduMapSDK.init(applicationcontext)
     */
    public static void init(Context context){
        if (instance == null){

            instance = new BaiduMapSDK();
            instance.initialBaiduMap(context);
            instance.mContext = context;
            Util.setContext(context);
        }
    }
    /**
     * baiduMap 初始化 API
     */
    private void initialBaiduMap(Context context){
//        try {
//            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
//
//            applicationInfo.metaData.putString("com.baidu.lbsapi.API_KEY","81xPybmcXKiDGZu1s5MFzO2IbMmrwOaE");//这样做无效
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(context);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
//                initTrace(context);
            }
        }
    }



    public  Context getContext(){
        return mContext;
    }

    public static BaiduMapSDK getInstance(){
        return instance;
    }
}

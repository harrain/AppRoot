package baidumapsdk.demo.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import baidumapsdk.demo.activity.MarkerInfoWindowActivity;
import io.reactivex.functions.Consumer;

/**
 * 定位单例类
 * 需要Manifest.permisstion.ACCESS_FINE_LOCATION权限， Android6.0须动态获取
 */

public class LocationRequest {

    private Context mContext;
    private LocationFormatListener mListener;
    private LocationInfoListener locationInfoListener;
    private OnLocateConditionListener mOnLocateConditionListener;
    private static LocationRequest instance;
    private String tag = "LocationRequest";

    /**
     * 一次定位构造函数
     */
    public LocationRequest(Context context){
        mContext = context;
        mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型
        mLocClient.setLocOption(option);
        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocClient.setLocOption(option);
    }
    /**
     * 持续间隔特点时间定位
     */
    public LocationRequest(Context context,int seconds){
        mContext = context;
        mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型
        option.setScanSpan(seconds*1000);//定位时间间隔   //默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocClient.setLocOption(option);
        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocClient.setLocOption(option);
    }

    
    /**
     * 一次定位获取单例
     * @param context application的context
     */
    public static LocationRequest getInstance(Context context) {
        if (instance == null) {
            synchronized (LocationRequest.class){
                if (instance == null)instance = new LocationRequest(context);
            }
        }
        return instance;
    }

    public void requestPermissionToLocate(final Activity activity, final LocationInfoListener listener){
        if (mOnLocateConditionListener != null && !mOnLocateConditionListener.canLocate()) {
            return;
        }
        mOnLocateConditionListener = null;

        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted){
                    //允许
                    startLocate(listener);
                }else if (permission.shouldShowRequestPermissionRationale){
                    //拒绝本次权限申请
                    Log.e(tag,"拒绝本次定位权限申请");
                }else {
                    //禁止该项权限
                    Log.e(tag,"禁止定位权限");
                    Util.showLocatePermissionTip(activity);
                }
            }
        });
    }

    /**
     * 持续定位获取单例
     * @param context application的context
     * @param seconds 持续定位间隔的时间
     */
    public static LocationRequest getInstance(Context context,int seconds) {
        if (instance == null) {
            synchronized (LocationRequest.class){
                if (instance == null)instance = new LocationRequest(context,seconds);
            }
        }
        return instance;
    }

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private String mCurrentLat;
    private String mCurrentLon;
    private String mCurrentAccracy;

    public void startLocate(LocationFormatListener locationListener){

        mListener = locationListener;

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocClient.start();
    }

    public void startLocate(LocationInfoListener locationListener){
        if (mOnLocateConditionListener != null && !mOnLocateConditionListener.canLocate()) {//可通过该接口方法在定位前做准备条件，如果返回true，向下执行
            return;
        }
        mOnLocateConditionListener = null;

        locationInfoListener = locationListener;

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocClient.start();
//        mLocClient.requestLocation();
    }

    /**
     * 定位SDK监听函数, 需实现BDLocationListener里的方法
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            mCurrentLat = String.valueOf(location.getLatitude());
            mCurrentLon = String.valueOf(location.getLongitude());
            mCurrentAccracy = String.valueOf(location.getRadius());
            Log.i(tag, "onReceiveLocation: "+location.getLatitude()+location.getLongitude());
            if (mCurrentLat.contains("E") || mCurrentLon.contains("E")){//过滤4.9E-324 4.9E-324 异常数据的情况
                Log.e(tag, "onReceiveLocation: 无法获取到正常 位置信息，可能SDK配置有问题或定位权限被系统禁止" );
                return;
            }
            if (mListener!=null)mListener.onLocate(mCurrentLat,mCurrentLon);
            if (locationInfoListener!=null) locationInfoListener.onLocationInfo(location.getLatitude(),location.getLongitude(),location.getRadius());
        }
    }
    /**
     * 实现接口，可以在定位前做准备工作，并决定之后的定位是否执行
     */
    public LocationRequest prepareCondition(OnLocateConditionListener listener){
        mOnLocateConditionListener = listener;
        return this;
    }

    private void releaseLocate(){
        // 退出时销毁定位
        mLocClient.stop();
        mListener = null;
        locationInfoListener = null;
//        instance = null;
    }
    /**
     * 释放定位资源
     */
    public static void release(){
        if (instance!=null) instance.releaseLocate();
    }

    public interface LocationFormatListener {
        void onLocate(String latitude, String longtitude);
    }

    public interface LocationInfoListener{
        void onLocationInfo(double lat,double lng,float radius);
    }

    public interface OnLocateConditionListener{
        boolean canLocate();//true 可以定位； false 不可以定位
    }
}

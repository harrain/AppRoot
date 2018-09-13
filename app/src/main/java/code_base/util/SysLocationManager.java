package code_base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by net on 2018/3/6.
 * 系统定位工具类
 */

public class SysLocationManager {

    private static SysLocationManager instance;
    LocationManager lm;
    MyLocationListener mLocationListener;
    private String provider;
    SimpleSysLocationListener simpleSysLocationListener;
    private String tag = "SysLocationManager";


    private SysLocationManager() {
        lm = (LocationManager) Utils.getContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();

        Criteria crite = new Criteria();
        crite.setAccuracy(Criteria.ACCURACY_FINE); //精度
        crite.setPowerRequirement(Criteria.POWER_HIGH); //功耗类型选择
        provider = lm.getBestProvider(crite, false);

    }

    /**
     * 系统定位开启
     */
    @SuppressLint("MissingPermission")
    public void startLocate(SimpleSysLocationListener listener) {
        Log.i(tag, "startLocate: ");
        simpleSysLocationListener = listener;
        lm.requestLocationUpdates(provider, 0, 0, mLocationListener);
    }

    public static SysLocationManager getInstance() {
        if (instance == null) {
            synchronized (SysLocationManager.class) {
                if (instance == null) instance = new SysLocationManager();
            }
        }
        return instance;
    }

    class MyLocationListener implements LocationListener {
        // 当位置发生变化 执行者方法
        @Override
        public void onLocationChanged(Location location) {
            if (simpleSysLocationListener != null)
                simpleSysLocationListener.onLocationEnabled(location.getLatitude(), location.getLongitude());
            LogUtils.i(tag,"onLocationChanged",location.toString());
            //释放定位资源
            releaseLocationService();
        }

        // 当某一个位置提供者状态发生变化的时候 关闭--》开启 或者开启--》关闭
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtils.i(tag,"onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            LogUtils.i(tag,"onProviderEnabled ",provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            LogUtils.i(tag,"onProviderDisabled ",provider);
        }
    }

    public interface SimpleSysLocationListener {
        void onLocationEnabled(double lat, double lng);
    }

    private void releaseLocationService() {
        try {

            lm.removeUpdates(mLocationListener);
            instance = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

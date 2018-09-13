package baidumapsdk.demo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import baidumapsdk.demo.BaiduMapSDK;


/**
 * Created by clawpo on 2017/3/21.
 * 地图类数据本地配置保存工具类
 */

public class MapSPUtil {
    private static final String PREFRENCE_NAME = "map";

    private static final String LATITUDE = "latitude";
    private static final String LONGTITUDE = "longitude";
    private static final String LAST_LOCATE_TIME = "lastLocateTime";
    private static final String tag = "MapSPUtil";

    private static MapSPUtil instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static MapSPUtil getInstance(){
        if (instance==null){
            instance = new MapSPUtil();
            sharedPreferences = BaiduMapSDK.getInstance().getContext().
                    getSharedPreferences(PREFRENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return instance;
    }



    public String getLatitude(){
        return sharedPreferences.getString(LATITUDE,"0.0");
    }
    public void saveLatitude(String lat){
        if (lat.contains("E")) {
            Log.e(tag,"saveLatitude: "+lat);
            return;
        }
        if (Util.isDoubleOrFloat(lat)) {
            Log.i(tag,"lat "+lat);
            editor.putString(LATITUDE,lat).commit();
        }
    }

    public String getLongtitude(){
        return sharedPreferences.getString(LONGTITUDE,"0.0");
    }

    public void saveLongitude(String lng){
        if (lng.contains("E")) {
            Log.e(tag,"saveLongitude: "+lng);
            return;
        }
        if (Util.isDoubleOrFloat(lng)){
            Log.i(tag,"lat "+lng);
            editor.putString(LONGTITUDE,lng).commit();
        }
    }

    public String getLastLocateTime(){
        return sharedPreferences.getString(LAST_LOCATE_TIME,"");
    }
    public void saveLastLocateTime(String time){
        editor.putString(LAST_LOCATE_TIME,time).commit();
    }

}

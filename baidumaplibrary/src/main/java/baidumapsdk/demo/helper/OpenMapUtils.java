package baidumapsdk.demo.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;

/**
 * Created by net on 2018/6/7.
 * 打开第三方地图工具类
 */

public class OpenMapUtils {
    /**
     * 打开百度地图驾驶路线规划 起点到终点
     * 注意手机百度地图用的是bd09ll（百度经纬度坐标）
     */
    public static void startRoutePlanDriving(Context context,LatLng start, LatLng end){
        RouteParaOption routeParaOption = new RouteParaOption()
                .startPoint(start)
                .endPoint(end);

        BaiduMapRoutePlan.openBaiduMapDrivingRoute(routeParaOption,context);
    }

    /**
     * 打开百度地图驾驶路线规划
     *  须先检查本地是否安装有百度地图 包名：com.baidu.BaiduMap  手机百度地图用的是bd09ll（百度经纬度坐标）
     *  起点为地图默认定位点
     *  参考官方API：http://lbsyun.baidu.com/index.php?title=uri/api/android
     *  @param lat 终点纬度
     *  @param lng 终点经度
     */
    public static void openBMapRoutePlan(Context mContext,double lat,double lng,String des) throws Exception{
        Intent i1 = new Intent();
        // 驾车路线规划
        i1.setData(Uri.parse("baidumap://map/direction?destination=latlng:"+lat+","+lng+"|name:"+des));
        mContext.startActivity(i1);
    }

    public static void openBMapRoutePlan(Context mContext,double lat,double lng) throws Exception{
        Intent i1 = new Intent();
        // 驾车路线规划
        i1.setData(Uri.parse("baidumap://map/direction?destination="+lat+","+lng));
        mContext.startActivity(i1);
    }

    /**
     *  打开高德地图驾驶路线规划
     *  须先检查本地是否安装有百度地图 包名：com.autonavi.minimap  手机高德用的是火星坐标，gcj02 国测局坐标(火星坐标)
     *  起点为地图默认定位点
     *  参考官方API ： http://lbs.amap.com/api/amap-mobile/guide/android/route
     *  @param lat 终点纬度
     *  @param lng 终点经度
     */
    public static void openAmapRoutePlan(Context mContext,double lat,double lng,String des) throws Exception{
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse("amapuri://route/plan/?&dlat="+lat+"&dlon="+lng+"&dname="+des+"&dev=0&t=0"));
        //amapuri://route/plan/?sid=BGVIS1&slat=39.92848272&slon=116.39560823&sname=A&did=BGVIS2&dlat=39.98848272&dlon=116.47560823&dname=B&dev=0&t=0

        mContext.startActivity(intent);
    }

    public static void openAmapRoutePlan(Context mContext,double lat,double lng) throws Exception{
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse("amapuri://route/plan/?&dlat="+lat+"&dlon="+lng+"&dev=0&t=0"));
        //amapuri://route/plan/?sid=BGVIS1&slat=39.92848272&slon=116.39560823&sname=A&did=BGVIS2&dlat=39.98848272&dlon=116.47560823&dname=B&dev=0&t=0

        mContext.startActivity(intent);
    }
}

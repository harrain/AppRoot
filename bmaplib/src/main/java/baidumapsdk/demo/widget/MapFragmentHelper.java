package baidumapsdk.demo.widget;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.widget.MapFragment;

/**
 * Created by net on 2018/5/9.
 * 辅助mapfragment的工具类，封装地图的基本操作，点、线显示，比例尺、指南针、缩放button等地图工具
 */

public class MapFragmentHelper {

    private SupportMapFragment map1;
    private BitmapDescriptor qw;
    private String TAG = "MapFragmentHelper";

    public MapFragmentHelper(FragmentActivity activity,@IdRes int id){
        map1 = (SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(id);
    }

    public MapFragmentHelper(FragmentActivity activity, LatLng latLng, @IdRes int id) {
        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(latLng);

        map1 = (SupportMapFragment) (activity.getSupportFragmentManager()
                .findFragmentById(id));
        map1.getBaiduMap().setMapStatus(u1);
    }

    public MapFragmentHelper(FragmentActivity activity, LatLng latLng, float zoom, @IdRes int id) {
        MapStatus.Builder builder = new MapStatus.Builder();
        MapStatus mapStatus = builder.target(latLng).zoom(zoom).build();
        MapStatusUpdate u1 = MapStatusUpdateFactory.newMapStatus(mapStatus);

        map1 = (SupportMapFragment) (activity.getSupportFragmentManager()
                .findFragmentById(id));

        map1.getBaiduMap().setMapStatus(u1);
    }

    public MapFragmentHelper(@IdRes int containerViewId, FragmentActivity activity) {

        map1 = new MapFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(containerViewId, map1).commit();

    }


    public MapFragmentHelper(MapFragment mapFragment) {

        map1 = mapFragment;

    }

    public void setLocDataForMap(double lat,double lng){
        setLocDataForMap(lat, lng,0f,getMap());
    }

    public void setLocDataForMap(LatLng latLng, float radius, BaiduMap baiduMap){
        Log.i(TAG,"setLocDataForMap");
        setLocDataForMap(latLng.latitude, latLng.longitude, radius,0 ,baiduMap);
    }

    public void setLocDataForMap(double lat, double lng, float radius, BaiduMap baiduMap){
        setLocDataForMap(lat, lng, radius,0 ,baiduMap);
    }

    public void setLocDataForMap(double lat, double lng, float radius, float direction, BaiduMap baiduMap){
        setLocDataForMap(lat, lng, radius, direction,15.0f, baiduMap);
    }
    /**
     * 显示并聚焦定位点
     * @param lat 纬度
     * @param lng 经度
     * @param radius 定位精度圈
     * @param direction 方向 0为手机竖屏时正方向 范围0-360
     * @param zoom 缩放级别
     */
    public void setLocDataForMap(double lat, double lng, float radius, float direction,float zoom, BaiduMap baiduMap){
        if (baiduMap == null){
            Log.e("LocationRequest", "setLocDataForMap: baidumap is null");
            return;
        }
        if (lat == 0 || lng == 0){
            Log.e(TAG,"setLocDataForMap :lat lng 为 0");
            return;
        }
        baiduMap.setMyLocationEnabled(true);

        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(radius)//accuracy为定位精度圈，0 无精度圈
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(direction).latitude(lat)
                .longitude(lng).build();
        baiduMap.setMyLocationData(locData);
        Log.i("LocationRequest", " "+lat+lng);
//                if (isFirstLoc) {
//                    isFirstLoc = false;
//
//                }
        LatLng ll = new LatLng(lat,
                lng);//经纬度容器.支持写parcel
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(zoom);//target 设置地图中心点（会显示位置图标） ； zoom 设置缩放级别
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public MapFragment getMapFragment() {
        if (map1 == null) Log.e(TAG, "mapfragment is null");
        return (MapFragment) map1;
    }

    public MapView getMapView() {
        if (map1.getMapView() == null) Log.e(TAG, "[getmapview] mapview is null");
        return map1.getMapView();
    }

    public BaiduMap getMap() {
        if (map1.getBaiduMap() == null) Log.e(TAG, "[getmap] baidumap is null");
        return map1.getBaiduMap();
    }

    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng) {
        return addMarker(drawableId, latLng, 2);
    }

    public Marker addMarkerGrow(@DrawableRes int drawableId, LatLng latLng) {
        return addMarker(drawableId, latLng, 2, MarkerOptions.MarkerAnimateType.grow);
    }


    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng, int zIndex) {
        return addMarker(drawableId, latLng, zIndex, MarkerOptions.MarkerAnimateType.none);
    }

    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng, String title) {
        return addMarker(drawableId, latLng,title,2,null, MarkerOptions.MarkerAnimateType.none);
    }

    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng, int zIndex, MarkerOptions.MarkerAnimateType markerAnimateType) {
        return addMarker(drawableId, latLng,null,zIndex,null,markerAnimateType);
    }

    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng,Bundle bundle) {
        return addMarker(drawableId, latLng, 2,bundle);
    }

    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng, int zIndex, Bundle bundle) {
        return addMarker(drawableId, latLng,null, zIndex, bundle,null);
    }
    /**
     * 在baidumap上显示marker点图标
     * @param drawableId 图片素材ID
     * @param latLng 百度坐标对象
     * @param title marker携带的文字标题，可传递字符串数据
     * @param zIndex marker所在层级
     * @param bundle 携带的额外信息
     * @param markerAnimateType 显示的动画类型
     */
    public Marker addMarker(@DrawableRes int drawableId, LatLng latLng,String title,int zIndex, Bundle bundle, MarkerOptions.MarkerAnimateType markerAnimateType){
        if (latLng == null) {
            Log.e(TAG, "[addmarker] latlng is null");
            return null;
        }
        qw = BitmapDescriptorFactory
                .fromResource(drawableId);
        if (qw == null) {
            Log.e(TAG, "[addmarker] qw is null");
            return null;
        }
        if (getMap() == null) return null;
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(qw).title(title)
                .zIndex(zIndex).extraInfo(bundle).animateType(markerAnimateType);
        Marker marker = (Marker) (getMap().addOverlay(ooA));

        return marker;
    }



    public void addMarkers(@DrawableRes int drawableId, List<LatLng> latLngs) {
        if (latLngs == null) {
            Log.e(TAG, "[addmarkers] latlngs is null");
            return;
        }
        BitmapDescriptor bd = BitmapDescriptorFactory
                .fromResource(drawableId);
        if (bd == null) {
            Log.e(TAG, "[addMarkers] bd is null");
            return;
        }
        List<OverlayOptions> markerOptions = new ArrayList<>();
        for (LatLng latlng : latLngs) {
            markerOptions.add(new MarkerOptions().position(latlng).icon(bd)
                    .zIndex(2));
        }
        Log.i(TAG, "addMarkers: size "+markerOptions.size());
        getMap().addOverlays(markerOptions);
//        for (LatLng latLng : latLngs) {
//            addMarker(drawableId,latLng);
//        }

    }

    public void addMarkers(@DrawableRes int drawableId, List<LatLng> latLngs,String title) {
        if (latLngs == null) {
            Log.e(TAG, "[addmarkers] latlngs is null");
            return;
        }
        BitmapDescriptor bd = BitmapDescriptorFactory
                .fromResource(drawableId);
        if (bd == null) {
            Log.e(TAG, "[addMarkers] bd is null");
            return;
        }
        List<OverlayOptions> markerOptions = new ArrayList<>();
        for (LatLng latlng : latLngs) {
            markerOptions.add(new MarkerOptions().position(latlng).icon(bd).title(title)
                    .zIndex(2));
        }
        Log.i(TAG, "addMarkers: size "+markerOptions.size());
        getMap().addOverlays(markerOptions);
//        for (LatLng latLng : latLngs) {
//            addMarker(drawableId,latLng);
//        }

    }
    /**
     * 聚焦坐标点 缩放
     */
    public void mapStatusFocus(double lat,double lng){
        if (getMap() == null){
            return;
        }
        LatLng ll = new LatLng(lat,
                lng);//经纬度容器.支持写parcel
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);//target 设置地图中心点（会显示位置图标） ； zoom 设置缩放级别
        getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    /**
     * 移除marker
     * @param marker 要移除的marker
     */
    public void removeMarker(Marker marker){
        if (marker!=null)marker.remove();
    }

    /**
     * 缩放按钮
     * true 开启；false 关闭
     */
    public void showZoomControl(boolean enable) {
        if (getMapView() != null) getMapView().showZoomControls(enable);
    }

    /**
     * 指南针
     * true 开 ； false 闭
     */
    public void showCompass(boolean enable){
        if (getMap() != null) getMap().getUiSettings().setCompassEnabled(enable);
    }

    /**
     * 比例尺
     * true 开 ; false 闭
     */
    public void showScale(boolean enable){
        if (getMapView() != null) getMapView().showScaleControl(enable);
    }

}

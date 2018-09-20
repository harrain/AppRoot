package baidumapsdk.demo.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.JingWei;
import baidumapsdk.demo.R;
import baidumapsdk.demo.helper.GeoCoderHelper;
import baidumapsdk.demo.helper.LocationRequest;
import baidumapsdk.demo.helper.MapSPUtil;
import baidumapsdk.demo.helper.Util;
import baidumapsdk.demo.listener.FragmentLifeCycle;
import baidumapsdk.demo.widget.MapFragment;
import baidumapsdk.demo.widget.MapFragmentHelper;

/**
 * 显示marker和marker信息窗体及跳转
 */
public class MarkerInfoWindowActivity extends AppCompatActivity {

    private GeoCoderHelper geoCoderHelper;
    private TextView infoWindowTv;
    private MapFragmentHelper mapFragmentHelper;

    private List<JingWei> markerDatas = new ArrayList<>();
    String tag = "MarkerInfoWindowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlebar_frame);
        FrameLayout backBtn = findViewById(R.id.map_toolbar_back_fl);
        TextView titleTv = findViewById(R.id.map_toolbar_title);
        ImageView moreIv = findViewById(R.id.map_toolbar_more);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv.setText("marker信息窗");

        geoCoderHelper = new GeoCoderHelper();

        mapFragmentHelper = new MapFragmentHelper(R.id.map_ac_contentview, this);

        mapFragmentHelper.getMapFragment().setFragmentLifeCycleListener(new FragmentLifeCycle() {//可根据各生命周期对marker操作

            @Override
            public void onViewCreated() {
                super.onViewCreated();
                mapFragmentHelper.showZoomControl(false);
                //默认显示上一次定位点
                String latitudestr = MapSPUtil.getInstance().getLatitude();
                String longtitudestr = MapSPUtil.getInstance().getLongtitude();
                if (!latitudestr.equals("0.0") && !longtitudestr.equals("0.0")) {
                    double latitude = Double.parseDouble(latitudestr);
                    double longtitude = Double.parseDouble(longtitudestr);
                    mapFragmentHelper.setLocDataForMap(latitude, longtitude
                            , 0, mapFragmentHelper.getMap());

                    generateMarkerItems(latitude, longtitude);

                }
                //点击除marker外的地方 关闭信息窗体
                mapFragmentHelper.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mapFragmentHelper.getMap().hideInfoWindow();
                    }

                    @Override
                    public boolean onMapPoiClick(MapPoi mapPoi) {
                        mapFragmentHelper.getMap().hideInfoWindow();
                        return false;
                    }
                });


                mapFragmentHelper.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        showInfoWindow((JingWei) marker.getExtraInfo().getParcelable("jingwei"));
                        return false;
                    }
                });

                initlocateBtn();
            }
        });


    }

    int index = 0;

    /**
     * 准备marker数据
     */
    private void generateMarkerItems(double lat, double lng) {
        final List<LatLng> lls = new ArrayList<>();
        double ra = 0.01;
        double la0 = lat + ra;
        double ln0 = lng;
        double la1 = lat;
        double ln1 = lng + ra;
        double la2 = lat - ra;
        double ln2 = lng;
        double la3 = lat;
        double ln3 = lng - ra;

        lls.add(new LatLng(la0, ln0));
        lls.add(new LatLng(la1, ln1));
        lls.add(new LatLng(la2, ln2));
        lls.add(new LatLng(la3, ln3));
        markerDatas.clear();
        geoCoderHelper.setReverseLister(new GeoCoderHelper.ReverseGeoCodeAddress() {
            @Override
            public void onGetReverseGeoCodeAddress(String address) {
                markerDatas.add(new JingWei(lls.get(index).latitude, lls.get(index).longitude, address));
                Log.i(tag, "onGetReverseGeoCodeAddress: markerDatas size " + markerDatas.size());
                index++;
                if (index >= lls.size()) {
                    index = 0;
                    showMarker(markerDatas);
                }
            }
        });
        for (LatLng ll : lls) {
            geoCoderHelper.reverseGeoCode(ll);
        }

    }

    /**
     * 在地图上显示marker
     */
    private void showMarker(List<JingWei> datas) {

        mapFragmentHelper.getMap().clear();//清除旧的marker
        for (JingWei data : datas) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("jingwei", data);
            mapFragmentHelper.addMarker(R.drawable.poi_favor_point, new LatLng(data.getLat(), data.getLng()), bundle);//地图上的小红点为marker
        }

    }

    /**
     * 地图上弹出的信息窗体
     */
    private void showInfoWindow(final JingWei jingWei) {
        View view = LayoutInflater.from(this).inflate(R.layout.map_textview_center, null, false);
        view.setBackgroundResource(R.drawable.popup);
        infoWindowTv = view.findViewById(R.id.map_center_tv);
        infoWindowTv.setText(jingWei.getAddress());
        InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), new LatLng(jingWei.getLat(), jingWei.getLng())
                , -50, new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                mapFragmentHelper.getMap().hideInfoWindow();
                double[] ll = {jingWei.getLat(), jingWei.getLng()};
                LocationInfoActivity.startActivity(MarkerInfoWindowActivity.this, ll, jingWei.getAddress());
            }
        });
        mapFragmentHelper.getMap().showInfoWindow(infoWindow);
    }

    /**
     * 在mapfragment上动态添加定位button
     */
    private void initlocateBtn() {
        ImageView locateBtn = new ImageView(this);
        locateBtn.setImageResource(R.drawable.locate_btn_selector);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Util.dp2px(50), Util.dp2px(50));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.rightMargin = Util.dp2px(16f);
        params.bottomMargin = Util.dp2px(16f);
        mapFragmentHelper.getMapFragment().getRootLayout().addView(locateBtn, params);

        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationRequest.getInstance(Util.getApplicationContext()).requestPermissionToLocate(MarkerInfoWindowActivity.this, new LocationRequest.LocationInfoListener() {
                            @Override
                            public void onLocationInfo(double lat, double lng, float radius) {
                                MapSPUtil.getInstance().saveLatitude(lat);
                                MapSPUtil.getInstance().saveLongitude(lng);

                                mapFragmentHelper.setLocDataForMap(lat, lng);
                                generateMarkerItems(lat, lng);

                            }
                        });
            }
        });
    }
}

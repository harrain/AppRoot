package baidumapsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;

import baidumapsdk.demo.helper.LocationRequest;
import baidumapsdk.demo.helper.MapSPUtil;
import baidumapsdk.demo.helper.Util;
import baidumapsdk.demo.widget.MapFragment;
import baidumapsdk.demo.widget.MapFragmentHelper;
import baidumapsdk.demo.widget.OpenMapDialog;

/**
 * Created by net on 2018/8/1.
 * 模仿微信的位置信息界面
 */

public class LocationInfoActivity extends AppCompatActivity {

    private Toolbar titlebar;
    private TextView titleTv;
    private ImageView moreIv;
    private MapFragment mapFragment;
    private String address;
    private double[] latlng;
    private MapFragmentHelper mapFragmentHelper;
    private TextView addressTv;
    String tag = "LocationInfoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        latlng = intent.getDoubleArrayExtra("latlng");

        setContentView(R.layout.activity_location_info);

        bindView();
        refreshView();

        mapFragmentHelper = new MapFragmentHelper(mapFragment);
        mapFragmentHelper.showZoomControl(false);//关闭缩放按钮
        mapFragmentHelper.showCompass(false);//关闭指南针
        mapFragmentHelper.addMarker(R.drawable.overlay_red_ding,new LatLng(latlng[0],latlng[1]));//显示marker
        mapFragmentHelper.mapStatusFocus(latlng[0],latlng[1]);//聚焦该坐标点
    }

    private void refreshView() {
        titleTv.setText(getResources().getString(R.string.tb_locationInfo));
        addressTv.setText(address);
    }

    private void bindView() {
        titlebar = findViewById(R.id.map_tb);
        FrameLayout backBtn = findViewById(R.id.map_toolbar_back_fl);
        titleTv = findViewById(R.id.map_toolbar_title);
        moreIv = findViewById(R.id.map_toolbar_more);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.ac_loc_mapf);
        ImageView locBtn = findViewById(R.id.ac_loc_iv);
        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag,"locbtn");
                double la = Double.parseDouble(MapSPUtil.getInstance().getLatitude());
                double ln = Double.parseDouble(MapSPUtil.getInstance().getLongtitude());
                mapFragmentHelper.setLocDataForMap(la,ln);//显示并聚焦定位点
                //单例获取定位
                LocationRequest.getInstance(Util.getApplicationContext()).startLocate(new LocationRequest.LocationInfoListener() {
                    @Override
                    public void onLocationInfo(double lat, double lng, float radius) {
                        Log.i(tag," "+lat);
                        mapFragmentHelper.setLocDataForMap(lat,lng);
                        LocationRequest.release();
                    }
                });
            }
        });

        //供用户选择使用哪个地图打开的弹窗（百度地图、高德地图可选）
        final OpenMapDialog openMapDialog = new OpenMapDialog(this,new LatLng(latlng[0],latlng[1]),address);

        findViewById(R.id.ac_loc_guild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapDialog.showDialog();
            }
        });
        addressTv = findViewById(R.id.addresstext);
    }

    public static void startActivity(AppCompatActivity context,double[] latlng,String address){
        Intent intent = new Intent(context,LocationInfoActivity.class);
        intent.putExtra("latlng",latlng);
        intent.putExtra("address",address);
        context.startActivity(intent);
    }
}

package baidumapsdk.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import baidumapsdk.demo.helper.LatlngConverterHelper;
import baidumapsdk.demo.helper.LocationRequest;
import baidumapsdk.demo.helper.MapSPUtil;
import baidumapsdk.demo.helper.PlaceSelectHelper;
import baidumapsdk.demo.widget.MapFragment;
import baidumapsdk.demo.widget.MapFragmentHelper;

public class PlaceSelectActivity extends AppCompatActivity {

//    private var mBaiduMap: BaiduMap? = null
//    private var mCurrentLat: Double = 0.toDouble()
//    private var mCurrentLon: Double = 0.toDouble()
//    private var mCurrentAccracy: Float = 0.toFloat()
//    private var locData: MyLocationData? = null
//    private val mCurrentDirection = 100f
//    private val isFirstLoc: Boolean = false
//    private var centerAddressTv: TextView? = null
//    private var addressDetail: String = ""
//    private var target: LatLng? = null
//    private var receiver: Receiver? = null
//    private lateinit var latlngConverterHelper: LatlngConverterHelper
//    lateinit var rxPermissions : RxPermissions
    double mCurrentLat;
    double mCurrentLon;
    float mCurrentAccracy;
    LatLng target;
    Receiver receiver;
    private MapFragmentHelper mapFragmentHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_select);

        android.support.v7.widget.Toolbar titlebar = findViewById(R.id.map_tb);
        FrameLayout backBtn = findViewById(R.id.map_toolbar_back_fl);
        TextView titleTv = findViewById(R.id.map_toolbar_title);
        ImageView moreIv = findViewById(R.id.map_toolbar_more);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv.setText("选择地点");

        Button poi = findViewById(R.id.place_poi);
        poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target != null) PlaceSelectHelper.getInstance().getTodoListener().dothis(target);
            }
        });

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.ac_placeselect_mapf);
        mapFragmentHelper = new MapFragmentHelper(mapFragment);
        initLocate();

        receiver = new Receiver();
        registerReceiver(receiver, new IntentFilter("FINISH_PLACESELECTACTIVITY"));
    }

    private void initLocate(){
        String latstr = MapSPUtil.getInstance().getLatitude();
        String lngstr = MapSPUtil.getInstance().getLongtitude();
        if (latstr != "0.0" && lngstr != "0.0"){
            double lat = Double.parseDouble(latstr);
            double lng = Double.parseDouble(lngstr);
            target = new LatLng(lat,lng);
            mapFragmentHelper.setLocDataForMap(lat,lng,0,90,mapFragmentHelper.getMap());
        }
        //开启定位
        LocationRequest.getInstance(this).startLocate(new LocationRequest.LocationInfoListener() {
            @Override
            public void onLocationInfo(double lat, double lng, float radius) {
                mCurrentLat = lat;
                mCurrentLon = lng;
                mCurrentAccracy = radius;
                mapFragmentHelper.setLocDataForMap(lat,lng,0,90,mapFragmentHelper.getMap());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 退出时销毁定位
        LocationRequest.release();

        try {
            if (receiver != null) unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    public static void startActivity(AppCompatActivity context){
        context.startActivity(new Intent(context,PlaceSelectActivity.class));
    }

}

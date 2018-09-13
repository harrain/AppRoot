package baidumapsdk.demo.helper;

import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 地理编码的工具类，实现经纬度与地理信息的互转
 * 1 new GeoCoderHelper
 * 2 setlistener
 * 3 reverseGeoCode
 */

public class GeoCoderHelper {

    private GeoCoder geoCoder;
    private String TAG = "GeoCoderHelper";
    private ReverseGeoCodeAddress reverseLister;
    private GeoCodeLatlng geoListener;

    public GeoCoderHelper(){

        geoCoder = GeoCoder.newInstance();

        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.i(TAG, "onGetGeoCodeResult: 抱歉，未能找到结果");

                    return;
                }
                if (geoListener!=null) geoListener.onGetGeoCodeLatlng(result.getLocation());

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.i(TAG, "onGetReverseGeoCodeResult: 抱歉，未能找到结果");

                    return;
                }

                String addressDetail = result.getAddressDetail().city + ""+
                        result.getAddressDetail().district +""+ result.getAddressDetail().street +
                        result.getAddressDetail().streetNumber;
                if (reverseLister!=null) reverseLister.onGetReverseGeoCodeAddress(addressDetail);
            }
        });
    }

    public void setReverseLister(ReverseGeoCodeAddress lister){
        reverseLister = lister;
    }

    public void setGeoListener(GeoCodeLatlng listener){
        geoListener = listener;
    }
    /**
     * 将经纬度转成地理信息
     */
    public void reverseGeoCode(LatLng latLng){
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }
    /**
     * 将地理信息转成经纬度
     */
    public void geoCode(String address){
        geoCoder.geocode(new GeoCodeOption().address(address));
    }

    public interface ReverseGeoCodeAddress{
        void onGetReverseGeoCodeAddress(String address);
    }

    public interface GeoCodeLatlng{
        void onGetGeoCodeLatlng(LatLng latLng);
    }
}

package baidumapsdk.demo.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

import baidumapsdk.demo.R;
import baidumapsdk.demo.helper.LatlngConverterHelper;
import baidumapsdk.demo.helper.OpenMapUtils;
import baidumapsdk.demo.helper.Util;

/**
 * Created by net on 2018/6/8.
 * 供打开第三方地图的底部弹窗（百度地图、高德地图）
 */

public class OpenMapDialog extends BottomSheetDialog implements View.OnClickListener {

    Context mContext;
    LatLng lastGPSLL;
    LatLng endll;
    String des;
    LatlngConverterHelper latlngConverterHelper;

    public OpenMapDialog(@NonNull Context context) {
        this(context, 0);
    }

    public OpenMapDialog(@NonNull Context context, int theme) {
        super(context, theme);
        mContext = context;
        initDialog();
    }

    protected OpenMapDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public OpenMapDialog(Context context, LatLng start, LatLng end) {
        this(context, 0);
        lastGPSLL = start;
        endll = end;
    }

    public OpenMapDialog(Context context, LatLng start, LatLng end,String destination) {
        this(context, 0);
        lastGPSLL = start;
        endll = end;
        des = destination;
        latlngConverterHelper = new LatlngConverterHelper(CoordinateConverter.CoordType.GPS);
    }
    /**
     * @param end 目的地点的坐标对象
     * @param destination 目的地的文字描述
     */
    public OpenMapDialog(Context context,LatLng end,String destination) {
        this(context, 0);
        endll = end;
        des = destination;
        latlngConverterHelper = new LatlngConverterHelper(CoordinateConverter.CoordType.COMMON);//火星坐标转bdll
    }

    private void initDialog() {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        this.setContentView(R.layout.layout_openmap);
        findViewById(R.id.cancle_openmapdialog).setOnClickListener(this);
        findViewById(R.id.bmap_ll).setOnClickListener(this);
        findViewById(R.id.amap_ll).setOnClickListener(this);
    }

    public OpenMapDialog showDialog() {
        this.show();
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancle_openmapdialog) {//点击对话框的"取消"
            dismiss();
        } else if (v.getId() == R.id.bmap_ll) {//点击"百度地图"
            if (Util.checkPackage(mContext, "com.baidu.BaiduMap")) {
                LatLng bd = latlngConverterHelper.convertLatlng(endll);//转成bd09ll（百度经纬度坐标）
                //用schema的方式比较好，参数设置更丰富简便
                try {
                    OpenMapUtils.openBMapRoutePlan(mContext, bd.latitude, bd.longitude,des);//打开百度地图APP
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else Toast.makeText(mContext, "没有安装百度地图APP", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.amap_ll) { //点击"高德地图"
            if (Util.checkPackage(mContext, "com.autonavi.minimap")) {
                try {
                    OpenMapUtils.openAmapRoutePlan(mContext, endll.latitude, endll.longitude,des);//打开高德地图APP
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "没有安装高德地图APP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

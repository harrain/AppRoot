package baidumapsdk.demo.helper;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

/**
 * GPS坐标与火星坐标向百度坐标系的转换工具类
 * function: convert other-type latlng to baidu type 坐标转换
 */

public class LatlngConverterHelper {

    private CoordinateConverter converter;

    /**
     * @param coordType GPS or COMMON(火星坐标)
     */
    public LatlngConverterHelper(CoordinateConverter.CoordType coordType){
        converter = new CoordinateConverter();
        converter.from(coordType);
    }

    /**
     * 开始转换
     * @param latLng 经纬度对象
     * @return 转换后得到的百度经纬度对象
     */
    public LatLng convertLatlng(LatLng latLng){
        return converter.coord(latLng).convert();
    }




}

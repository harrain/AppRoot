package code_base.util.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 各种转换方法的工具类
 * Created by yao on 2016/10/2.
 */

public class ModelConvertUtils {

    public static <T> ArrayList<T> array2List(T[] array) {
        List<T> list = Arrays.asList(array);
        ArrayList<T> arrayList = new ArrayList<>(list);
        return arrayList;
    }

}

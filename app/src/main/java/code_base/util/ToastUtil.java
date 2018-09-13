package code_base.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by data on 2017/9/13.
 * toast工具类，debug和release都显示
 */

public class ToastUtil {
    private static Context mContext = null;
    public static void init(Context context){ mContext = context;}

    public static void showShortToast(String text){

        Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String text){

        Toast.makeText(mContext,text,Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(@StringRes int strId){

        Toast.makeText(mContext,strId,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(@StringRes int strId){

        Toast.makeText(mContext,strId,Toast.LENGTH_LONG).show();
    }
}

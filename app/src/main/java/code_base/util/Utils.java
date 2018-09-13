package code_base.util;

import android.content.Context;


/**
 * Created by data on 2017/9/1.
 * Context工具类
 */

public class Utils {

    private static Context mContext;
    private static boolean debug = true;

    public static void init(Context context){mContext = context;}

    public static Context getContext(){
        return mContext;
    }

    public static boolean isDebug(){ return debug; }
}

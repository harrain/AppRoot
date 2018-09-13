package code_base.util.view;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by net on 2018/6/13.
 * 状态栏工具类
 */

public class StatusBarUtil {

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}

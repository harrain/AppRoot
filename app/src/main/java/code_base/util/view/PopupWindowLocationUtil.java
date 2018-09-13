package code_base.util.view;

import android.view.View;


import code_base.util.DeviceUtils;

/**
 * Created by net on 2018/3/12.
 * 在recyclerview列表上显示的删除窗体，模仿微信弹出形式
 */

public class PopupWindowLocationUtil {

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = DeviceUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = DeviceUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight - UIConvertUtils.dp2px(60)< windowHeight);//屏幕高度-锚点view的纵坐标-锚点view的高度-导航栏高度 是否小于 popview高度
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight + UIConvertUtils.dp2px(30);//30dp是为了让popupwindow和anchorview重叠一块
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight - UIConvertUtils.dp2px(30);//同上
        }
        return windowPos;
    }
}

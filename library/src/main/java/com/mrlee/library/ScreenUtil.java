package com.mrlee.library;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class ScreenUtil {
    /**
     * dpתpx
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * pxתdp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * screenWidth
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * screenHeight
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }



    /**
     * @param context
     * @return
     */

    public static boolean isPortrait(Context context) {
        /**
         * int ORIENTATION_PORTRAIT = 1;  竖屏
         * int ORIENTATION_LANDSCAPE = 2; 横屏
         */
        //获取屏幕的方向  ,数值1表示竖屏，数值2表示横屏
        int screenNum = context.getResources().getConfiguration().orientation;
        //（如果竖屏）设置屏幕为横屏
        if (screenNum == 1) {
            //            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            return true;
        } else {
            //            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return false;
        }
    }

    /**
     * 获取手机状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

package com.tldxdy.androidutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *@desc 沉浸式状态栏 兼容4.4
 **/
public class StatuBarCompat {
    private static final String TAG = "StatuBarCompat";

    private static String osType;
    private static final String MIUI_OS    = "miui";
    private static final String FLY_OS     = "fly";
    private static final String ANDROID_OS = "6.0+";

    private static final int COLOR_DEFAULT = Color.TRANSPARENT;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (contentView.findViewById(R.id.statu_title) != null) {
                contentView.findViewById(R.id.statu_title).setBackgroundColor(statusColor);
            } else {
                View statusBarView = new View(activity);
                statusBarView.setId(R.id.statu_title);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ScreenUtils.getStatusBarHeight(activity));
                statusBarView.setBackgroundColor(statusColor);
                contentView.addView(statusBarView, lp);
            }
        }

    }

    public static void compat(Activity activity) {
        compat(activity, COLOR_DEFAULT);

    }

    /**
     * 设置状态栏透明
     */
    public static void setTranslucentStatus(Activity activity) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     *
     * @param fontIconDark  设置是否深色
     * @param statusBarColor    遮罩颜色值
     * @param activity
     */
    public static void setImmersiveStatusBar(boolean fontIconDark, int statusBarColor, Activity activity) {
        setTranslucentStatus(activity);
        if (fontIconDark) {
            if (isMIUI()) {
                // 小米MIUI
                setStatusBarFontIconDarkForMIUI(true, activity);
            } else if (isFlyme()) {
                // 魅族FlymeUI
                setStatusBarFontIconDarkForFlyme(true, activity);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // android6.0+系统
                setStatusBarFontIconDarkForAndroidM(true, activity);
            } else {
                if (statusBarColor == Color.WHITE) {
                    statusBarColor = 0xffcccccc;
                }
            }
        }
        setAndroidNativeLightStatusBar(activity,fontIconDark);
        compat(activity, statusBarColor);
    }


    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体和图标是否为深色
     */
    protected static void setStatusBarFontIconDarkForAndroidM(boolean dark, Activity activity) {
        // android6.0+系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    // 魅族FlymeUI
    private static void setStatusBarFontIconDarkForFlyme(boolean dark, Activity activity) {
        try {
            Window window     = activity.getWindow();
            WindowManager.LayoutParams lp         = window.getAttributes();
            Field darkFlag   = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit   = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MIUI 设置状态栏
    private static void setStatusBarFontIconDarkForMIUI(boolean dark, Activity activity) {
        try {
            Window window         = activity.getWindow();
            Class clazz          = activity.getWindow().getClass();
            Class layoutParams   = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field          = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int    darkModeFlag   = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断miui系统
     *
     * @return
     */
    public static boolean isMIUI() {
        if (MIUI_OS.equals(osType) || ((!TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name")) && !"V9".equals(getSystemProperty("ro.miui.ui.version.name"))))) {
            osType = MIUI_OS;
            return true;
        }
        return false;
    }

    /**
     * 判断魅族手机
     *
     * @return
     */
    public static boolean isFlyme() {
        if (FLY_OS.equals(osType)) return true;
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            if (method != null) {
                osType = FLY_OS;
                return true;
            }
            return false;
        } catch (final Exception e) {
            return false;
        }
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    /**
     * 侧拉专用沉浸式
     *
     * @param activity      有drawlayout页面
     * @param holdSpaceView 与状态栏等高的占位view
     */
    public static void setImmersiveStatusBarWithView(boolean fontIconDark, Activity activity, View holdSpaceView) {
        setImmersiveStatusBarWithView(fontIconDark, activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            holdSpaceView.setVisibility(View.GONE);
//            activity.getWindow().setStatusBarColor(statusBarColor);
            setHolderViewHeightEqualsStatuBar(activity, holdSpaceView);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setHolderViewHeightEqualsStatuBar(activity, holdSpaceView);
        }
    }

    /**
     * 标题栏沉在状态栏里面
     *
     * @param fontIconDark
     * @param activity
     */
    public static void setImmersiveStatusBarWithView(boolean fontIconDark, Activity activity) {
        setTranslucentStatus(activity);
        if (fontIconDark) {
            if (isMIUI()) {
                // 小米MIUI
                setStatusBarFontIconDarkForMIUI(true, activity);
            } else if (isFlyme()) {
                // 魅族FlymeUI
                setStatusBarFontIconDarkForFlyme(true, activity);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // android6.0+系统
                setStatusBarFontIconDarkForAndroidM(true, activity);
            }
        }
    }

    public static void setHolderViewHeightEqualsStatuBar(Context context, View holdSpaceView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getStatusBarHeight(context));
        holdSpaceView.setLayoutParams(layoutParams);
    }

    public static void setHolderViewHeightEqualsStatuBar(Context context, View holdSpaceView, int color) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getStatusBarHeight(context));
        holdSpaceView.setLayoutParams(layoutParams);
        holdSpaceView.setBackgroundColor(color);
    }

    public static void setHolderViewWithColor(Context context, View holdSpaceView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getStatusBarHeight(context));
        holdSpaceView.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            holdSpaceView.setBackgroundColor(Color.parseColor("#ffcccccc"));
        }
    }




    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
    /**
     * 导航栏，状态栏透明
     * @param activity
     */
    public static void setNavigationBarStatusBarTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}